package application.pane;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import message.print.MessagePrint;

import java.io.FileNotFoundException;
import java.net.URL;

public class PaneLoader {

    private static Pane pane;

    public static Pane getPane(String pathToFXML) {
        try {
            URL fxmlURL = PaneLoader.class.getResource(pathToFXML);
            // TODO: check
            //InputStream inputStream = ClassLoader.class.getResourceAsStream(pathToFXML);
            if (fxmlURL == null) {
                throw new FileNotFoundException("FXML file can't be found.");
            }

            pane = FXMLLoader.load(fxmlURL);
        } catch (Exception e) {
            MessagePrint.println(MessagePrint.Stream.ERR,
                    "No page " + pathToFXML +
                            " please check 'PaneLoader' or 'FXMLLoader'" +
                            " and make sure that the .fxml file is a 'Pane' Component!.");
            e.printStackTrace(); // TODO: check
        }
        return pane;
    }
}
