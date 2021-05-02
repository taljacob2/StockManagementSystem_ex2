package application.pane;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import message.print.MessagePrint;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * This class manages the <i>load</i>s of independent <i>pane</i>s, by loading
 * their
 * <tt>.fxml</tt> files and their according {@code Controller}s.
 */
public class PaneLoader {

    private static final String PATH_TO_PANE_RESOURCES =
            "/application/pane/resources";

    private static Pane pane;

    /**
     * This is the <b>core</b> method of this class. The method gets a <i>path
     * to <tt>.fxml</tt> file</i> and returns the according {@link Pane} to it.
     *
     * @param pathToFXML the path to the <tt>.fxml</tt> file the user wishes to
     *                   load.
     * @return the according {@link Pane} the user wishes to show to the screen.
     */
    public static Pane getPane(String pathToFXML) {
        try {
            URL fxmlURL = PaneLoader.class
                    .getResource(PATH_TO_PANE_RESOURCES + pathToFXML);

            // TODO: kill this:
            System.out.println(fxmlURL != null ? fxmlURL.toString() : "null");

            if (fxmlURL == null) {
                throw new FileNotFoundException("FXML file can't be found.");
            }

            pane = FXMLLoader.load(fxmlURL);
        } catch (Exception e) {
            MessagePrint.println(MessagePrint.Stream.ERR,
                    "No page " + pathToFXML +
                            " please check 'PaneLoader' or 'FXMLLoader'" +
                            " and make sure that the .fxml file is a 'Pane' Component!.");
            // e.printStackTrace(); // TODO: check
        }
        return pane;
    }
}
