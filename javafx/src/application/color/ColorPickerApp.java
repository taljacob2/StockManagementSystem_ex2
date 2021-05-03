package application.color;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is used for picking a {@code Color} via a <i>pop-up window</i>.
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
    private static Color colorPicked = Color.rgb(101, 96, 30);

    /**
     * This method <i>pops-up</i> a <i>window</i> of choosing a {@code Color}.
     *
     * @return The chosen {@link Color} by the user.
     */
    public static Color getColor() {
        Stage window = new Stage();

        // user must handle the pop-up window:
        window.initModality(Modality.APPLICATION_MODAL);

        // set window title:
        window.setTitle("Pick a Color");

        Scene scene = new Scene(new HBox(20), 400, 100);
        HBox hBox = (HBox) scene.getRoot();
        hBox.setPadding(new Insets(5, 5, 5, 5));

        final ColorPicker colorPicker = new ColorPicker();

        // set an initial Color value:
        colorPicker.setValue(colorPicked);

        // set text:
        final Text text = new Text("Choose a Custom Color!");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(colorPicker.getValue());

        // set ColorPicker action after press:
        colorPicker.setOnAction(event -> {

            // store the value in a variable:
            colorPicked = colorPicker.getValue();
            text.setFill(colorPicked);
        });
        hBox.getChildren().addAll(colorPicker, text);

        window.setScene(scene);
        window.showAndWait();

        // return the color which the user has picked:
        return colorPicked;
    }

    public static String getStringColor() {

        // get color via the color pop-up window:
        Color answer = ColorPickerApp.getColor();

        // cut out the first two '0x' chars from the String:
        return ColorPickerApp.trimColor(answer);
    }

    public static Color getColorPicked() {
        return colorPicked;
    }

    public static String getStringColorPicked() {
        return ColorPickerApp.trimColor(colorPicked);
    }

    public static String trimColor(Color color) {

        // cut out the first two '0x' chars from the String:
        return color.toString().substring(2);
    }

    public static void setStyleColor(Node node, String anyRole,
                                     String stringColor) {
        node.setStyle("-fx-background-color: " + "#" + stringColor);
    }
}
