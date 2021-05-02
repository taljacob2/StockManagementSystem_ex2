package application.scene;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import message.print.MessagePrint;

import java.io.IOException;

public class SceneHandler implements EventHandler {

    /**
     * Contains the {@link javafx.scene.layout.AnchorPane} windows that are
     * being <i>replaced</i>.
     */
    @FXML private StackPane parentContainer;
    /**
     * The <i>path</i> to the attached <tt>.fxml</tt> file.
     */
    private String pathToFXML;

    /**
     * The <i>root</i> of the attached <tt>.fxml</tt> file.
     */
    @FXML private AnchorPane anchorRoot = new AnchorPane();

    public SceneHandler(StackPane parentContainer, String pathToFXML) {
        this.parentContainer = parentContainer;
        this.pathToFXML = pathToFXML;
    }

    @Override public void handle(
            Event event) { // FIXME: maybe can get the contents of `parentContainer` and `triggeringNode` through the `event` parameter
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(pathToFXML));
        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }
        Node triggeringNode = (Node) (event.getSource());
        Scene scene = triggeringNode.getScene();
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

}