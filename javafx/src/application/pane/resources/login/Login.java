package application.pane.resources.login;

import application.JavaFXAppController;
import application.pane.PaneAnimator;
import application.pane.resources.login.selecteduser.SelectedUser;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import message.print.MessagePrint;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} of the <i>Login</i>
 * page.</p>
 */
public class Login implements Initializable {

    @FXML private ComboBox<User> profileComboBox;

    @FXML private Button orderButton;

    public Login() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        try {
            profileComboBox.getItems()
                    .addAll(Engine.getUsers().getCollection());
        } catch (IOException e) {

            // Note: this exception should not happen thanks to the initial check of users.
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }

        initOrderButton(JavaFXAppController.getStaticBorderPane(),
                JavaFXAppController.getParentContainer(),
                JavaFXAppController.getAnimationType());

        SelectedUser.selectedUserProperty()
                .bind(profileComboBox.valueProperty());

        orderButton.disableProperty()
                .bind(profileComboBox.valueProperty().isNull());
    }

    private void initOrderButton(BorderPane borderPane, Pane parentContainer,
                                 PaneAnimator.AnimationType animationType) {

        // define 'orderExecutionButton':
        orderButton.setOnAction(
                new PaneAnimator.Handler(borderPane, parentContainer,
                        "/application/pane/resources/orderexecution/OrderExecution.fxml",
                        animationType));
    }
}
