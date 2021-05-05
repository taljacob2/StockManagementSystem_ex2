package application.pane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class represents a <tt>JavaFX</tt> {@code Controller} that is able to
 * show a {@link javafx.scene.layout.Pane} on the <i>Center</i> of its {@link
 * BorderPane}.
 */
public class PaneShower implements Initializable, PaneReplacer {

    @FXML private BorderPane borderPane;
    private String pathToFXML;

    public PaneShower(String pathToFXML) {
        this.pathToFXML = pathToFXML;
    }

    /**
     * This method sets the new Pane to be shown on the <i>center</i> of the
     * {@link #borderPane}.
     *
     * @param pathToFXML path to the <tt>.fxml</tt> of the pane the user wishes
     *                   to show.
     */
    private void setPane(String pathToFXML) {
        setPane(borderPane, pathToFXML);
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        setPane(pathToFXML);
    }
}
