package application.pane.resources.viewlog;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import message.print.Log;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} of showing all the
 * <i>Messages</i> that were printed.</p>
 */
public class ViewLog implements Initializable {

    @FXML private Text log;

    public ViewLog() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        log.textProperty().setValue(Log.getMessageLog().toString());
    }
}

