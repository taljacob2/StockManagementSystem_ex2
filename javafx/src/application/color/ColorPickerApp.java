package application.color;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ColorPickerApp extends Application {

    // manual initialize to 'BLUEVIOLET'
    private static Color colorPicked = Color.BLUEVIOLET;

    private static boolean answer;

    @Deprecated public static void main(String[] args) {
        launch(args);
    }

    public static boolean getBoolean() { // TODO: this is a boolean picker.
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ColorPicker");

        Label label = new Label();
        label.setText("get a color now!");

        // create buttons:

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }

    public static Color getColor() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ColorPicker");

        Scene scene = new Scene(new HBox(20), 400, 100);
        HBox hBox = (HBox) scene.getRoot();
        hBox.setPadding(new Insets(5, 5, 5, 5));

        final ColorPicker colorPicker = new ColorPicker();
        // colorPicker.setValue(Color.WHITE);
        colorPicker.setValue(colorPicked);


        final Text text = new Text("Try the color picker!");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(colorPicker.getValue());

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
