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

    /**
     * <p>A dynamical value.</p>
     * <p>
     * This value indicates the <i>minimum</i> <i>valid</i> {@code Quantity} in
     * the executed {@code Order}.
     * </p>
     * <p>
     * Mainly, this number is a {@code final} value of {@code 1}.
     */
    private Long activeMinQuantity = 1L;

    /**
     * <p>A dynamical value.</p>
     * <p>
     * This value indicates the <i>maximum</i> <i>valid</i> {@code Quantity} in
     * the executed {@code Order}.
     * </p>
     *
     * <ul>
     *     <li>In case the {@code Order} is a <i>Sell</i> {@code Order}, then
     *     this number is set to be the <i>maximum</i> {@code Quantity} of
     *     the current {@code Stock} being selected in the
     *     {@link SelectedUser#getSelectedUser()} .
     *     </li>
     *     <li>In case the {@code Order} is a <i>Buy</i> {@code Order}, then
     *     this number is set to be the {@code final}: {@link Long#MAX_VALUE}
     *     number.
     *     </li>
     * </ul>
     */
    private Long activeMaxQuantity = Long.MAX_VALUE;

    @FXML private ComboBox<String> buySellComboBox;
    @FXML private ComboBox<Stock> stockComboBox;
    @FXML private ComboBox<String> orderTypeComboBox;
    @FXML private Button executeOrderButton;
    @FXML private TextField quantityTextField;
    @FXML private Label validityLabel;
    @FXML private Label userNameLabel;


    public OrderExecution() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        initComboBoxes();
        initUserNameLabel();
        initTextQuantity();
        initExecuteOrderButton();
        initDisable();
    }

    private void initComboBoxes() {
        buySellComboBox.getItems().addAll("Buy", "Sell");
        orderTypeComboBox.getItems().addAll("LMT", "MKT");

        buySellComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> initStockComboBox());

        stockComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> initMinMaxQuantities());
    }

    private void initUserNameLabel() {
        userNameLabel.setText(
                "Hello, " + SelectedUser.getSelectedUser().getName() + ". " +
                        "Please make an order.");
    }

    private void initDisable() {

        // 'stockComboBox' depends on 'buySellComboBox'.
        stockComboBox.disableProperty()
                .bind(buySellComboBox.valueProperty().isNull()
                        .or(buySellComboBox.disableProperty()));


        // 'orderTypeComboBox' depends on 'stockComboBox'.
        orderTypeComboBox.disableProperty()
                .bind(stockComboBox.valueProperty().isNull()
                        .or(stockComboBox.disableProperty()));

        // 'quantityTextField' depends on 'orderTypeComboBox'.
        quantityTextField.disableProperty()
                .bind(orderTypeComboBox.valueProperty().isNull()
                        .or(orderTypeComboBox.disableProperty()));

        /*
         * All combo-boxes must be selecting something, in order to press the
         * button. Thus, 'executeOrderButton' depends on all the combo-boxes.
         */
        executeOrderButton.disableProperty()
                .bind(buySellComboBox.disableProperty()
                        .or(stockComboBox.disableProperty()
                                .or(orderTypeComboBox.disableProperty()
                                        .or(quantityTextField
                                                .disableProperty()))));
    }

    private void initTextQuantity() {
        initTextQuantityFormatter();
        initTextQuantityMinMaxValidation();
    }

    /**
     * This method enforces that the given number in the {@link
     * #quantityTextField} would be a <i>number</i>.
     */
    private void initTextQuantityFormatter() {
        quantityTextField.setTextFormatter(new TextFormatter<>(change -> {

            // Allow only numbers:
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;

        }));
    }

    /**
     * This method checks whether the given number within {@link
     * #quantityTextField} is a valid {@code long} number, that is in between
     * the values of {@link #activeMinQuantity} and {@link #activeMaxQuantity}.
     * <p>
     * This method checks the given number in <i>real-time</i>.
     * </p>
     * <ul>
     *      <li>In case the given number is <i>valid</i>, print an <i>output</i>
     *      message to the {@link MessagePrint.Stream#OUT} {@code Stream}.
     *      </li>
     *      <li>In case the given number is <i>invalid</i>, print an <i>error</i>
     *      message to the {@link MessagePrint.Stream#ERR} {@code Stream}.
     *      </li>
     * </ul>
     */
    private void initTextQuantityMinMaxValidation() {
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

                        // Means, the given number is invalid.
                        printInvalidErrorMessage();
                        return;
                    }
                    if (quantityTextField.textProperty().getValue()
                            .matches("")) {

                        // Means, there is no number given.
                        printInvalidErrorMessage();
                        return;
                    }

                    // If the given number is valid.
                    printValidOutputMessage();
                });
    }

    private void printInvalidErrorMessage() {
        MessagePrint.println(MessagePrint.Stream.ERR,
                "Invalid [long] 'Quantity'." + "\nNumber needs to be between:" +
                        " '" + activeMinQuantity + "' and '" +
                        activeMaxQuantity + "'");
    }

    private void printValidOutputMessage() {
        MessagePrint
                .println(MessagePrint.Stream.OUT, "Valid [long] 'Quantity'.");
    }

    private void initMinMaxQuantities() {
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

    private void initExecuteOrderButton() {
        setExecuteOrderButton(JavaFXAppController.getStaticBorderPane(),
                JavaFXAppController.getParentContainer(),
                JavaFXAppController.getAnimationType());
    }

    private void setExecuteOrderButton(BorderPane borderPane,
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
