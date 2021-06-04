package application.pane.resources.printall;

import application.javafxapp.JavaFXAppHandler;
import application.pane.resources.login.selecteduser.SelectedUser;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import message.print.MessagePrint;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} of showing all the
 * {@link order.Order}s and {@link transaction.Transaction}s in the system, for
 * each {@link stock.Stock}.
 * </p>
 */
public class PrintAll implements Initializable {

    @FXML private ComboBox<User> userComboBox;

    @FXML private Button loginButton;

    public PrintAll() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        try {
            userComboBox.getItems().addAll(Engine.getUsers().getCollection());
        } catch (IOException e) {

            // Note: this exception should not happen thanks to the initial check of users.
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }

        new JavaFXAppHandler(
                "/application/pane/resources/login/selecteduser/pane/UserPane.fxml")
                .handleOnce(loginButton);

        SelectedUser.selectedUserProperty().bind(userComboBox.valueProperty());

        loginButton.disableProperty()
                .bind(userComboBox.valueProperty().isNull());
    }

}
