package application.pane.resources.orderexecution;

import application.dialog.FxDialogs;
import application.pane.resources.login.selecteduser.SelectedUser;
import engine.Engine;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
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
     * States if the current inputted {@code quantity} value in the {@link
     * #quantityTextField} is <i>valid</i> or not.
     */
    private final SimpleBooleanProperty quantityValidityState =
            new SimpleBooleanProperty(false);

    /**
     * States if the current inputted {@code LimitPrice} value in the {@link
     * #limitPriceTextField} is <i>valid</i> or not.
     */
    private final SimpleBooleanProperty limitPriceValidityState =
            new SimpleBooleanProperty(false);

    /**
     * <p>A dynamical value.</p>
     * <p>
     * This value indicates the <i>minimum</i> <i>valid</i> {@code Quantity} in
     * the executed {@code Order}.
     * </p>
     * <p>
     * Mainly, this number is a {@code final} value of {@code 1}.
     */
    private SimpleLongProperty activeMinQuantityValue =
            new SimpleLongProperty(1L);
    ;

    /**
     * <p>A dynamical value.</p>
     * <p>
     * This value indicates the <i>minimum</i> <i>valid</i> {@code LimitPrice}
     * in the executed {@code Order}.
     * </p>
     * <p>
     * Mainly, this number is a {@code final} value of {@code 1}.
     */
    private SimpleLongProperty activeMinLimitPriceValue =
            new SimpleLongProperty(1L);

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
    private SimpleLongProperty activeMaxQuantityValue =
            new SimpleLongProperty(Long.MAX_VALUE);

    /**
     * <p>A dynamical value.</p>
     * <p>
     * This value indicates the <i>maximum</i> <i>valid</i> {@code LimitPrice}
     * in the executed {@code Order}.
     * </p>
     *
     * <ul>
     *     <li>In case the {@code Order} is a <i>Sell</i> {@code Order}, then
     *     this number is set to be the <i>maximum</i> {@code LimitPrice} of
     *     the current {@code Stock} being selected in the
     *     {@link SelectedUser#getSelectedUser()} .
     *     </li>
     *     <li>In case the {@code Order} is a <i>Buy</i> {@code Order}, then
     *     this number is set to be the {@code final}: {@link Long#MAX_VALUE}
     *     number.
     *     </li>
     * </ul>
     */
    private SimpleLongProperty activeMaxLimitPriceValue =
            new SimpleLongProperty(Long.MAX_VALUE);

    @FXML private Label userNameLabel;
    @FXML private ComboBox<String> orderDirectionComboBox;
    @FXML private ComboBox<Stock> stockComboBox;
    @FXML private ComboBox<String> orderTypeComboBox;
    @FXML private Button executeOrderButton;
    @FXML private TextField quantityTextField;
    @FXML private TextField limitPriceTextField;

    /**
     * The {@link Runnable} that its {@link Runnable#run()} method would be
     * <i>invoked</i> when clicking the {@link #executeOrderButton}.
     */
    private final Runnable executeOrderRunnable = new Runnable() {
        @Override public void run() {
            MenuUI.command_EXECUTE_TRANSACTION_ORDER(stockComboBox.getValue(),
                    OrderDirection.valueOf(
                            orderDirectionComboBox.getValue().toUpperCase()),
                    OrderType.valueOf(
                            orderTypeComboBox.getValue().toUpperCase()),
                    Long.parseLong(quantityTextField.getText()),
                    (limitPriceTextField.getText() == null) ||
                            (limitPriceTextField.getText()
                                    .equalsIgnoreCase("MKT")) ? 0L :
                            Long.parseLong(limitPriceTextField.getText()));

        }
    };

    /**
     * Save here the {@link ChangeListener} that is being invoked in {@link
     * #initMinMaxQuantityValues()} to be able to <i>remove</i> it via {@link
     * javafx.beans.property.Property#removeListener(ChangeListener)} within
     * {@link #initDependencyOfMKTBranch()}.
     */
    private ChangeListener<String> textFieldChangeListener;

    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public OrderExecution() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        initUserNameLabel();
        initComboBoxes();
        initTextFields();

        // new JavaFXAppHandler("/application/pane/resources/login/Login.fxml",
        //         executeOrderRunnable).handleOnce(executeOrderButton);

        executeOrderButton.setOnAction(event -> {
            event.consume();

            String answer = FxDialogs
                    .showConfirm("Order Confirmation", "Are you sure?", "Yes",
                            "No");
            if (answer.equals("Yes")) {
                executeOrderRunnable.run();
            }
        });

        initDisable();
    }

    private void initTextFields() {
        initTextFieldValues();
        initTextToLongNumbersOnly(limitPriceTextField, "'Price'",
                limitPriceValidityState, activeMinLimitPriceValue,
                activeMaxLimitPriceValue);
        initTextToLongNumbersOnly(quantityTextField, "'Quantity'",
                quantityValidityState, activeMinQuantityValue,
                activeMaxQuantityValue);
    }

    private void initTextFieldValues() {
        orderDirectionComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> initStockComboBox());
        stockComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> initMinMaxQuantityValues());
        initDependencyOfMKT();
    }

    private void initComboBoxes() {
        orderDirectionComboBox.getItems().addAll("Buy", "Sell");
        orderTypeComboBox.getItems().addAll("LMT", "MKT");
    }

    private void initUserNameLabel() {
        userNameLabel.setText("Please make an order.");
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
    }

    private void initDependencyOfMKT() {
        orderTypeComboBox.valueProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (orderTypeComboBox.getValue().equals("MKT")) {
                        initDependencyOfMKTBranch();
                    } else {
                        initDependencyOfNonMKTBranch();
                    }
                });

    }

    private void initDependencyOfMKTBranch() {

        // Removing old format listener:
        limitPriceTextField.textFormatterProperty()
                .setValue(new TextFormatter<>(change -> change));
        limitPriceTextField.textProperty()
                .removeListener(textFieldChangeListener);

        // Setting the new settings:
        // limitPriceTextField.setText("MKT");
        limitPriceTextField.setText(null);
        limitPriceTextField.setPromptText("MKT");
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
                                .or(orderTypeComboBox.disableProperty()
                                        .or(quantityTextField.disableProperty()
                                                .or(quantityValidityState
                                                        .not())))));
    }

    private void initDependencyOfNonMKTBranch() {

        // Setting the new settings:
        limitPriceTextField.setPromptText("Desired Limit Price");
        initTextToLongNumbersOnly(limitPriceTextField, "'Price'",
                limitPriceValidityState, activeMinLimitPriceValue,
                activeMaxLimitPriceValue);
        limitPriceTextField.disableProperty().unbind();
        limitPriceTextField.disableProperty().bind(quantityValidityState.not()
                .or(quantityTextField.disabledProperty()
                        .or(quantityTextField.textProperty().isNull())));
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
                                .or(orderTypeComboBox.disableProperty()
                                        .or(quantityTextField.disableProperty()
                                                .or(limitPriceTextField
                                                        .disableProperty()
                                                        .or(quantityValidityState
                                                                .not())
                                                        .or(limitPriceValidityState
                                                                .not()))))));
    }

    /**
     * This method enforces that the given number in the {@link TextField} would
     * be a <i>number</i>.
     * <p>
     * This method checks whether the given number within {@link TextField} is a
     * valid {@code long} number, that is in between the values of {@link
     * #activeMinQuantityValue} and {@link #activeMaxQuantityValue}.
     * <p>
     * This method checks the given number in <i>real-time</i>.
     *
     * @param textField      the {@link TextField} to enforce its field to allow
     *                       numbers only.
     * @param field          the <i>name</i> of the field that is presented by
     *                       the {@link TextField} given. This is required in
     *                       order to print a <i>message</i> to the user, to
     *                       inform what is the validity of the <i>current</i>
     *                       {@code TextField's value}.
     * @param validity       updates the <i>validity</i> state.
     * @param activeMinValue the <i>value</i> that the {@link TextField} should
     *                       be <tt>greater than or equal to</tt> this value.
     * @param activeMaxValue the <i>value</i> that the {@link TextField} should
     *                       be <tt>less than or equal to</tt> this value.
     * @see #initTextFormatter(TextField)
     * @see #initTextMinMaxValueValidation(TextField, String,
     * SimpleBooleanProperty, SimpleLongProperty, SimpleLongProperty)
     */
    private void initTextToLongNumbersOnly(TextField textField, String field,
                                           SimpleBooleanProperty validity,
                                           SimpleLongProperty activeMinValue,
                                           SimpleLongProperty activeMaxValue) {
        initTextFormatter(textField);
        initTextMinMaxValueValidation(textField, field, validity,
                activeMinValue, activeMaxValue);
    }

    /**
     * This method enforces that the given number in the {@link TextField} would
     * be a <i>number</i>.
     *
     * @param textField the {@link TextField} to:
     *                  <ul>
     *                      <li>enforce its field to allow numbers only.</li>
     *                      <li>enforce its field to allow only numbers in
     *                      between the values of {@link #activeMinQuantityValue}
     *                      and {@link #activeMaxQuantityValue}.</li>
     *                  </ul>
     */
    private void initTextFormatter(TextField textField) {
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
     * #activeMinQuantityValue} and {@link #activeMaxQuantityValue}.
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
     * @param textField      the {@link TextField} to enforce its field to allow
     *                       only numbers in between the values of {@link
     *                       #activeMinQuantityValue} and {@link
     *                       #activeMaxQuantityValue}.
     * @param field          the <i>name</i> of the field that is presented by
     *                       the {@link TextField} given. This is required in
     *                       order to print a <i>message</i> to the user, to
     *                       inform what is the validity of the <i>current</i>
     *                       {@code TextField's value}.
     * @param validity       updates the <i>validity</i> state.
     * @param activeMinValue the <i>value</i> that the {@link TextField} should
     *                       be <tt>greater than or equal to</tt> this value.
     * @param activeMaxValue the <i>value</i> that the {@link TextField} should
     *                       be <tt>less than or equal to</tt> this value.
     */
    private void initTextMinMaxValueValidation(TextField textField,
                                               String field,
                                               SimpleBooleanProperty validity,
                                               SimpleLongProperty activeMinValue,
                                               SimpleLongProperty activeMaxValue) {
        textField.textProperty().addListener(
                textFieldChangeListener = new ChangeListener<String>() {
                    @Override public void changed(
                            ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        try {
                            if (Long.parseLong(newValue) >
                                    activeMaxValue.get()) {
                                textField.setText(oldValue);
                            }
                            if (Long.parseLong(newValue) <
                                    activeMinValue.get()) {
                                textField.setText(oldValue);
                            }
                        } catch (Exception e) {

                            // Means, the given number is invalid.
                            validity.setValue(false);
                            printInvalidErrorMessage(field,
                                    activeMinValue.get(), activeMaxValue.get());
                            return;
                        }
                        if (textField.textProperty().getValue().matches("")) {

                            // Means, there is no number given. Number is invalid.
                            validity.setValue(false);
                            printInvalidErrorMessage(field,
                                    activeMinValue.get(), activeMaxValue.get());
                            return;
                        }

                        // If the given number is valid.
                        validity.setValue(true);
                        printValidOutputMessage(field);
                    }
                });
    }

    private void printInvalidErrorMessage(String field, Long activeMinValue,
                                          Long activeMaxValue) {
        MessagePrint.println(MessagePrint.Stream.ERR,
                "Invalid [long] " + field + "." +
                        "\nNumber needs to be between:" + " '" +
                        activeMinValue + "' and '" + activeMaxValue + "'",
                false);
    }

    private void printValidOutputMessage(String field) {
        MessagePrint
                .println(MessagePrint.Stream.OUT, "Valid [long] " + field + ".",
                        false);
    }

    private void initMinMaxQuantityValues() {
        if (orderDirectionComboBox.valueProperty().getValue().toString()
                .equals("Sell") &&
                (stockComboBox.valueProperty().isNotNull().get())) {

            // "Sell" is being selected, and a "Stock" is being selected.
            activeMinQuantityValue.setValue(1L);
            activeMaxQuantityValue.setValue(stockComboBox.getValue()
                    .getQuantity(SelectedUser.getSelectedUser()));
        } else if (orderDirectionComboBox.valueProperty().getValue().toString()
                .equals("Sell") &&
                (stockComboBox.valueProperty().isNull().get())) {

            // "Sell" is being selected, and a "Stock" is NOT being selected.
            activeMinQuantityValue.setValue(1L);
            activeMaxQuantityValue.setValue(1L);
            quantityTextField.setText("1");
        } else if (orderDirectionComboBox.valueProperty().getValue().toString()
                .equals("Buy")) {

            // "Buy" is being selected.
            activeMinQuantityValue.setValue(1L);
            activeMaxQuantityValue.setValue(Long.MAX_VALUE);
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
