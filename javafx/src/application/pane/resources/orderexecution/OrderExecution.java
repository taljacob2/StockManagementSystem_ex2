package application.pane.resources.orderexecution;

import application.javafxapp.JavaFXAppHandler;
import application.pane.resources.login.selecteduser.SelectedUser;
import engine.Engine;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.MenuUI;
import message.print.MessagePrint;
import order.OrderDirection;
import order.OrderType;
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

    private SimpleBooleanProperty quantityValidityState =
            new SimpleBooleanProperty(false);
    private SimpleBooleanProperty limitPriceValidityState =
            new SimpleBooleanProperty(false);

    @FXML private ComboBox<String> orderDirectionComboBox;
    @FXML private ComboBox<Stock> stockComboBox;
    @FXML private ComboBox<String> orderTypeComboBox;
    @FXML private Button executeOrderButton;
    @FXML private TextField quantityTextField;
    @FXML private TextField limitPriceTextField;
    @FXML private Label userNameLabel;

    private ChangeListener<String> textFieldChangeListner;

    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public OrderExecution() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        initComboBoxes();
        initUserNameLabel();
        initTextToLongNumbersOnly(quantityTextField, "'Quantity'",
                quantityValidityState);
        initTextToLongNumbersOnly(limitPriceTextField, "'Price'",
                limitPriceValidityState);

        Runnable executeOrderRunnable = new Runnable() {
            @Override public void run() {
                MenuUI.command_EXECUTE_TRANSACTION_ORDER(
                        stockComboBox.getValue(), OrderDirection.valueOf(
                                orderDirectionComboBox.getValue()
                                        .toUpperCase()), OrderType.valueOf(
                                orderTypeComboBox.getValue().toUpperCase()),
                        Long.parseLong(quantityTextField.getText()),
                        limitPriceTextField.getText().equalsIgnoreCase("MKT") ?
                                0L :
                                Long.parseLong(limitPriceTextField.getText()));

            }
        };

        new JavaFXAppHandler("/application/pane/resources/login/Login.fxml",
                executeOrderRunnable).handleOnce(executeOrderButton);

        initDisable();
    }

    private void initComboBoxes() {
        orderDirectionComboBox.getItems().addAll("Buy", "Sell");
        orderTypeComboBox.getItems().addAll("LMT", "MKT");

        orderDirectionComboBox.valueProperty().addListener(
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
        limitPriceTextField.setDisable(true);
        executeOrderButton.setDisable(true);

        // 'stockComboBox' depends on 'orderDirectionComboBox'.
        stockComboBox.disableProperty()
                .bind(orderDirectionComboBox.valueProperty().isNull()
                        .or(orderDirectionComboBox.disableProperty()));


        // 'orderTypeComboBox' depends on 'stockComboBox'.
        orderTypeComboBox.disableProperty()
                .bind(stockComboBox.valueProperty().isNull()
                        .or(stockComboBox.disableProperty()));

        // 'quantityTextField' depends on 'orderTypeComboBox'.
        quantityTextField.disableProperty()
                .bind(orderTypeComboBox.valueProperty().isNull()
                        .or(orderTypeComboBox.disableProperty()));

        // 'limitPriceTextField' depends on 'quantityTextField'.


        orderTypeComboBox.valueProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (orderTypeComboBox.getValue().equals("MKT")) {
                        limitPriceTextField.textFormatterProperty().setValue(
                                new TextFormatter<>(change -> change));
                        limitPriceTextField.textProperty()
                                .removeListener(textFieldChangeListner);
                        limitPriceTextField.setText("MKT");
                        limitPriceTextField.disableProperty().unbind();
                        limitPriceTextField.setDisable(true);
                        executeOrderButton.disableProperty().unbind();

                        /*
                         * In order to press the button,
                         * all combo-boxes must be enabled and select something,
                         * and all TextField inputs must be enabled,
                         * and their inputs must be valid.
                         */
                        executeOrderButton.disableProperty()
                                .bind(orderDirectionComboBox.disableProperty()
                                        .or(stockComboBox.disableProperty()
                                                .or(orderTypeComboBox
                                                        .disableProperty()
                                                        .or(quantityTextField
                                                                .disableProperty()
                                                                .or(quantityValidityState
                                                                        .not())))));

                    } else {
                        initTextToLongNumbersOnly(limitPriceTextField,
                                "'Price'", limitPriceValidityState);

                        limitPriceTextField.disableProperty().unbind();
                        limitPriceTextField.disableProperty()
                                .bind(quantityValidityState.not()
                                        .or(quantityTextField.disabledProperty()
                                                .or(quantityTextField
                                                        .textProperty()
                                                        .isNull())));
                        executeOrderButton.disableProperty().unbind();

                        /*
                         * In order to press the button,
                         * all combo-boxes must be enabled and select something,
                         * and all TextField inputs must be enabled but limitPriceTextField,
                         * and their inputs must be valid.
                         */
                        executeOrderButton.disableProperty()
                                .bind(orderDirectionComboBox.disableProperty()
                                        .or(stockComboBox.disableProperty()
                                                .or(orderTypeComboBox
                                                        .disableProperty()
                                                        .or(quantityTextField
                                                                .disableProperty()
                                                                .or(limitPriceTextField
                                                                        .disableProperty()
                                                                        .or(quantityValidityState
                                                                                .not())
                                                                        .or(limitPriceValidityState
                                                                                .not()))))));
                    }
                });


    }

    /**
     * This method enforces that the given number in the {@link TextField} would
     * be a <i>number</i>.
     * <p>
     * This method checks whether the given number within {@link TextField} is a
     * valid {@code long} number, that is in between the values of {@link
     * #activeMinQuantity} and {@link #activeMaxQuantity}.
     * <p>
     * This method checks the given number in <i>real-time</i>.
     *
     * @param textField the {@link TextField} to enforce its field to allow
     *                  numbers only.
     * @param field     the <i>name</i> of the field that is presented by the
     *                  {@link TextField} given. This is required in order to
     *                  print a <i>message</i> to the user, to inform what is
     *                  the validity of the <i>current</i> {@code TextField's
     *                  value}.
     * @param validity  updates the <i>validity</i> state.
     * @see #initTextQuantityFormatter(TextField)
     * @see #initTextQuantityMinMaxValidation(TextField, String,
     * SimpleBooleanProperty)
     */
    private void initTextToLongNumbersOnly(TextField textField, String field,
                                           SimpleBooleanProperty validity) {
        initTextQuantityFormatter(textField);
        initTextQuantityMinMaxValidation(textField, field, validity);
    }

    /**
     * This method enforces that the given number in the {@link TextField} would
     * be a <i>number</i>.
     *
     * @param textField the {@link TextField} to:
     *                  <ul>
     *                      <li>enforce its field to allow numbers only.</li>
     *                      <li>enforce its field to allow only numbers in
     *                      between the values of {@link #activeMinQuantity}
     *                      and {@link #activeMaxQuantity}.</li>
     *                  </ul>
     */
    private void initTextQuantityFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {

            // Allow only numbers:
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;

        }));
    }

    /**
     * This method checks whether the given number within {@link TextField} is a
     * valid {@code long} number, that is in between the values of {@link
     * #activeMinQuantity} and {@link #activeMaxQuantity}.
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
     *
     * @param textField the {@link TextField} to enforce its field to allow only
     *                  numbers in between the values of {@link
     *                  #activeMinQuantity} and {@link #activeMaxQuantity}.
     * @param field     the <i>name</i> of the field that is presented by the
     *                  {@link TextField} given. This is required in order to
     *                  print a <i>message</i> to the user, to inform what is
     *                  the validity of the <i>current</i> {@code TextField's
     *                  value}.
     * @param validity  updates the <i>validity</i> state.
     */
    private void initTextQuantityMinMaxValidation(TextField textField,
                                                  String field,
                                                  SimpleBooleanProperty validity) {


        textField.textProperty().addListener(
                textFieldChangeListner = new ChangeListener<String>() {
                    @Override public void changed(
                            ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        try {
                            if (Long.parseLong(newValue) > activeMaxQuantity) {
                                textField.setText(oldValue);
                            }
                            if (Long.parseLong(newValue) < activeMinQuantity) {
                                textField.setText(oldValue);
                            }
                        } catch (NumberFormatException e) {

                            // Means, the given number is invalid.
                            validity.setValue(false);
                            printInvalidErrorMessage(field);
                            return;
                        }
                        if (textField.textProperty().getValue().matches("")) {

                            // Means, there is no number given. Number is invalid.
                            validity.setValue(false);
                            printInvalidErrorMessage(field);
                            return;
                        }

                        // If the given number is valid.
                        validity.setValue(true);
                        printValidOutputMessage(field);
                    }
                });
    }

    private void printInvalidErrorMessage(String field) {
        MessagePrint.println(MessagePrint.Stream.ERR,
                "Invalid [long] " + field + "." +
                        "\nNumber needs to be between:" + " '" +
                        activeMinQuantity + "' and '" + activeMaxQuantity +
                        "'");
    }

    private void printValidOutputMessage(String field) {
        MessagePrint.println(MessagePrint.Stream.OUT,
                "Valid [long] " + field + ".");
    }

    private void initMinMaxQuantities() {
        if (orderDirectionComboBox.valueProperty().getValue().toString()
                .equals("Sell") &&
                (stockComboBox.valueProperty().isNotNull().get())) {

            // "Sell" is being selected, and a "Stock" is being selected.
            activeMinQuantity = 1L;
            activeMaxQuantity = stockComboBox.getValue()
                    .getQuantity(SelectedUser.getSelectedUser());
        } else if (orderDirectionComboBox.valueProperty().getValue().toString()
                .equals("Sell") &&
                (stockComboBox.valueProperty().isNull().get())) {

            // "Sell" is being selected, and a "Stock" is NOT being selected.
            activeMinQuantity = 1L;
            activeMaxQuantity = 1L;
            quantityTextField.setText("1");
        } else if (orderDirectionComboBox.valueProperty().getValue().toString()
                .equals("Buy")) {

            // "Buy" is being selected.
            activeMinQuantity = 1L;
            activeMaxQuantity = Long.MAX_VALUE;
        }
    }

    private void initStockComboBox() {

        // Remove all previous items in the stock-comboBox:
        stockComboBox.getItems().clear();

        if (orderDirectionComboBox.valueProperty().getValue().toString()
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

        } else if (orderDirectionComboBox.valueProperty().getValue().toString()
                .equals("Sell")) {

            // Show only the stock available in the user's items:
            stockComboBox.getItems()
                    .addAll(SelectedUser.getSelectedUser().getHoldings()
                            .getCollection().stream().map(Item::getStock)
                            .collect(Collectors.toList()));

        }
    }
}
