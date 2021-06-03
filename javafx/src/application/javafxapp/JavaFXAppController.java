package application.javafxapp;

import application.color.ColorPickerApp;
import application.dialog.FxDialogs;
import application.pane.PaneAnimator;
import application.pane.PaneReplacerShortened;
import application.property.NumberProperty;
import engine.Engine;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.MenuUI;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main {@code Controller} of the <tt>JavaFX</tt> {@link JavaFXApp} program.
 * <p>
 * Sets {@link Pane}s as the {@link #borderPane}'s <i>CENTER</i>: via setting a
 * child {@link Pane} to the parent {@link #parentContainer}.
 *
 * <p>Note: <i>implements</i> {@link Initializable} in order to set the
 * <i>observers</i> <i>binded</i> to the {@link #progressBar} and
 * {@link ColorPickerApp#getColorPicked()}.</p>
 */
public class JavaFXAppController
        implements Initializable, PaneReplacerShortened {

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

    private static final ObjectProperty<Color> colorPicked =
            (ColorPickerApp.getColorPicked() == null) ?
                    new SimpleObjectProperty<>(Color.rgb(128, 179, 128)) :
                    new SimpleObjectProperty<>(ColorPickerApp.getColorPicked());

    private static final StringProperty rgbaString = new SimpleStringProperty(
            ColorPickerApp.toRGBAString(0, 0, 0, 0.75));

    private static final StringProperty rgbString = new SimpleStringProperty(
            ColorPickerApp.toRGBString(colorPicked.get()));

    private static final StringProperty stringColor = new SimpleStringProperty(
            ColorPickerApp.toStringColor(colorPicked.get()));

    /**
     * Contains {@link #replaceAblePane}. The {@link Pane} that is
     * <i>replace-able</i>.
     */
    @FXML private static StackPane parentContainer = new StackPane();

    /**
     * The <i>replace-able</i> {@link Pane}.<p> Note: needs to be updated for
     * every <i>replace</i> of {@code Pane}s.</p>
     */
    private static Pane replaceAblePane;
    private static Label staticStatusLabel;
    private static VBox staticMenuVBox;
    private static BorderPane staticBorderPane;
    private static PaneAnimator.AnimationType animationType =
            PaneAnimator.AnimationType.FADE_OUT_IN;

    @FXML private static Label staticProgressLabel;
    @FXML private BorderPane leftBorderPane;
    @FXML private AnchorPane bottomAnchorPane;
    @FXML private BorderPane bottomBorderPane;
    @FXML private Button loginButton;
    @FXML private Button printStocksButton;
    @FXML private Button viewLogButton;
    @FXML private Label statusLabel;
    @FXML private Label progressLabel;
    @FXML private MenuBar menuBar;
    @FXML private Label rseLabel;
    @FXML private BorderPane borderPane;
    @FXML private VBox menuVBox;
    @FXML private ProgressBar progressBar;

    public static PaneAnimator.AnimationType getAnimationType() {
        return animationType;
    }

    public static void setAnimationType(
            PaneAnimator.AnimationType animationType) {
        JavaFXAppController.animationType = animationType;
    }

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

    public static void setReplaceAblePane(Pane replaceAblePane) {
        JavaFXAppController.replaceAblePane = replaceAblePane;
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
            staticProgressLabel.setStyle("-fx-text-fill: " + rgbString.get());
        } else {

            /*
             * if the color is too dark:
             * set the staticProgressLabel the updated style:
             */
            staticProgressLabel.setStyle("-fx-text-fill: rgb(202, 200, 197)");
        }
    }

    public static VBox getStaticMenuVBox() {
        return staticMenuVBox;
    }

    public static BorderPane getStaticBorderPane() {
        return staticBorderPane;
    }

    public static String getRgbaString() {
        return rgbaStringProperty().get();
    }

    public static void setRgbaString(String rgbaString) {
        JavaFXAppController.rgbaStringProperty().set(rgbaString);
    }

    public static StringProperty rgbaStringProperty() {
        return rgbaString;
    }

    public static String getRgbString() {
        return rgbString.get();
    }

    public static void setRgbString(String rgbString) {
        JavaFXAppController.rgbString.set(rgbString);
    }

    public static StringProperty rgbStringPropertyProperty() {
        return rgbString;
    }

    public static String getStringColor() {
        return stringColor.get();
    }

    public static void setStringColor(String stringColor) {
        JavaFXAppController.stringColor.set(stringColor);
    }

    public static StringProperty stringColorPropertyProperty() {
        return stringColor;
    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        /*
         * by default disable the menuVBox.
         * keep it disabled until there are loaded Stocks in the system.
         */
        menuVBox.setDisable(true);

        // Set static variables
        setStaticVariables();

        /*
         * Set Initial Pane:
         * Note: this is not a must
         */
        setPane("/application/pane/resources/welcome/Welcome.fxml");

        // Define Buttons.
        defineAnimationToAllButtons();

        /* -- Properties -- */

        initProgressBar();

        // DoubleProperty fontSize = new SimpleDoubleProperty(12); // font size in pt
        // root.styleProperty().bind(
        //         Bindings.format("-fx-font-size: %.2fpt;", fontSize)); //TODO font slider

        firstColorBind();
        InitMenuVBox();
        secondColorBind();
    }

    private void firstColorBind() {
        colorPicked.bind(ColorPickerApp.colorPickedProperty());
        colorPicked.addListener((observable, oldValue, newValue) -> {
            colorPicked.unbind();
            colorPicked
                    .setValue(ColorPickerApp.colorPickedProperty().getValue());
            rgbaString.set(ColorPickerApp.toRGBAString(colorPicked.get(), 0.5));
            rgbString.set(ColorPickerApp.toRGBString(colorPicked.get()));
            stringColor.set(ColorPickerApp.getStringColor(colorPicked.get()));
            colorPicked.bind(ColorPickerApp.colorPickedProperty());
        });
    }

    private void initProgressBar() {

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
    }

    private void setStaticVariables() {
        staticMenuVBox = menuVBox;
        staticStatusLabel = statusLabel;
        staticProgressLabel = progressLabel;
        staticBorderPane = borderPane;
    }

    private void InitMenuVBox() {
        menuVBox.setStyle("-fx-background-color: " + getRgbaString());
        String formatString = "-fx-text-fill: " + "#" + stringColor.get();
        progressLabel.setStyle(formatString);

        // Set initial color of 'menuVBox':
        rgbaString.addListener((observable, oldValue, newValue) -> {
            menuVBox.setStyle("-fx-background-color: " + getRgbaString());
        });
    }

    private void secondColorBind() {
        rgbaString.addListener((observable, oldValue, newValue) -> {

            // Set text Color and background Color of label rseLabel:
            setRseLabelColor();

            // Set staticProgressLabel the updated style:
            setProgressLabelColor();
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

        // Play the color pop-up window:
        ColorPickerApp.play();

        /* -- Set Style Colors -- */

        // Set menuBar the updated style:
        menuBar.styleProperty().bind(Bindings.when(menuBar.hoverProperty())
                .then(new SimpleStringProperty(
                        "-fx-background-color: " + rgbaString.get() + ";"))
                .otherwise(new SimpleStringProperty(
                        "-fx-background-color: " + "rgba(0, 0, 0, 0.75)" +
                                ";")));
    }

    private void setRseLabelColor() {
        String formatString = "";
        if ((ColorPickerApp.getRed(colorPicked.get()) >= 150) ||
                (ColorPickerApp.getGreen(colorPicked.get()) > 150) ||
                (ColorPickerApp.getBlue(colorPicked.get()) > 150)) {

            /*
             * if the background is bright:
             * set the rseLabel the updated style:
             */
            formatString = "-fx-text-fill: black;";
            if (stringColor.get() == null) {
                stringColor
                        .set(ColorPickerApp.toStringColor(colorPicked.get()));
            }

        } else {

            /*
             * if the background is dark:
             * set the rseLabel the updated style:
             */
            formatString = "-fx-text-fill: rgb(202,200,197);";
            if (stringColor.get() == null) {
                stringColor
                        .set(ColorPickerApp.toStringColor(colorPicked.get()));
            }
        }
        formatString += "-fx-background-color: " + "#" + stringColor.get();
        rseLabel.setStyle(formatString);
    }

    /**
     * This method <i>loads</i> a {@link Pane} of {@link
     * javafx.scene.control.TableView} of all the {@link stock.Stocks} in the
     * program, and shows it to the screen.
     */
    public void printStocksOnTableView() { //TODO: kill

        // // set the new pane to show:
        // Pane view =
        //         getPane("/application/scene/HoldingsTablePane.fxml");//todo: check if can be replaced with: getClass().getResource(pathToFXML)
        // borderPane.setCenter(view);
        //
        // // TODO: check this is `double` lifting the pane up
        // System.out.println("printStockOnTableView Pressed");

        setPane("/application/pane/resources/stocktablepane/StockTablePane.fxml");
    }

    public void command_LOAD_XML_FILE(ActionEvent event) {

        // set fileChooser Title:
        JavaFXApp.fileChooser.setTitle("Choose a '.xml' file");
        try {
            File file = JavaFXApp.fileChooser.showOpenDialog(null);
            MenuUI.command_LOAD_XML_FILE(file.getAbsolutePath());
        } catch (java.lang.NullPointerException catchIgnored) {

            /* In case the fileChooser.showOpenDialog was canceled, ignore. */
        }
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

            try {
                File file = JavaFXApp.fileChooser.showSaveDialog(null);
                MenuUI.command_SAVE_XML_FILE(file.getAbsolutePath());
            } catch (java.lang.NullPointerException catchIgnored) {

                /* In case the fileChooser.showSaveDialog was canceled, ignore. */
            }

        }
    }

    public void command_EXIT(ActionEvent event) {
        closeRequest();
    }

    /**
     * This method sets the new Pane to be shown on the <i>CENTER</i> of the
     * {@link #borderPane} and <i>updates</i> the {@link #replaceAblePane}
     * accordingly.
     *
     * <p>
     * Note: there is no {@link javafx.animation.Animation} in this method.
     *
     * @param pathToFXML path to the <tt>.fxml</tt> of the pane the user wishes
     *                   to show.
     */
    public void setPane(String pathToFXML) {
        setPane(borderPane, parentContainer, pathToFXML);
    }

    private void defineAnimationToAllButtons() {

        // define 'printStocksButton':
        JavaFXAppHandler.handle(printStocksButton,
                "/application/pane/resources/stocktablepane/StockTablePane.fxml");

        // define 'loginButton':
        JavaFXAppHandler.handle(loginButton,
                "/application/pane/resources/login/Login.fxml");

        // define 'viewLogButton':
        JavaFXAppHandler.handle(viewLogButton,
                "/application/pane/resources/viewlog/ViewLog.fxml");
    }

    @FXML private void defineAnimationFadeInOut() {
        animationType = PaneAnimator.AnimationType.FADE_IN_OUT;
        defineAnimationToAllButtons();
    }

    @FXML private void defineAnimationFadeOutIn() {
        animationType = PaneAnimator.AnimationType.FADE_OUT_IN;
        defineAnimationToAllButtons();
    }

    @FXML private void defineAnimationTimelineBottomToTop() {
        animationType = PaneAnimator.AnimationType.TIMELINE_BOTTOM_TO_TOP;
        defineAnimationToAllButtons();
    }

    @FXML private void defineAnimationTimelineRightToLeft() {
        animationType = PaneAnimator.AnimationType.TIMELINE_RIGHT_TO_LEFT;
        defineAnimationToAllButtons();
    }

    @FXML private void defineAnimationNone() {
        animationType = PaneAnimator.AnimationType.NONE;
        defineAnimationToAllButtons();
    }

}

