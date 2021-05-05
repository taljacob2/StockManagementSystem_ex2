package application;

import application.color.ColorPickerApp;
import application.dialog.FxDialogs;
import application.pane.PaneReplacer;
import application.pane.handler.PaneAnimationHandler;
import application.property.NumberProperty;
import engine.Engine;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.MenuUI;

import java.io.File;
import java.net.URL;
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
    final public static StringProperty messageProperty =
            new SimpleStringProperty();

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
            new NumberProperty();//FIXME need to fix/kill all of this

    private static final StringProperty rgbaStringProperty =
            new SimpleStringProperty();

    private static final StringProperty rgbStringProperty =
            new SimpleStringProperty();

    private static final StringProperty stringColorProperty =
            new SimpleStringProperty();

    /**
     * Contains {@link #replaceAblePane}. The {@link Pane} that is
     * <i>replace-able</i>.
     */
    @FXML private static StackPane parentContainer = new StackPane();

    /**
     * the <i>replace-able</i> {@link Pane}.
     */
    private static Pane replaceAblePane;

    @FXML private static Label staticStatusLabel;

    @FXML private static Label staticProgressLabel;

    @FXML private BorderPane leftBorderPane;
    @FXML private BorderPane bottomBorderPane;
    @FXML private Button printStocksButton = new Button();
    @FXML private Button ownProfileButton = new Button();
    @FXML private Button loginButton = new Button();
    @FXML private Label statusLabel;
    @FXML private Label progressLabel; // TODO: can make it static trick.
    @FXML private MenuBar menuBar;
    @FXML private Label rseLabel;
    @FXML private BorderPane borderPane;
    @FXML private VBox menuVBox;
    @FXML private ProgressBar progressBar;

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

    public static Pane getReplaceAblePane() {
        return replaceAblePane;
    }

    public static StackPane getParentContainer() {
        return parentContainer;
    }

    public static Label getStaticStatusLabel() {
        return staticStatusLabel;
    }

    private static void setProgressLabelColor() {

        double sumOfColors =
                ColorPickerApp.getRed() + ColorPickerApp.getGreen() +
                        ColorPickerApp.getBlue();

        if (sumOfColors > 150) {

            /*
             * if the color is dark enough:
             * set the staticProgressLabel the updated style:
             */
            staticProgressLabel
                    .setStyle("-fx-text-fill: " + rgbStringProperty.get());
        } else {

            /*
             * if the color is too dark:
             * set the staticProgressLabel the updated style:
             */
            staticProgressLabel.setStyle("-fx-text-fill: rgb(202, 200, 197)");
        }
    }

    // TODO kill this
    // public void generateRandom(ActionEvent event) {
    //     Random rand = new Random();
    //     int myRand = rand.nextInt(50) + 1;
    //     myMessage.setText(Integer.toString(myRand));
    //     progressBarDoubleNumber.setNumber(((double) myRand / 100) *
    //             2); // TODO: fix progress bar to sync into processes and not to the 'generate' method
    // }

    @Override public void initialize(URL location, ResourceBundle resources) {

        staticStatusLabel = statusLabel;
        staticProgressLabel = progressLabel;

        // set the rseLabel the initial style:
        ColorPickerApp.setStringStyleColor(rseLabel, "-fx-background-color: ",
                ColorPickerApp.getStringColor());

        /* -- set Initial Pane -- */

        /*
         * set an initial Pane in the borderPane's CENTER:
         * via setting a child Pane to the parent StackPane:
         * Note: this is not a must, but only to set an initial Pane.
         */
        setPane("/application/scene/Welcome.fxml");// TODO remove check

        /* -- define Buttons -- */

        // define 'printStocksButton':
        printStocksButton.setOnAction(
                new PaneAnimationHandler(borderPane, parentContainer,
                        "/application/scene/StockTablePane.fxml",
                        PaneAnimationHandler.AnimationType.TIMELINE));

        // define 'ownProfileButton':
        ownProfileButton.setOnAction(
                event -> setPane("/application/scene/OwnProfile.fxml"));

        // define 'loginButton':
        loginButton.setOnAction(
                new PaneAnimationHandler(borderPane, parentContainer,
                        "/application/scene/Login.fxml",
                        PaneAnimationHandler.AnimationType.FADE));


        /* -- Properties -- */

        // initialize 'progressBarDoubleNumber':
        progressBarDoubleNumber.setNumber(0);

        // set all Observers of 'progressBarDoubleNumber':
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


        // messageProperty.addListener(new ChangeListener<String>() {
        //
        //     @Override
        //     public void changed(ObservableValue<? extends String> observable,
        //                         String oldValue, String newValue) {
        //
        //         // set bind of 'statusLabel' to 'messageProperty' property:
        //         statusText.textProperty().bind(messageProperty);
        //     }
        // });


        // Translate: set Observers of 'ColorPickerApp.colorPickedProperty()':
        ColorPickerApp.colorPickedProperty()
                .addListener((observable, oldValue, newValue) -> {

                    rgbaStringProperty.set(ColorPickerApp.toRGBAString(0.5));
                    rgbStringProperty.set(ColorPickerApp.toRGBString());
                    stringColorProperty.set(ColorPickerApp.getStringColor());
                });
    }

    public void setFullScreen(ActionEvent event) {
        JavaFXApp.getStage().setFullScreen(true);
    }

    /**
     * The method calls a <i>pop-up window</i> for choosing a {@code Color}, and
     * afterwards {@code set}s the
     * <blockquote><code> -fx-background-color: </code></blockquote>
     * <blockquote><code> -fx-text-fill: </code></blockquote> {@code
     * Components} of {@link JavaFXApp} in the <tt>.css</tt> file.
     *
     * @param event the {@code ActionEvent} of pressing the button.
     */
    public void setColor(ActionEvent event) {

        // get color via the color pop-up window into a String:
        ColorPickerApp.playAndGetStringColor();


        /* -- Set Style Colors -- */

        // set menuBar the updated style:
        menuBar.styleProperty().bind(Bindings.when(menuBar.hoverProperty())
                .then(new SimpleStringProperty(
                        "-fx-background-color: " + rgbaStringProperty.get() +
                                ";")).otherwise(new SimpleStringProperty(
                        "-fx-background-color: " + "rgba(0, 0, 0, 0.75)" +
                                ";")));

        // set root the updated style: //TODO kill this
        // ColorPickerApp.setStringStyleColor(JavaFXApp.getRoot(),
        //         "-fx-background-color: ", stringColor);

        // set menuVBox the updated style:
        menuVBox.setStyle("-fx-background-color: " + rgbaStringProperty.get());

        // set staticProgressLabel the updated style:
        setProgressLabelColor();

        // set text Color and background Color of label rseLabel:
        setRseLabelColor();

    }

    private void setRseLabelColor() {
        //TODO: may make this method a `static` method by doing the `static` label copy trick.
        if ((ColorPickerApp.getRed() >= 150) ||
                (ColorPickerApp.getGreen() > 150) ||
                (ColorPickerApp.getBlue() > 150)) {

            /*
             * if the background is bright:
             * set the rseLabel the updated style:
             */
            String formatString =
                    "-fx-text-fill: black;" + "-fx-background-color: " + "#" +
                            stringColorProperty.get();
            rseLabel.setStyle(formatString);
        } else {

            /*
             * if the background is dark:
             * set the rseLabel the updated style:
             */
            String formatString = "-fx-text-fill: rgb(202, 200, 197);" +
                    "-fx-background-color: " + "#" + stringColorProperty.get();
            rseLabel.setStyle(formatString);
        }
    }

    /**
     * This method <i>loads</i> a {@link Pane} of {@link
     * javafx.scene.control.TableView} of all the {@link stock.Stocks} in the
     * program, and shows it to the screen.
     */
    public void printStocksOnTableView() { //TODO: kill

        // // set the new pane to show:
        // Pane view =
        //         getPane("/application/scene/StockTablePane.fxml");//todo: check if can be replaced with: getClass().getResource(pathToFXML)
        // borderPane.setCenter(view);
        //
        // // TODO: check this is `double` lifting the pane up
        // System.out.println("printStockOnTableView Pressed");

        setPane("/application/scene/StockTablePane.fxml");
    }

    public void scene1() {
        setPane("/application/scene/Scene1.fxml");
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

    /**
     * This method sets the new Pane to be shown on the <i>center</i> of the
     * {@link #borderPane} and <i>updates</i> the {@link #replaceAblePane}
     * accordingly.
     *
     * @param pathToFXML path to the <tt>.fxml</tt> of the pane the user wishes
     *                   to show.
     */
    private void setPane(String pathToFXML) {
        setPane(borderPane, parentContainer, replaceAblePane, pathToFXML);
    }

}

