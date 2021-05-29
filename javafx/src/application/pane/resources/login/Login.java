package application.pane.resources.login;

import engine.Engine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
 * <p>Represents the {@link javafx.scene.layout.Pane} of the <i>Login</i>
 * page.</p>
 */
public class Login implements Initializable {

    @FXML private ComboBox<User> comboBox;

    @Override public void initialize(URL location, ResourceBundle resources) {
        try {
            comboBox.getItems().addAll(Engine.getUsers().getCollection());
        } catch (IOException e) {

            // Note: this exception should not happen thanks to the initial check of users.
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }
    }
}
