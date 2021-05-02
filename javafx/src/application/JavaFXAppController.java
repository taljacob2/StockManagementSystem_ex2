package application;

import application.color.ColorPickerApp;
import application.dialog.FxDialogs;
import application.property.NumberProperty;
import application.scene.PaneReplacer;
import application.scene.SceneHandler;
import engine.Engine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import main.MenuUI;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Main {@code Controller} of the <tt>JavaFX</tt> {@link JavaFXApp} program.
 *
 * <p>Note: <i>implements</i> {@link Initializable} in order to set the
 * <i>observers</i> <i>binded</i> to the {@link #progressBar}.</p>
 */
public class JavaFXAppController implements Initializable, PaneReplacer {

    /**
     * This editable field defines a {@code double} number that is binded to the
     * {@link #progressBar} via <i>observation</i>.
     * <p>
     * Edit this number with {@link NumberProperty#setNumber(double)} to set a
     * <i>value</i> to the {@link #progressBar}.
     */
    final private static NumberProperty progressBarDoubleNumber =
            new NumberProperty();

    /**
     * This editable field defines a {@code double} number that is binded to the
     * {@link #progressBar} via <i>observation</i>.
     * <p>
     * Edit this number with {@link NumberProperty#setNumber(double)} to set a
     * <i>value</i> to the {@link #progressBar}.
     */
    final private static NumberProperty fontSizeDoubleNumber =
            new NumberProperty();

    @FXML private BorderPane borderPane;

    @FXML private Label myMessage;

    @FXML private ProgressBar progressBar;

    /**
     * Contains the {@link javafx.scene.layout.AnchorPane} windows that are
     * being <i>replaced</i>.
     */
    @FXML private StackPane parentContainer = new StackPane();

    @FXML private Button printButton2 = new Button("PRINTBUTTON2");

    public static void closeRequest() {
        String answer =
                FxDialogs.showConfirm("Hello", "Are you sure?", "Yes", "No");
        if (answer.equals("Yes")) {
            System.exit(0);
        }
    }

    /**
     * This method updates the {@link #progressBar} value, through an
     * <i>iteration</i> of an '{@code Array'} or some kind of '{@code
     * Collection}'.
     *
     * @param i    is the current iteration value.
     * @param size is the {@code size} of the '{@code Array} or {@code
     *             Collection}' that is being iterated.
     */
    public static void setProgressBarValue(int i, int size) {
        progressBarDoubleNumber.setNumber((double) (i + 1) / size);
    }

    /**
     * This method resets the {@link #progressBar} value.
     */
    public static void resetProgressBar() {
        progressBarDoubleNumber.setNumber(0);
    }

    public void setFullScreen(ActionEvent event) {
        JavaFXApp.getStage().setFullScreen(true);
    }

    /**
     * The method calls a <i>pop-up window</i> for choosing a {@code Color}, and
     * afterwards {@code set}s the
     * <blockquote><code> -fx-background-color: </code></blockquote> of the
     * {@code root} of {@link JavaFXApp} in the <tt>.css</tt> file.
     *
     * @param event the {@code ActionEvent} of pressing the button.
     */
    public void getColor(ActionEvent event) {

        // get color via the color pop-up window:
        Color answer = ColorPickerApp.getColor();

        // cut out the first two '0x' chars from the String:
        String stringColor = answer.toString().substring(2);

        // set the root the updated style:
        JavaFXApp.getRoot()
                .setStyle("-fx-background-color: " + "#" + stringColor);

    }

    // TODO kill this
    public void generateRandom(ActionEvent event) {
        Random rand = new Random();
        int myRand = rand.nextInt(50) + 1;
        myMessage.setText(Integer.toString(myRand));
        progressBarDoubleNumber.setNumber(((double) myRand / 100) *
                2); // TODO: fix progress bar to sync into processes and not to the 'generate' method
    }

    public void command_LOAD_XML_FILE(ActionEvent event) {

        // set fileChooser Title:
        JavaFXApp.fileChooser.setTitle("Choose a '.xml' file");

        File file = JavaFXApp.fileChooser.showOpenDialog(null);
        MenuUI.command_LOAD_XML_FILE(file.getAbsolutePath());
    }

    public void command_PRINT_STOCKS(ActionEvent event) {
        MenuUI.command_PRINT_STOCKS();
    }

    public void command_INFO_ABOUT_A_STOCK(ActionEvent event) {
        MenuUI.command_INFO_ABOUT_A_STOCK();
    }

    public void command_EXECUTE_TRANSACTION_ORDER(ActionEvent event) {
        MenuUI.command_EXECUTE_TRANSACTION_ORDER();
    }

    public void command_PRINT_LISTS_OF_ALL_ORDERS_AND_TRANSACTIONS(
            ActionEvent event) {
        MenuUI.command_PRINT_LISTS_OF_ALL_ORDERS_AND_TRANSACTIONS();
    }

    public void command_SAVE_XML_FILE(ActionEvent event) {

        // if there are stocks in the system:
        if (Engine.isStocks()) {

            // set fileChooser Title:
            JavaFXApp.fileChooser
                    .setTitle("Choose where to save the '.xml' file");

            File file = JavaFXApp.fileChooser.showSaveDialog(null);
            MenuUI.command_SAVE_XML_FILE(file.getAbsolutePath());
        }
    }

    public void command_EXIT(ActionEvent event) {
        closeRequest();
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        parentContainer.getChildren()
                .add(getPane("/application/scene/StockTablePane.fxml"));
        borderPane.setCenter(parentContainer);

        printButton2.setOnAction(new SceneHandler(parentContainer,
                "/application/scene/StockTablePane.fxml"));

        //
        // replaceWith(event, buttonToScene2, parentContainer, anchorRoot,
        //         "/application/scene/Scene2.fxml");


        // initialize 'progressBarDoubleNumber':
        progressBarDoubleNumber.setNumber(0);

        // set all observers for 'progressBarDoubleNumber' property:
        progressBarDoubleNumber.getProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override public void changed(
                            ObservableValue<? extends Number> observable,
                            Number oldValue, Number newValue) {

                        // set bind of 'progressBar' to 'progressBarDoubleNumber' property:
                        progressBar.progressProperty()
                                .bind(progressBarDoubleNumber.getProperty());
                    }
                });

        // DoubleProperty fontSize = new SimpleDoubleProperty(12); // font size in pt
        // root.styleProperty().bind(
        //         Bindings.format("-fx-font-size: %.2fpt;", fontSize)); //TODO font slider

    }

    /**
     * This method <i>loads</i> a {@link Pane} of {@link
     * javafx.scene.control.TableView} of all the {@link stock.Stocks} in the
     * program, and shows it to the screen.
     */
    public void printStocksOnTableView() {

        // set the new pane to show:
        Pane view = getPane("/stocktable/StockTablePane.fxml");
        borderPane.setCenter(view);
    }
    //
    // public void printStocksOnTableView2() {
    //
    //
    //
    //
    //     // set the new pane to show:
    //     Pane parentStackPane = getPane("/stocktable/StockTablePane.fxml");
    //     borderPane.setCenter(parentStackPane);
    // }

}
