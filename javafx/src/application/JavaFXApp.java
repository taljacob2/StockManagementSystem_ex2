package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private static final String pathToCSS =
            JavaFXApp.class.getResource("JavaFXApp.css").toExternalForm();
    public static FileChooser fileChooser;
    private static Parent root;
    private static Stage stage;
    private static Scene scene;

    static {

        // create the fileChooser:
        fileChooser = new FileChooser();

        // set fileChooser to start on current path:
        fileChooser.setInitialDirectory(new File(currentPath));

        // define extensionFilters:
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));

    }

    public static Parent getRoot() {
        return root;
    }

    public static Stage getStage() {
        return stage;
    }

    @Override public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Tal Yacob RSE (V2.0)");

        // set root from an FXML file:
        root = FXMLLoader.load(getClass().getResource("JavaFXApp.fxml"));

        // primaryStage.setFullScreen(true);

        // set COMMANDS: TODO: reorder this, not to be here!
        stage.setOnCloseRequest(event -> {
            event.consume();
            Controller.closeRequest();
        });

        // customTheme.setOnAction(event -> { System.out.println("hey");});

        scene = new Scene(root);

        // set CSS:
        scene.getStylesheets().add(pathToCSS);

        stage.setScene(scene);
        stage.show();

    }

}
