package application.pane.resources.ownprofile;

import application.pane.PaneShower;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} of showing the
 * <i>Status</i> of a <i>Profile</i> in the system.</p>
 */
public class OwnProfile extends PaneShower {

    public OwnProfile() {

        // Show 'StockTablePane' on the BorderPane's CENTER:
        super("/application/pane/resources/stocktablepane/StockTablePane.fxml");
    }

}
