package application.color;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This {@code Class} is used for:
 * <ul>
 *     <li>picking a {@code Color} via a <i>pop-up</i> window.</li>
 *     <li>managing {@code static} methods to handle with {@link Color}s and
 *     {@link String}s.</li>
 * </ul>
 */
public class ColorPickerApp {

    /**
     * Manually initialize to {@code Color} <i>rgb(101, 96, 30);</i>.
     *
     * <p>Should be the same value as the <tt>#rseLabel</tt> role:</p>
     * <code>#rseLabel {
     * fx-background-color: rgb(101, 96, 30); }</code> in the <tt>.css</tt> file
     * of the {@code primaryStage} of {@link application.JavaFXApp}.
     */
    private static final ObjectProperty<Color> colorPicked =
            new SimpleObjectProperty<>(Color.rgb(101, 96, 30));


    /**
     * The <b>core</b> method of this {@code Class}. Calls {@link #getColor()}
     * to <i>pop-up</i> a <i>window</i> of choosing a {@code Color}.
     */
    public static void play() {

        // Get color via the color pop-up window:
        ColorPickerApp.getColor();
    }

    /**
     * This method <i>pops-up</i> a <i>window</i> of choosing a {@code Color}.
     */
    private static void getColor() {
        Stage window = new Stage();

        // User must handle the pop-up window:
        window.initModality(Modality.APPLICATION_MODAL);

        // Set window title:
        window.setTitle("Pick a Color");

        Scene scene = new Scene(new HBox(20), 400, 100);
        HBox hBox = (HBox) scene.getRoot();
        hBox.setPadding(new Insets(5, 5, 5, 5));

        final ColorPicker colorPicker = new ColorPicker();

        // Set an initial Color value:
        colorPicker.setValue(colorPicked.get());

        // Set text:
        final Text text = new Text("Choose a Custom Color!");
        text.setFont(Font.font("Sans-Serif", 20));
        text.setFill(colorPicker.getValue());

        // Set ColorPicker action after press:
        colorPicker.setOnAction(event -> {

            // Store the value in a variable Property:
            colorPicked.set(colorPicker.getValue());
            text.setFill(colorPicked.get());
        });
        hBox.getChildren().addAll(colorPicker, text);

        window.setScene(scene);
        window.showAndWait();
    }

    public static Color getColorPicked() {
        return colorPicked.get();
    }

    public static void setColorPicked(Color colorPicked) {
        ColorPickerApp.colorPicked.set(colorPicked);
    }

    public static ObjectProperty<Color> colorPickedProperty() {
        return colorPicked;
    }

    public static String getStringColor() {
        return ColorPickerApp.toStringColor(colorPicked.get());
    }

    public static String toStringColor(Color color) {

        // cut out the first two '0x' chars from the String:
        return color.toString().substring(2);
    }

    public static String toRGBString(double red, double green, double blue) {
        return String.format("rgb(%f, %f, %f)", red, green, blue);
    }

    public static String toRGBString() {
        return toRGBString(getRed(), getGreen(), getBlue());
    }

    public static String toRGBAString(double red, double green, double blue,
                                      double alpha) {
        return String.format("rgb(%f, %f, %f, %f)", red, green, blue, alpha);
    }

    public static String toRGBAString(double alpha) {
        return toRGBAString(getRed(), getGreen(), getBlue(), alpha);
    }

    public static double getRed() {
        return colorPicked.get().getRed() * 255;
    }

    public static double getGreen() {
        return colorPicked.get().getGreen() * 255;
    }

    public static double getBlue() {
        return colorPicked.get().getBlue() * 255;
    }
}
