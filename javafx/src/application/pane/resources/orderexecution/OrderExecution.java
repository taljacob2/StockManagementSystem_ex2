package application.pane.resources.orderexecution;

import application.JavaFXAppController;
import application.pane.PaneAnimator;
import application.pane.resources.login.selecteduser.SelectedUser;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import message.print.MessagePrint;
import stock.Stock;
import user.User;
import user.holding.item.Item;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} of performing an <i>order
 * execution</i>.</p>
 *
 * <p>
 * This {@code class} uses the {@link User} that has been selected in the
 * previous <i>Login page</i>.
 * </p>
 */
public class OrderExecution implements Initializable {

    @FXML private ComboBox<String> buySellComboBox;
    @FXML private ComboBox<Stock> stockComboBox;
    @FXML private ComboBox<String> orderTypeComboBox;
    @FXML private Button executeOrderButton;
    @FXML private Spinner<Integer> spinner;

    /**
     * Note: the when performing a "Buy" order, the 'max' limit of the {@code
     * quantity} is hardcoded to be: '1000000'.
     *
     * @see #initSpinner
     */
    private SpinnerValueFactory<Integer> spinnerValueFactory;
    @FXML private Label userNameLabel;


    public OrderExecution() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        buySellComboBox.getItems().addAll("Buy", "Sell");
        orderTypeComboBox.getItems().addAll("LMT", "MKT");


        buySellComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> initStockComboBox());

        stockComboBox.valueProperty()
                .addListener((observable, oldValue, newValue) -> initSpinner());

        userNameLabel.setText(
                "Hello, " + SelectedUser.getSelectedUser().getName() + ". " +
                        "Please make an order.");

        initExecuteOrderButton(JavaFXAppController.getStaticBorderPane(),
                JavaFXAppController.getParentContainer(),
                JavaFXAppController.getAnimationType());

        /* -- Disable -- */

        stockComboBox.disableProperty()
                .bind(buySellComboBox.valueProperty().isNull()
                        .or(buySellComboBox.disableProperty()));

        orderTypeComboBox.disableProperty()
                .bind(stockComboBox.valueProperty().isNull()
                        .or(stockComboBox.disableProperty()));

        spinner.disableProperty()
                .bind(orderTypeComboBox.valueProperty().isNull()
                        .or(orderTypeComboBox.disableProperty()));

        /*
         * All combo-boxes must be selecting something,
         * in order to press the button.
         */
        executeOrderButton.disableProperty()
                .bind(buySellComboBox.disableProperty()
                        .or(stockComboBox.disableProperty()
                                .or(orderTypeComboBox.disableProperty()
                                        .or(spinner.disableProperty()))));
    }

    private void initSpinner() {
        if (buySellComboBox.valueProperty().getValue().toString()
                .equals("Sell") &&
                (stockComboBox.valueProperty().isNotNull().get())) {
            spinnerValueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                            (int) stockComboBox.getValue().getQuantity(
                                    SelectedUser.getSelectedUser()), 1);

        } else if (buySellComboBox.valueProperty().getValue().toString()
                .equals("Sell") &&
                (stockComboBox.valueProperty().isNull().get())) {
            spinnerValueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
        } else if (buySellComboBox.valueProperty().getValue().toString()
                .equals("Buy")) {

            // Note: max - limit here is hardcoded: 1000000
            spinnerValueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                            1000000, 1);
        }
        spinner.setValueFactory(spinnerValueFactory);
    }

    private void initExecuteOrderButton(BorderPane borderPane,
                                        Pane parentContainer,
                                        PaneAnimator.AnimationType animationType) {

        // define 'orderExecutionButton':
        executeOrderButton.setOnAction(
                new PaneAnimator.Handler(borderPane, parentContainer,
                        "/application/pane/resources/login/Login.fxml",
                        animationType));
    }

    private void initStockComboBox() {

        // Remove all previous items in the stock-comboBox:
        stockComboBox.getItems().clear();

        if (buySellComboBox.valueProperty().getValue().toString()
                .equals("Buy")) {
            try {

                // Show all stock available in the system:
                stockComboBox.getItems()
                        .addAll(Engine.getStocks().getCollection());
            } catch (IOException e) {

                /*
                 * Note: this exception should not happen thanks to the
                 * initial check of stocks.
                 */
                MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            }

        } else if (buySellComboBox.valueProperty().getValue().toString()
                .equals("Sell")) {

            // Show only the stock available in the user's items:
            stockComboBox.getItems()
                    .addAll(SelectedUser.getSelectedUser().getHoldings()
                            .getCollection().stream().map(Item::getStock)
                            .collect(Collectors.toList()));

        }
    }


}
