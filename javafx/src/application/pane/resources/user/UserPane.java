package application.pane.resources.user;

import application.pane.ContainsAnotherPane;
import application.pane.resources.login.selecteduser.SelectedUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} after logging in to a
 * <i>User</i>, showing all commands available for the
 * {@link SelectedUser#getSelectedUser()} to pick.</p>
 */
public class UserPane extends ContainsAnotherPane implements Initializable {

    @FXML private Label userNameLabel;

    public UserPane() {

        // Show 'StockTablePane' on the inner BorderPane's CENTER:
        super("/application/pane/resources/stocktablepane/StockTablePane.fxml");
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        userNameLabel.setText(
                "Hello, " + SelectedUser.getSelectedUser().getName() + ". ");
    }
}
