package application.scene;

import application.pane.PaneLoader;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import message.print.MessagePrint;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

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
            URL fxmlURL = PaneLoader.class.getResource(pathToFXML);

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

    /**
     * This method <b>replaces</b> an {@link AnchorPane} with an another {@link
     * AnchorPane} that is the <i>root</i> of a given <tt>.fxml</tt> file.
     * <p> Note: this is implemented with {@link Timeline} {@code Animation}
     * for graphical purposes only, but could also be implemented without
     * it.</p>
     *
     * @param event           the encountered {@link ActionEvent}.
     * @param parentContainer the <i>parent</i> {@code Container} of the {@link
     *                        AnchorPane} to be replaced. <p>Important: Must be
     *                        a {@link StackPane}.</p>
     * @param anchorRoot      the new {@link AnchorPane} to be replaced with.
     *                        <p> Note: this {@link AnchorPane} should be the
     *                        <i>root</i> of the <tt>.fxml</tt> file.</p>
     * @param pathToFXML      the <i>path</i> to the <tt>.fxml</tt> file of the
     *                        {@link AnchorPane}.
     * @throws IOException in case of an Error.
     */
    @FXML default void replaceWith(ActionEvent event, StackPane parentContainer,
                                   AnchorPane anchorRoot, String pathToFXML)
            throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(pathToFXML));
        Scene scene = anchorRoot.getScene();
        root.translateYProperty().set(scene.getHeight());

        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateYProperty(), 0,
                Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(anchorRoot);
        });
        timeline.play();
    }

    // @FXML default void replaceWith(AnchorPane anchorRoot, String pathToFXML)
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


}
