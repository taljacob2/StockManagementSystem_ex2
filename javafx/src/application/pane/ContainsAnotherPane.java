package application.pane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} represents a <tt>JavaFX</tt> {@code Controller} that is
 * able to
 * <i>show</i> <b>another</b> {@link javafx.scene.layout.Pane} inside of it,
 * and does so on the <i>CENTER</i> of its {@link BorderPane}.
 * <p>
 * Note: The {@code Class}es that <b><i>extends</i></b> this {@code Class} are
 * the {@code Class}es that wish to <i>show</i> <b>another</b> {@link
 * javafx.scene.layout.Pane} on them.
 */
public class ContainsAnotherPane implements Initializable, PaneReplacer {

    @FXML private BorderPane borderPaneToShowTheAnotherInnerPane;
    private String pathToFXML;

    /**
     * Constructor.
     *
     * @param pathToFXML the <i>path</i> to the <tt>.fxml</tt> file that
     *                   represents the {@link javafx.scene.layout.Pane} to be
     *                   shown.
     */
    public ContainsAnotherPane(String pathToFXML) {
        this.pathToFXML = pathToFXML;
    }

    /**
     * This method sets the new {@link javafx.scene.layout.Pane} to be shown on
     * the <i>CENTER</i> of the {@link #borderPaneToShowTheAnotherInnerPane}.
     *
     * @param pathToFXML path to the <tt>.fxml</tt> of the {@link
     *                   javafx.scene.layout.Pane} the user wishes to show.
     */
    private void setPane(String pathToFXML) {
        setPane(borderPaneToShowTheAnotherInnerPane, pathToFXML);
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        setPane(pathToFXML);
    }

    public BorderPane getBorderPaneToShowTheAnotherInnerPane() {
        return borderPaneToShowTheAnotherInnerPane;
    }

}
