package application.pane;

import application.javafxapp.JavaFXAppController;
import javafx.animation.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * This <i>interface</i> gets <i>two</i> {@link Pane}s, and creates a
 * <tt>JavaFX</tt> {@link Animation}s transition between them.
 * <p>Also:</p>
 * <ul>
 *      <li>Defines an {@link AnimationType}, to define the {@link Animation}
 *      <i>types</i> available to be used.
 *      </li>
 *
 *      <li>Defines a {@link Handler} to serve as an {@link Animation}
 *      {@link EventHandler}.
 *      </li>
 * </ul>
 */
public interface PaneAnimator extends PaneReplacer {

    static Animation presentAnimation(Event event, Pane parentContainer,
                                      Pane newPane,
                                      AnimationType animationType) {
        Animation animation =
                presentEventRequiredAnimation(event, parentContainer, newPane,
                        animationType);
        if (animation == null) {
            presentNoEventRequiredAnimation(parentContainer, newPane,
                    animationType);
        }

        // in case of an error. shouldn't be happening:
        return animation;
    }

    static Animation presentEventRequiredAnimation(Event event,
                                                   Pane parentContainer,
                                                   Pane newPane,
                                                   AnimationType animationType) {
        if (animationType == AnimationType.TIMELINE_BOTTOM_TO_TOP) {

            // Timeline Bottom To Top - Animation:
            return createTimeLineBottomToTopAnimationAndPlay(event,
                    parentContainer, newPane);
        } else if (animationType == AnimationType.TIMELINE_RIGHT_TO_LEFT) {

            // Timeline Right To Left - Animation:
            return createTimeLineRightToLeftAnimationAndPlay(event,
                    parentContainer, newPane);
        }

        // in case of an error. shouldn't be happening:
        return null;
    }

    static Animation presentNoEventRequiredAnimation(Pane parentContainer,
                                                     Pane newPane,
                                                     AnimationType animationType) {
        if (animationType == AnimationType.FADE_OUT_IN) {

            // Fade Out In - Animation:
            return createFadeOutInTransitionAnimationAndPlay(parentContainer,
                    newPane);
        } else if (animationType == AnimationType.FADE_IN_OUT) {

            // Fade In Out - Animation:
            return createFadeInOutTransitionAnimationAndPlay(parentContainer,
                    newPane);
        } else if (animationType == AnimationType.NONE) {
            setPaneWithNoneAnimation(parentContainer, newPane);
        }

        // in case of an error. shouldn't be happening:
        return null;
    }

    @Deprecated
    static void setPaneWithNoneAnimation(Pane parentContainer, Pane newPane) {
        // TODO: maybe can kill this
        parentContainer.getChildren().add(newPane);
        parentContainer.getChildren()
                .remove(JavaFXAppController.getReplaceAblePane());
        JavaFXAppController.setReplaceAblePane(newPane);
    }

