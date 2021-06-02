package application.pane.resources.ownprofile;

import application.pane.ContainsAnotherPane;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} of showing the
 * <i>Status</i> of a <i>User</i> in the system.</p>
 */
public class OwnProfile extends ContainsAnotherPane {

    public OwnProfile() {

        // Show 'StockTablePane' on the inner BorderPane's CENTER:
        super("/application/pane/resources/stocktablepane/StockTablePane.fxml");
    }

}
