package application.pane.resources.controller;

import application.pane.PaneShower;

/**
 * This class represents the {@link javafx.scene.layout.Pane} of showing the
 * <i>Status</i> of a
 * <i>Profile</i> in the system.
 */
public class OwnProfile extends PaneShower {

    public OwnProfile() {

        // show 'StockTablePane' on the BorderPane's CENTER:
        super("/application/pane/resources/fxml/StockTablePane.fxml");
    }

}
