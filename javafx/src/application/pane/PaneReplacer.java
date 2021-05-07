package application.pane;

import application.JavaFXAppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import message.print.MessagePrint;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * This <i>interface</i> manages the <i>load</i>s of independent <i>pane</i>s,
 * by loading their
 * <tt>.fxml</tt> files and their according {@code Controller}s.
 */
public interface PaneReplacer {

    /**
     * The method gets a <i>path to <tt>.fxml</tt> file</i> and returns the
     * according {@link Pane} to it.
     *
     * @param pathToFXML the path to the <tt>.fxml</tt> file the user wishes to
     *                   load.
     * @return the according {@link Pane} the user wishes to show to the screen.
     */
    default Pane getPane(String pathToFXML) {
        Pane pane = null;
        try {
            URL fxmlURL = PaneReplacer.class.getResource(pathToFXML);
            if (fxmlURL == null) {
                throw new FileNotFoundException("FXML file can't be found.");
            }

            pane = FXMLLoader.load(fxmlURL);
        } catch (Exception e) {
            MessagePrint.println(MessagePrint.Stream.ERR,
                    "No page " + pathToFXML +
                            " please check 'PaneLoader' or 'FXMLLoader'." +
                            " ALSO: make sure that the '.fxml' file root " +
                            "Component is a 'Pane' Component!");
        }
        return pane;
    }

    /**
     * This method sets the new Pane to be shown on a <i>CENTER</i> of the
     * {@link javafx.scene.layout.BorderPane} and <i>updates</i> the {@link
     * Pane} accordingly.
     *
     * @param borderPaneToShowOnItsCenter the {@link BorderPane} to show the
     *                                    {@link Pane} provided in the
     *                                    <tt>.fxml</tt> file.
     * @param parentContainer             is a <i>child</i> of the provided
     *                                    {@link BorderPane}, and the {@link
     *                                    Parent} of the {@link Pane} that is
     *                                    being provided by the <tt>.fxml</tt>
     *                                    file.
     * @param pathToFXML                  path to the <tt>.fxml</tt> of the pane
     *                                    the user wishes to show.
     */
    default void setPane(BorderPane borderPaneToShowOnItsCenter,
                         Pane parentContainer, String pathToFXML) {

        // get the 'newPane':
        Pane newPane = getPane(pathToFXML);

        // add the pane as a child of the 'parentContainer':
        parentContainer.getChildren().add(newPane);

        // remove the 'replaceAblePane' as a child of the 'parentContainer':
        parentContainer.getChildren()
                .remove(JavaFXAppController.getReplaceAblePane());

        // update the 'replaceAblePane':
        JavaFXAppController.setReplaceAblePane(newPane);

        // show the pane in the CENTER of the 'borderPane':
        borderPaneToShowOnItsCenter.setCenter(newPane);
    }


    /**
     * This method is similar to {@link #setPane(BorderPane, Pane, String)} but
     * without updating the {@code replaceAblePane} and {@code
     * parentContainer}'s <i>children</i>.
     *
     * @param borderPaneToShowOnItsCenter the {@link BorderPane} to show the
     *                                    {@link Pane} provided in the
     *                                    <tt>.fxml</tt> file.
     * @param pathToFXML                  path to the <tt>.fxml</tt> of the pane
     *                                    the user wishes to show.
     * @see #setPane(BorderPane, Pane, String)
     */
    default void setPane(BorderPane borderPaneToShowOnItsCenter,
                         String pathToFXML) {

        // get the newPane:
        Pane newPane = getPane(pathToFXML);

        // show the pane in the CENTER of the borderPane:
        borderPaneToShowOnItsCenter.setCenter(newPane);
    }
}