    static Timeline createTimeLineBottomToCenterAnimation(Event event,
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

    static Timeline createTimeLineRightToCenterAnimation(Event event,
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

    static Timeline createTimeLineCenterToLeftAnimation(Event event) {
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

    static Timeline createTimeLineCenterToTopAnimation(Event event) {
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

    static Animation createTimeLineBottomToTopAnimationAndPlay(Event event,
                                                               Pane parentContainer,
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

    static Animation createTimeLineRightToLeftAnimationAndPlay(Event event,
                                                               Pane parentContainer,
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

    static FadeTransition createFadeInTransitionAnimation(Pane pane,
                                                          Duration duration) {
        FadeTransition fadeTransition = new FadeTransition(duration, pane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        return fadeTransition;
    }

    static FadeTransition createFadeOutTransitionAnimation(Pane pane,
                                                           Duration duration) {
        FadeTransition fadeTransition = new FadeTransition(duration, pane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        return fadeTransition;
    }

    static Animation createFadeInOutTransitionAnimationAndPlay(
            Pane parentContainer, Pane newPane) {
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
            //         FIXME not sure why this is kinda BUGGED
            // parentContainer.getChildren()
            //         .remove(JavaFXAppController.getReplaceAblePane()); //


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

    static Animation createFadeOutInTransitionAnimationAndPlay(
            Pane parentContainer, Pane newPane) {
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


    /**
     * Use this {@code Class} as a {@link EventHandler} for handling
     * <tt>JavaFX</tt> {@link Pane} <i>switches</i> and {@link Animation}s.
     */
    public class Handler implements EventHandler, PaneReplacer {

        /* XXX
         *  GUIDE: check Animation here:
         *  https://www.genuinecoder.com/javafx-animation-tutorial/
         */

        /**
         * This field determines whether to "arm" the {@link
         * javafx.scene.control.ButtonBase} or not.
         * <p>The {@code Button} should be armed only <i>once</i>.</p>
         */
        SimpleBooleanProperty executeOrderButtonWasNotPressedYet =
                new SimpleBooleanProperty(true);

        /**
         * Stores here the {@link BorderPane} to show the new scene on its
         * {@link BorderPane#getCenter()}.
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
         * Stores here the {@link Animation} <i>type</i> the user wishes to
         * invoke.
         */
        private AnimationType animationType;

        /**
         * Stores here a {@link Runnable} to invoke its {@link Runnable#run()}
         * method, right after invoking the {@link #handle(Event)}'s body.
         */
        private Runnable runnable = null;

        /**
         * Indicates whether to invoke the {@link #handle} method or not.
         */
        private boolean handle = true;

        /**
         * @param borderPaneToShowOnItsCenter the {@link BorderPane} to show on
         *                                    its CENTER the <i>newPane</i>.
         * @param parentContainer             the {@code Parent} {@code Pane}
         *                                    {@code Container} of the new scene
         *                                    to be shown. <p> Note: this must
         *                                    be a {@code Container} that is
         *                                    able to use the {@link Pane#getChildren()}
         *                                    in order to {@code add} children
         *                                    to it.</p>
         * @param pathToFXML                  the <i>path</i> to the <tt>.fxml</tt>
         *                                    file for the <i>newPane</i> to be
         *                                    shown.
         * @param animationType               the <i>type</i> of {@link
         *                                    Animation} to be shown while
         *                                    transitioning between the {@code
         *                                    replaceAblePane} and the
         *                                    <i>new</i> {@link Pane}.
         */
        public Handler(BorderPane borderPaneToShowOnItsCenter,
                       Pane parentContainer, String pathToFXML,
                       AnimationType animationType) {
            initFields(borderPaneToShowOnItsCenter, parentContainer, pathToFXML,
                    animationType);
        }

        public Handler(BorderPane borderPaneToShowOnItsCenter,
                       Pane parentContainer, String pathToFXML,
                       AnimationType animationType, boolean handle) {
            initFields(borderPaneToShowOnItsCenter, parentContainer, pathToFXML,
                    animationType);
            this.handle = handle;
        }

        public Handler(BorderPane borderPaneToShowOnItsCenter,
                       Pane parentContainer, String pathToFXML,
                       AnimationType animationType, Runnable runnable) {
            initFields(borderPaneToShowOnItsCenter, parentContainer, pathToFXML,
                    animationType);
            this.runnable = runnable;
        }

        public Handler(BorderPane borderPaneToShowOnItsCenter,
                       Pane parentContainer, String pathToFXML,
                       AnimationType animationType, boolean handle,
                       Runnable runnable) {
            initFields(borderPaneToShowOnItsCenter, parentContainer, pathToFXML,
                    animationType);
            this.handle = handle;
            this.runnable = runnable;
        }

        private void initFields(BorderPane borderPaneToShowOnItsCenter,
                                Pane parentContainer, String pathToFXML,
                                AnimationType animationType) {
            this.borderPaneToShowOnItsCenter = borderPaneToShowOnItsCenter;
            this.parentContainer = parentContainer;
            this.pathToFXML = pathToFXML;
            this.animationType = animationType;
        }

        /**
         * Switch between the {@link Pane}s. {@code handle} it with an {@code
         * Animation} in between, according to the {@link #animationType}
         * provided.
         *
         * @param event the encountered {@link Event}.
         * @see AnimationType
         */
        @Override public void handle(Event event) {
            if (handle) {
                setPane(event, borderPaneToShowOnItsCenter, parentContainer,
                        pathToFXML, animationType);
                if (runnable != null) { runnable.run(); }
            }
        }

        public void handle(ButtonBase buttonBase) {
            if (handle) {

                // define 'buttonBase':
                buttonBase.setOnAction(
                        new PaneAnimator.Handler(borderPaneToShowOnItsCenter,
                                parentContainer, pathToFXML, animationType));
                if (runnable != null) { runnable.run(); }
            }
        }

        /**
         * This method sets the new Pane to be shown on a <i>CENTER</i> of the
         * {@link javafx.scene.layout.BorderPane} and <i>updates</i> the {@link
         * Pane} accordingly.
         *
         * <p>Note: this implementation is using an
         * {@link javafx.animation.Animation} to switch between the {@link Pane}
         * s, according to the user's decision.
         * </p>
         *
         * @param event                       the {@link Event} that <i>triggers
         *                                    </i> this method invocation.
         * @param borderPaneToShowOnItsCenter the {@link BorderPane} to show the
         *                                    {@link Pane} provided in the
         *                                    <tt>.fxml</tt> file.
         * @param parentContainer             is a <i>child</i> of the provided
         *                                    {@link BorderPane}, and the {@link
         *                                    javafx.scene.Parent} of the {@link
         *                                    Pane} that is being provided by
         *                                    the
         *                                    <tt>.fxml</tt> file.
         * @param pathToFXML                  path to the <tt>.fxml</tt> of the
         *                                    pane the user wishes to show.
         * @param animationType               the {@link javafx.animation.Animation}
         *                                    <i>type</i> that is able to be
         *                                    configured via {@link application.pane.PaneAnimator.AnimationType},
         *                                    and will determine the current
         *                                    <i>switch</i> of {@link Pane}s
         *                                    {@link javafx.animation.Animation}.
         * @see application.pane.PaneAnimator.AnimationType
         */
        private void setPane(Event event,
                             BorderPane borderPaneToShowOnItsCenter,
                             Pane parentContainer, String pathToFXML,
                             PaneAnimator.AnimationType animationType) {

            // Get 'newPane' to show:
            Pane newPane = getPane(pathToFXML);

            // If the Animation state is enabled, preset an Animation:
            presentAnimation(event, parentContainer, newPane, animationType);

            // Present the extracted Pane:
            borderPaneToShowOnItsCenter.setCenter(parentContainer);
        }

        /**
         * Set the {@link javafx.scene.control.ButtonBase} to be pressed only
         * <i>once</i> when "spam" clicking it.
         */
        public void armOnce(ButtonBase buttonBase) {
            buttonBase.armedProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        if (!executeOrderButtonWasNotPressedYet.get()) {

                            // If the button was already pressed
                            buttonBase.disarm();
                        }
                        if (executeOrderButtonWasNotPressedYet.get()) {

                            // If this is the first time the button is being pressed
                            executeOrderButtonWasNotPressedYet.setValue(false);
                        }
                    });
        }

        /**
         * This method makes sure to {@link PaneAnimator.Handler#handle(Event)}
         * a {@link ButtonBase} with an {@link javafx.animation.Animation},
         * while enforcing the {@link ButtonBase} to be pressed at most
         * <b>once</b>.
         * <p>This means, the user should not be able to "spam" click the
         * {@link ButtonBase}.
         *
         * @param buttonBase      the {@link ButtonBase} to be configured.
         * @param borderPane      the {@link BorderPane} to show on its CENTER
         *                        the
         *                        <i>newPane</i>.
         * @param parentContainer the {@code Parent} {@code Pane} {@code
         *                        Container} of the new scene to be shown. <p>
         *                        Note: this must be a {@code Container} that is
         *                        able to use the {@link Pane#getChildren()} in
         *                        order to {@code add} children to it.</p>
         * @param pathToFXML      the <i>path</i> to the <tt>.fxml</tt> file for
         *                        the new scene to be shown.
         * @param animationType   the <i>type</i> of {@link Animation} to be
         *                        shown while transitioning between the {@code
         *                        replaceAblePane} and the
         *                        <i>new</i> {@link Pane}.
         */
        public void handleOnce(ButtonBase buttonBase, BorderPane borderPane,
                               Pane parentContainer,
                               PaneAnimator.AnimationType animationType,
                               String pathToFXML) {
            if (handle) {

                // define 'buttonBase':
                buttonBase.setOnAction(
                        new PaneAnimator.Handler(borderPane, parentContainer,
                                pathToFXML, animationType));

                /*
                 * Set the 'buttonBase' to be pressed only ONCE when "spam"
                 * clicking it.
                 */
                armOnce(buttonBase);
                if (runnable != null) { runnable.run(); }
            }
        }

        public void handleOnce(ButtonBase buttonBase) {
            handleOnce(buttonBase, borderPaneToShowOnItsCenter, parentContainer,
                    animationType, pathToFXML);
        }

    }
}
