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

    /**
     * Stores here the <i>Absolute Path</i> of the program.
     * <p>Mainly used for setting the {@link FileChooser#setInitialDirectory(File)}
     * to the current path of the program.</p>
     */
    public static final String currentPath =
            Paths.get(".").toAbsolutePath().normalize().toString();

    /**
     * Stores here the <i>Absolute Path</i> to the main <tt>.css</tt> file used
     * of the program.
     */
    private static final String pathToCSS =
            JavaFXApp.class.getResource("JavaFXApp.css").toExternalForm();

    /**
     * Stores here the {@code FileChooser} of the program.
     */
    public static FileChooser fileChooser;

    /**
     * Stores here the {@code Parent} of the <tt>JavaFX</tt> program. Imported
     * by a <tt>.fxml</tt> file.
     */
    private static Parent root;

    /**
     * The main {@code Stage} of the <tt>JavaFX</tt> program.
     */
    private static Stage stage;

    /**
     * The main {@code Scene} of the <tt>JavaFX</tt> program.
     */
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

    public static Scene getScene() {
        return scene;
    }

    @Override public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        // set title:
        stage.setTitle("Tal Yacob RSE - Rolling Exercise 2.0");

        // set root from a .FXML file:
        root = FXMLLoader
                .load(getClass().getResource("/application/JavaFXApp.fxml"));
        // root = FXMLLoader.load(getClass().getResource("/application/scene/SceneOne.fxml"));// TODO: check

        // set COMMANDS: TODO: reorder this, not to be here!
        stage.setOnCloseRequest(event -> {
            event.consume();
            JavaFXAppController.closeRequest();
        });

        scene = new Scene(root);

        // set .CSS file:
        scene.getStylesheets().add(pathToCSS);

        stage.setScene(scene); // TODO check

        stage.show();
    }

}
