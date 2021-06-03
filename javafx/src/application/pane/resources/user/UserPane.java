package application.pane.resources.user;

import application.javafxapp.JavaFXAppController;
import application.pane.ContainsAnotherPane;
import application.pane.resources.login.selecteduser.SelectedUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
    @FXML private Button viewOwnsButton;
    @FXML private Button makeAnOrderButton;
    @FXML private VBox userVBox;

    public UserPane() {

        // Show 'StockTablePane' on the inner BorderPane's CENTER:
        super("/application/pane/resources/stocktablepane/StockTablePane.fxml");
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        userNameLabel.setText(
                "Hello, " + SelectedUser.getSelectedUser().getName() + ".");


        viewOwnsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                setPane(getBorderPaneToShowTheAnotherInnerPane(),
                        "/application/pane/resources/ownuser/OwnUser.fxml");
            }
        });

        makeAnOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                setPane(getBorderPaneToShowTheAnotherInnerPane(),
                        "/application/pane/resources/orderexecution/OrderExecution.fxml");
            }
        });

        userVBox.setStyle("-fx-background-color: " +
                JavaFXAppController.rgbaStringProperty().get());

        // Set userVBox the updated style:
        JavaFXAppController.rgbaStringProperty()
                .addListener((observable, oldValue, newValue) -> {
                    userVBox.setStyle("-fx-background-color: " +
                            JavaFXAppController.rgbaStringProperty()
                                    .get());
                });

        userVBox.setMinHeight(290);
        userVBox.setMinWidth(187);
    }
}