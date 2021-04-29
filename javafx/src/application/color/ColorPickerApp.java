package application.color;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ColorPickerApp extends Application {

    public static Color colorPicked;

    @Deprecated public static void main(String[] args) {
        launch(args);
    }

    public static void getColor() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ColorPicker");

        Label label = new Label();
        label.setText("hey");
        HBox hBox = new HBox();

        // Scene scene = new Scene(new HBox(20), 400, 100);
        // HBox hBox = (HBox) scene.getRoot();
        // hBox.setPadding(new Insets(5, 5, 5, 5));
        //
        // final ColorPicker colorPicker = new ColorPicker();
        // colorPicker.setValue(Color.RED);
        //
        // final Text text = new Text("Try the color picker!");
        // text.setFont(Font.font("Verdana", 20));
        // text.setFill(colorPicker.getValue());
        //
        // colorPicker.setOnAction(event -> text.setFill(colorPicker.getValue()));

        // hBox.getChildren().addAll(colorPicker, text);
        hBox.getChildren().addAll(label);

        Scene scene = new Scene(hBox);

        window.setScene(scene);
        window.showAndWait();

        // return the color which the user has picked:
        // return colorPicker.getValue();
    }

    @Override public void start(Stage stage) {
        stage.setTitle("ColorPicker");
        Scene scene = new Scene(new HBox(20), 400, 100);
        HBox hBox = (HBox) scene.getRoot();
        hBox.setPadding(new Insets(5, 5, 5, 5));

        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.RED);

        final Text text = new Text("Try the color picker!");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(colorPicker.getValue());

        colorPicker.setOnAction(event -> text.setFill(colorPicker.getValue()));

        hBox.getChildren().addAll(colorPicker, text);

        // set the color to what the user has chosen:
        colorPicked = colorPicker.getValue();

        stage.setScene(scene);
        stage.show();
    }
}
