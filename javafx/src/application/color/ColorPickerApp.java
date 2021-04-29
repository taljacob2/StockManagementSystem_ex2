package application.color;

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
 * This class is used for picking a {@code Color} via a <i>pop-up window</i>.
 */
public class ColorPickerApp {

    /**
     * Manually initialize to {@code Color} <i>BLUEVIOLET</i>.
     *
     * <p>Should be the same value as the <tt>.root</tt> role:</p>
     * <code>.root {
     * fx-background-color: blueviolet; }</code> in the <tt>.css</tt> file of
     * the {@code primaryStage} of {@link application.JavaFXApp}.
     */
    private static Color colorPicked = Color.BLUEVIOLET;

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

}
