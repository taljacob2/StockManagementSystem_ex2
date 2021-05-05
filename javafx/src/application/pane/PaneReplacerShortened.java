package application.pane;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This <i>interface</i> serves a {@code Class} that has a {@link
 * javafx.scene.layout.BorderPane} as a field. <p>This <i>interface</i> is used
 * to simplify the call to the {@link #setPane(BorderPane, Pane, Pane, String)}
 * method, to make the calls to it shorter.</p>
 */
public interface PaneReplacerShortened extends PaneReplacer {

    /**
     * This method sets the new Pane to be shown on the <i>center</i> of the
     * {@link BorderPane} of this {@code Class}, and <i>updates</i> the {@code
     * replaceAblePane} of it accordingly.
     *
     * @param pathToFXML path to the <tt>.fxml</tt> of the pane the user wishes
     *                   to show.
     */
    void setPane(String pathToFXML);

}
