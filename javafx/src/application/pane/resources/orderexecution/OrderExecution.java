package application.pane.resources.orderexecution;

import application.JavaFXAppController;
import application.pane.PaneAnimator;
import application.pane.resources.login.selecteduser.SelectedUser;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
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
    @FXML private Spinner<Long> spinner;


    public OrderExecution() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        buySellComboBox.getItems().addAll("Buy", "Sell");
        orderTypeComboBox.getItems().addAll("LMT", "MKT");


        buySellComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> initStockComboBox());

        stockComboBox.valueProperty()
                .addListener((observable, oldValue, newValue) -> initSpinner());


        initExecuteOrderButton(JavaFXAppController.getStaticBorderPane(),
                JavaFXAppController.getParentContainer(),
                JavaFXAppController.getAnimationType());

        /*
         * All combo-boxes must be selecting something,
         * in order to press the button.
         */
        executeOrderButton.disableProperty()
                .bind(buySellComboBox.valueProperty().isNull()
                        .or(stockComboBox.valueProperty().isNull()
                                .or(orderTypeComboBox.valueProperty()
                                        .isNull())));

    }

    private void initSpinner() {
        if (buySellComboBox.valueProperty().getValue().toString()
                .equals("Sell")) {

            spinner.valueFactoryProperty().getValue().setValue(
                    stockComboBox.getValue()
                            .getQuantity(SelectedUser.getSelectedUser()));

        } else if (buySellComboBox.valueProperty().getValue().toString()
                .equals("Buy")) {

            spinner.valueFactoryProperty().getValue().setValue(0L);
        }
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
