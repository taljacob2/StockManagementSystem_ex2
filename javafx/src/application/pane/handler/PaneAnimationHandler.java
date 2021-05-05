package application.pane.handler;

import application.JavaFXAppController;
import application.pane.PaneReplacer;
import javafx.animation.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/* XXX
    GUIDE: check Animation here:
    https://www.genuinecoder.com/javafx-animation-tutorial/
 */

/**
 * Use this class as a {@link EventHandler} for handling <b>slide</b>
 * <tt>JavaFX</tt> {@code Animation}s.
 */
public class PaneAnimationHandler implements EventHandler, PaneReplacer {

    /**
     * Stores here the {@link Animation} <i>type</i> the user wishes to invoke.
     */
    private AnimationType animationType;

    /**
     * Stores here the {@link BorderPane} to show the new scene on its {@link
     * BorderPane#getCenter()}.
     */
    private BorderPane borderPaneToShowOnItsCenter;

    /**
     * Contains the {@link Pane} that is being <i>replaced</i>.
     */
    @FXML private Pane parentContainer;

    /**
     * The <i>path</i> to the attached <tt>.fxml</tt> file.
     */
    private String pathToFXML;

    /**
     * @param borderPaneToShowOnItsCenter the {@link BorderPane} to show on its
     *                                    center the new scene.
     * @param parentContainer             the {@code Parent} {@code Pane} {@code
     *                                    Container} of the new scene to be
     *                                    shown. Note: this must be a {@code
     *                                    Container} that is able to use the
     *                                    {@link Pane#getChildren()} in order to
     *                                    {@code add} children to it.
     * @param pathToFXML                  the <i>path</i> to the <tt>.fxml</tt>
     *                                    file for the new scene to be shown.
     * @param animationType               the <i>type</i> of {@link Animation}
     *                                    to be shown while transitioning
     *                                    between {@code JavaFXAppController.getReplaceAblePane()}
     *                                    and the
     *                                    <i>new</i> {@link Pane}.
     */
    public PaneAnimationHandler(BorderPane borderPaneToShowOnItsCenter,
                                Pane parentContainer, String pathToFXML,
                                AnimationType animationType) {
        this.borderPaneToShowOnItsCenter = borderPaneToShowOnItsCenter;
        this.parentContainer = parentContainer;
        this.pathToFXML = pathToFXML;
        this.animationType = animationType;
    }

    /**
     * Switch between the {@link Pane}s. {@code handle} it with an {@code
     * Animation} in between, if the {@code Animation} is enabled in {@link
     * application.pane.animation.Animation#getAnimation()}.
     *
     * @param event the encountered {@link Event}.
     * @see application.pane.animation.Animation
     */
    @Override public void handle(Event event) {

        // get 'newPane' to show:
        Pane newPane = getPane(pathToFXML);

        // if the Animation state is enabled, preset an Animation: // TODO need to implement 'if' statement
        if (application.pane.animation.Animation.getAnimation()) {
            presentAnimation(event, newPane);
        }

        // update the 'replaceAblePane':
        JavaFXAppController.setReplaceAblePane(newPane);

        // present the extracted Pane:
        borderPaneToShowOnItsCenter.setCenter(parentContainer);
    }

    private Animation presentAnimation(Event event, Pane newPane) {
        if (animationType == AnimationType.TIMELINE) {

            // Timeline - Animation:
            return createTimeLineAnimationAndPlay(event, newPane);
        } else if (animationType == AnimationType.FADE_OUT_IN) {

            // Fade Out In - Animation:
            return createFadeOutInTransitionAnimationAndPlay(newPane);
        } else if (animationType == AnimationType.FADE_IN_OUT) {

            // Fade In Out - Animation:
            return createFadeInOutTransitionAnimationAndPlay(newPane);
        }

        // in case of an error. shouldn't be happening:
        return null;
    }

    private Timeline createTimeLineAnimation(Event event, Pane pane) {
        Timeline timeline = new Timeline();
        Node triggeringNode = (Node) (event.getSource());
        Scene scene = triggeringNode.getScene();
        pane.translateYProperty().set(scene.getHeight());

        KeyValue keyValue = new KeyValue(pane.translateYProperty(), 0,
                Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        return timeline;
    }

    private Animation createTimeLineAnimationAndPlay(Event event,
                                                     Pane newPane) {

        Timeline timeline = createTimeLineAnimation(event, newPane);
        timeline.play();

        return timeline;
    }

    private FadeTransition createFadeInTransitionAnimation(Pane pane,
                                                           Duration duration) {
        FadeTransition fadeTransition = new FadeTransition(duration, pane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        return fadeTransition;
    }

    private FadeTransition createFadeOutTransitionAnimation(Pane pane,
                                                            Duration duration) {
        FadeTransition fadeTransition = new FadeTransition(duration, pane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        return fadeTransition;
    }

    private Animation createFadeInOutTransitionAnimationAndPlay(Pane newPane) {
        FadeTransition fadeOutTransition = createFadeOutTransitionAnimation(
                JavaFXAppController.getReplaceAblePane(),
                Duration.seconds(0.5));

        FadeTransition fadeInTransition =
                createFadeInTransitionAnimation(newPane, Duration.seconds(0.5));

        fadeInTransition.setOnFinished(event -> {

            // play fade out of 'replaceAblePane':
            fadeOutTransition.play(); // TODO: check
        });

        /*
         * Start the 'newPane' in '0' Opacity.
         * The 'FadeTransition' would return it to '1'.
         */
        newPane.setOpacity(0);
        parentContainer.getChildren().add(newPane);

        // play fade in of 'newPane':
        fadeInTransition.play();

        return fadeOutTransition;
    }

    private Animation createFadeOutInTransitionAnimationAndPlay(Pane newPane) {
        FadeTransition fadeOutTransition = createFadeOutTransitionAnimation(
                JavaFXAppController.getReplaceAblePane(),
                Duration.seconds(0.5));

        FadeTransition fadeInTransition =
                createFadeInTransitionAnimation(newPane, Duration.seconds(0.5));

        fadeOutTransition.setOnFinished(event -> {
            parentContainer.getChildren()
                    .remove(JavaFXAppController.getReplaceAblePane());

            /*
             * Start the 'newPane' in '0' Opacity.
             * The 'FadeTransition' would return it to '1'.
             */
            newPane.setOpacity(0);
            parentContainer.getChildren().add(newPane);

            // play fade in of 'newPane':
            fadeInTransition.play();

        });

        // play fade out of 'replaceAblePane':
        fadeOutTransition.play();

        return fadeInTransition;
    }

    /**
     * The <i>type</i> of {@link Animation} the user wishes to invoke.
     */
    public enum AnimationType {
        TIMELINE, FADE_OUT_IN, FADE_IN_OUT
    }

}