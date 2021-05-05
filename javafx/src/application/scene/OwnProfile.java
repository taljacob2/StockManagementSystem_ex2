package application.scene;

import application.pane.PaneShower;

/**
 * This class represents the {@link javafx.scene.layout.Pane} of showing the
 * <i>Status</i> of a
 * <i>Profile</i> in the system.
 */
public class OwnProfile extends PaneShower {

    public OwnProfile() {

        // show 'StockTablePane' on the BorderPane's Center:
        super("/application/scene/StockTablePane.fxml");
    }

}
