package application.javafxapp;

import application.pane.PaneAnimator;
import javafx.event.Event;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This {@code Class} is made for optimizing the invocations for {@link
 * JavaFXAppController} specifically.
 */
public class JavaFXAppHandler extends PaneAnimator.Handler {

    public JavaFXAppHandler(String pathToFXML) {
        super(JavaFXAppController.getStaticBorderPane(),
                JavaFXAppController.getParentContainer(), pathToFXML,
                JavaFXAppController.getAnimationType());
    }

    public JavaFXAppHandler(BorderPane borderPaneToShowOnItsCenter,
                            Pane parentContainer, String pathToFXML,
                            PaneAnimator.AnimationType animationType) {
        super(borderPaneToShowOnItsCenter, parentContainer, pathToFXML,
                animationType);
    }

    public JavaFXAppHandler(BorderPane borderPaneToShowOnItsCenter,
                            Pane parentContainer, String pathToFXML,
                            PaneAnimator.AnimationType animationType,
                            boolean handle) {
        super(borderPaneToShowOnItsCenter, parentContainer, pathToFXML,
                animationType, handle);
    }

    public JavaFXAppHandler(BorderPane borderPaneToShowOnItsCenter,
                            Pane parentContainer, String pathToFXML,
                            PaneAnimator.AnimationType animationType,
                            Runnable runnable) {
        super(borderPaneToShowOnItsCenter, parentContainer, pathToFXML,
                animationType, runnable);
    }

    public JavaFXAppHandler(BorderPane borderPaneToShowOnItsCenter,
                            Pane parentContainer, String pathToFXML,
                            PaneAnimator.AnimationType animationType,
                            boolean handle, Runnable runnable) {
        super(borderPaneToShowOnItsCenter, parentContainer, pathToFXML,
                animationType, handle, runnable);
    }

    /**
     * This <b>static</b> method makes sure to {@link PaneAnimator.Handler#handle(Event)}
     * a {@link ButtonBase} with an {@link javafx.animation.Animation}, while
     * enforcing the {@link ButtonBase} to be pressed at most <b>once</b>.
     * <p>This means, the user should not be able to "spam" click the
     * {@link ButtonBase}.
     *
     * @param buttonBase the {@link ButtonBase} to be configured.
     * @param pathToFXML the <i>path</i> to the <tt>.fxml</tt> file for the new
     *                   scene to be shown.
     * @see application.pane.PaneAnimator.Handler#handleOnce(ButtonBase)
     */
    public static void handleOnce(ButtonBase buttonBase, String pathToFXML) {
        new JavaFXAppHandler(pathToFXML).handleOnce(buttonBase);
    }

    /**
     * This <b>static</b> method makes sure to {@link PaneAnimator.Handler#handle(Event)}
     * a {@link ButtonBase} with an {@link javafx.animation.Animation}.
     *
     * @param buttonBase the {@link ButtonBase} to be configured.
     * @param pathToFXML the <i>path</i> to the <tt>.fxml</tt> file for the new
     *                   scene to be shown.
     * @see application.pane.PaneAnimator.Handler#handle(ButtonBase)
     */
    public static void handle(ButtonBase buttonBase, String pathToFXML) {
        new JavaFXAppHandler(pathToFXML).handle(buttonBase);
    }

}
