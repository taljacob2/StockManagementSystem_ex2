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

    // /**
    //  * This method <b>replaces</b> an {@link AnchorPane} with an another {@link
    //  * AnchorPane} that is the <i>root</i> of a given <tt>.fxml</tt> file.
    //  * <p> Note: this is implemented with {@link Timeline} {@code Animation}
    //  * for graphical purposes only, but could also be implemented without
    //  * it.</p>
    //  *
    //  * @param event           the encountered {@link ActionEvent}.
    //  * @param parentContainer the <i>parent</i> {@code Container} of the {@link
    //  *                        AnchorPane} to be replaced. <p>Important: Must be
    //  *                        a {@link StackPane}.</p>
    //  * @param anchorRoot      the new {@link AnchorPane} to be replaced with.
    //  *                        <p> Note: this {@link AnchorPane} should be the
    //  *                        <i>root</i> of the <tt>.fxml</tt> file.</p>
    //  * @param pathToFXML      the <i>path</i> to the <tt>.fxml</tt> file of the
    //  *                        {@link AnchorPane}.
    //  * @throws IOException in case of an Error.
    //  */
    // @FXML default void replaceWith(ActionEvent event, StackPane parentContainer,
    //                                AnchorPane anchorRoot,
    //                                String pathToFXML) // TODO: maybe kill this method/class
    //         throws IOException {
    //     Parent root = FXMLLoader.load(getClass().getResource(pathToFXML));
    //     Scene scene = anchorRoot.getScene();
    //     root.translateYProperty().set(scene.getHeight());
    //
    //     parentContainer.getChildren().add(root);
    //
    //     Timeline timeline = new Timeline();
    //     KeyValue keyValue = new KeyValue(root.translateYProperty(), 0,
    //             Interpolator.EASE_IN);
    //     KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), keyValue);
    //     timeline.getKeyFrames().add(keyFrame);
    //     timeline.setOnFinished(t -> {
    //         parentContainer.getChildren().remove(anchorRoot);
    //     });
    //     timeline.play();
    // }

    /**
     * This method sets the new Pane to be shown on a <i>center</i> of the
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

        // show the pane in the center of the 'borderPane':
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

        // show the pane in the center of the borderPane:
        borderPaneToShowOnItsCenter.setCenter(newPane);
    }
}
