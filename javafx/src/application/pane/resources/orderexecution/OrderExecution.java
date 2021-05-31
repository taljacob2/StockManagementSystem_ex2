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

    private Long activeMinQuantity = 1L;
    private Long activeMaxQuantity = Long.MAX_VALUE;

    @FXML private ComboBox<String> buySellComboBox;
    @FXML private ComboBox<Stock> stockComboBox;
    @FXML private ComboBox<String> orderTypeComboBox;
    @FXML private Button executeOrderButton;
    @FXML private TextField quantityTextField;
    @FXML private Label validityLabel;
    /**
     * Note: the when performing a "Buy" order, the 'max' limit of the {@code
     * quantity} is hardcoded to be: '1000000'.
     *
     * @see #initQuantity
     */
    private SpinnerValueFactory<Integer> spinnerValueFactory;
    @FXML private Label userNameLabel;


    public OrderExecution() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        buySellComboBox.getItems().addAll("Buy", "Sell");
        orderTypeComboBox.getItems().addAll("LMT", "MKT");


        buySellComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> initStockComboBox());

        stockComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> initQuantity());

        userNameLabel.setText(
                "Hello, " + SelectedUser.getSelectedUser().getName() + ". " +
                        "Please make an order.");

        quantityTextField.setTextFormatter(new TextFormatter<>(change -> {

            // Allow only numbers:
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;

        }));

        quantityTextField.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        if (Long.parseLong(newValue) > activeMaxQuantity) {
                            quantityTextField.setText(oldValue);
                        }

                        if (Long.parseLong(newValue) < activeMinQuantity) {
                            quantityTextField.setText(oldValue);
                        }
                    } catch (NumberFormatException e) {
                        MessagePrint.println(MessagePrint.Stream.ERR,
                                "Invalid [long] quantity.");
                        return;
                    }
                    if (quantityTextField.textProperty().getValue()
                            .matches("")) {
                        MessagePrint.println(MessagePrint.Stream.ERR,
                                "Invalid [long] quantity.");
                        return;
                    }
                    MessagePrint.println(MessagePrint.Stream.OUT,
                            "Valid [long] quantity.");
                });

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

        quantityTextField.disableProperty()
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
                                        .or(quantityTextField
                                                .disableProperty()))));
    }

    private void initQuantity() {
        if (buySellComboBox.valueProperty().getValue().toString()
                .equals("Sell") &&
                (stockComboBox.valueProperty().isNotNull().get())) {

            // "Sell" is being selected, and a "Stock" is being selected.
            activeMinQuantity = 1L;
            activeMaxQuantity = stockComboBox.getValue()
                    .getQuantity(SelectedUser.getSelectedUser());
        } else if (buySellComboBox.valueProperty().getValue().toString()
                .equals("Sell") &&
                (stockComboBox.valueProperty().isNull().get())) {

            // "Sell" is being selected, and a "Stock" is NOT being selected.
            activeMinQuantity = 0L;
            activeMaxQuantity = 0L;
            quantityTextField.setText(null);
        } else if (buySellComboBox.valueProperty().getValue().toString()
                .equals("Buy")) {

            // "Buy" is being selected.
            activeMinQuantity = 1L;
            activeMaxQuantity = Long.MAX_VALUE;
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
