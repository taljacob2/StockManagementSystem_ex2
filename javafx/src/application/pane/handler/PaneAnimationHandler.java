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
     * Animation} in between, according to the {@link #animationType} provided.
     *
     * @param event the encountered {@link Event}.
     * @see AnimationType
     */
    @Override public void handle(Event event) {

        // Get 'newPane' to show:
        Pane newPane = getPane(pathToFXML);

        // If the Animation state is enabled, preset an Animation:
        presentAnimation(event, newPane);

        // Present the extracted Pane:
        borderPaneToShowOnItsCenter.setCenter(parentContainer);
    }

    private Animation presentAnimation(Event event, Pane newPane) {
        if (animationType == AnimationType.TIMELINE_BOTTOM_TO_TOP) {

            // Timeline Bottom To Top - Animation:
            return createTimeLineBottomToTopAnimationAndPlay(event, newPane);
        } else if (animationType == AnimationType.TIMELINE_RIGHT_TO_LEFT) {

            // Timeline Right To Left - Animation:
            return createTimeLineRightToLeftAnimationAndPlay(event, newPane);
        } else if (animationType == AnimationType.FADE_OUT_IN) {

            // Fade Out In - Animation:
            return createFadeOutInTransitionAnimationAndPlay(newPane);
        } else if (animationType == AnimationType.FADE_IN_OUT) {

            // Fade In Out - Animation:
            return createFadeInOutTransitionAnimationAndPlay(newPane);
        } else if (animationType == AnimationType.NONE) {
            setPaneWithNoneAnimation(newPane);
        }

        // in case of an error. shouldn't be happening:
        return null;
    }

    private void setPaneWithNoneAnimation(Pane newPane) {
        parentContainer.getChildren().add(newPane);
        parentContainer.getChildren()
                .remove(JavaFXAppController.getReplaceAblePane());
        JavaFXAppController.setReplaceAblePane(newPane);
    }

    private Timeline createTimeLineBottomToCenterAnimation(Event event,
                                                           Pane newPane) {
        Timeline timeline = new Timeline();
        Node triggeringNode = (Node) (event.getSource());
        Scene scene = triggeringNode.getScene();
        newPane.translateYProperty().set(scene.getHeight());

        KeyValue keyValue = new KeyValue(newPane.translateYProperty(), 0,
                Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        return timeline;
    }

    private Timeline createTimeLineRightToCenterAnimation(Event event,
                                                          Pane newPane) {
        Timeline timeline = new Timeline();
        Node triggeringNode = (Node) (event.getSource());
        Scene scene = triggeringNode.getScene();
        newPane.translateXProperty().set(scene.getWidth());

        KeyValue keyValue = new KeyValue(newPane.translateXProperty(), 0,
                Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        return timeline;
    }

    private Timeline createTimeLineCenterToLeftAnimation(Event event) {
        Timeline timeline = new Timeline();
        Node triggeringNode = (Node) (event.getSource());
        Scene scene = triggeringNode.getScene();
        JavaFXAppController.getReplaceAblePane().translateXProperty().set(0);

        KeyValue keyValue = new KeyValue(
                JavaFXAppController.getReplaceAblePane().translateXProperty(),
                -scene.getWidth(), Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        return timeline;
    }

    private Timeline createTimeLineCenterToTopAnimation(Event event) {
        Timeline timeline = new Timeline();
        Node triggeringNode = (Node) (event.getSource());
        Scene scene = triggeringNode.getScene();
        JavaFXAppController.getReplaceAblePane().translateYProperty().set(0);

        KeyValue keyValue = new KeyValue(
                JavaFXAppController.getReplaceAblePane().translateYProperty(),
                -scene.getHeight(), Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        return timeline;
    }

    private Animation createTimeLineBottomToTopAnimationAndPlay(Event event,
                                                                Pane newPane) {
        Timeline timeline =
                createTimeLineBottomToCenterAnimation(event, newPane);
        parentContainer.getChildren().add(newPane);
        timeline.play();

        Timeline replaceAbleTimeline =
                createTimeLineCenterToTopAnimation(event);

        /*
         * Animate the 'replaceAblePane' before removing it,
         * even if the 'replaceAblePane' wasn't defined as animated by the
         * JavaFXController in the first place.
         * We are doing so by removing it as a child of the parentContainer, and
         * right after that adding it as a child of the parentContainer.
         */
        parentContainer.getChildren()
                .remove(JavaFXAppController.getReplaceAblePane());
        parentContainer.getChildren()
                .add(JavaFXAppController.getReplaceAblePane());
        replaceAbleTimeline.play();
        replaceAbleTimeline.setOnFinished(event1 -> {
            parentContainer.getChildren()
                    .remove(JavaFXAppController.getReplaceAblePane());

            // Update the 'replaceAblePane':
            JavaFXAppController.setReplaceAblePane(newPane);
        });

        return timeline;
    }

    private Animation createTimeLineRightToLeftAnimationAndPlay(Event event,
                                                                Pane newPane) {
        Timeline timeline =
                createTimeLineRightToCenterAnimation(event, newPane);
        parentContainer.getChildren().add(newPane);
        timeline.play();

        Timeline replaceAbleTimeline =
                createTimeLineCenterToLeftAnimation(event);

        /*
         * Animate the 'replaceAblePane' before removing it,
         * even if the 'replaceAblePane' wasn't defined as animated by the
         * JavaFXController in the first place.
         * We are doing so by removing it as a child of the parentContainer, and
         * right after that adding it as a child of the parentContainer.
         */
        parentContainer.getChildren()
                .remove(JavaFXAppController.getReplaceAblePane());
        parentContainer.getChildren()
                .add(JavaFXAppController.getReplaceAblePane());
        replaceAbleTimeline.play();
        replaceAbleTimeline.setOnFinished(event1 -> {
            parentContainer.getChildren()
                    .remove(JavaFXAppController.getReplaceAblePane());

            // Update the 'replaceAblePane':
            JavaFXAppController.setReplaceAblePane(newPane);
        });

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

            /*
             * Animate the 'replaceAblePane' before removing it,
             * even if the 'replaceAblePane' wasn't defined as animated by the
             * JavaFXController in the first place.
             * We are doing so by removing it as a child of the parentContainer, and
             * right after that adding it as a child of the parentContainer.
             */
            parentContainer.getChildren()
                    .remove(JavaFXAppController.getReplaceAblePane());

            // play fade out of 'replaceAblePane':
            fadeOutTransition.play();

            // Update the 'replaceAblePane':
            JavaFXAppController.setReplaceAblePane(newPane);
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

            // Update the 'replaceAblePane':
            JavaFXAppController.setReplaceAblePane(newPane);
        });

        /*
         * Animate the 'replaceAblePane' before removing it,
         * even if the 'replaceAblePane' wasn't defined as animated by the
         * JavaFXController in the first place.
         * We are doing so by removing it as a child of the parentContainer, and
         * right after that adding it as a child of the parentContainer.
         */
        parentContainer.getChildren()
                .remove(JavaFXAppController.getReplaceAblePane());
        parentContainer.getChildren()
                .add(JavaFXAppController.getReplaceAblePane());

        // play fade out of 'replaceAblePane':
        fadeOutTransition.play();

        return fadeInTransition;
    }

    /**
     * The <i>type</i> of {@link Animation} the user wishes to invoke.
     */
    public enum AnimationType {
        TIMELINE_RIGHT_TO_LEFT, TIMELINE_BOTTOM_TO_TOP, FADE_OUT_IN,
        FADE_IN_OUT, NONE
    }

}