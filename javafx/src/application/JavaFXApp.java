package application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;

/**
 * The main <tt>JavaFX</tt> {@code Application}.
 *
 * @author Tal Yacob, ID: 208632778
 * @version 1.0
 */
public class JavaFXApp extends Application {

    public static final String currentPath =
            Paths.get(".").toAbsolutePath().normalize().toString();
    public static FileChooser fileChooser;

    static {

        // create the fileChooser:
        fileChooser = new FileChooser();

        // set fileChooser Title:
        fileChooser.setTitle("Choose a '.xml' file");

        // set fileChooser to start on current path:
        fileChooser.setInitialDirectory(new File(currentPath));

        // define extensionFilters:
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));

    }

    @FXML private MenuItem customTheme;

    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tal Yacob RSE (V2.0)");

        // set root from an FXML file:
        Parent root = FXMLLoader.load(getClass().getResource("JavaFXApp.fxml"));

        // primaryStage.setFullScreen(true);

        // set COMMANDS: TODO: reorder this, not to be here!
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            Controller.closeRequest();
        });

        // customTheme.setOnAction(new EventHandler<ActionEvent>() {
        //                             public void handle(ActionEvent event) {
        //                                 Parent root;
        //                                 // root = FXMLLoader.load(getClass().getClassLoader().getResource("path/to/other/view.fxml"), resources);
        //                                 HBox hBox = new HBox();
        //                                 Stage stage = new Stage();
        //                                 stage.setTitle("My New Stage Title");
        //                                 stage.setScene(new Scene(hBox, 450, 450));
        //                                 stage.show();
        //                                 // Hide this current window (if this is what you want)
        //                                 // ((Node)(event.getSource())).getScene().getWindow().hide();
        //                             }
        //                         });

        Scene scene = new Scene(root);

        // set CSS:
        scene.getStylesheets()
                .add(getClass().getResource("JavaFXApp.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
