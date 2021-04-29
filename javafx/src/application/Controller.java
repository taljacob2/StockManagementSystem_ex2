package application;

import application.color.ColorPickerApp;
import application.dialog.FxDialogs;
import engine.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import main.MenuUI;

import java.io.File;
import java.util.Random;

/**
 * Main {@code Controller} for the <tt>JavaFX</tt> {@link JavaFXApp} program.
 */
public class Controller {

    @FXML private Label myMessage;

    public static void closeRequest() {
        String answer =
                FxDialogs.showConfirm("Hello", "Are you sure?", "Yes", "No");
        if (answer.equals("Yes")) {
            System.exit(0);
        }
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
        System.out.println(Integer.toString(myRand));
        myMessage.setText(Integer.toString(myRand));
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

}
