package application.javafxapp;

import application.pane.PaneAnimator;
import javafx.event.Event;
import javafx.scene.control.ButtonBase;

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

    public JavaFXAppHandler(String pathToFXML, boolean handle) {
        super(JavaFXAppController.getStaticBorderPane(),
                JavaFXAppController.getParentContainer(), pathToFXML,
                JavaFXAppController.getAnimationType(), handle);
    }

    public JavaFXAppHandler(String pathToFXML, Runnable runnable) {
        super(JavaFXAppController.getStaticBorderPane(),
                JavaFXAppController.getParentContainer(), pathToFXML,
                JavaFXAppController.getAnimationType(), runnable);
    }

    public JavaFXAppHandler(String pathToFXML, boolean handle,
                            Runnable runnable) {
        super(JavaFXAppController.getStaticBorderPane(),
                JavaFXAppController.getParentContainer(), pathToFXML,
                JavaFXAppController.getAnimationType(), handle, runnable);
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
     * @param handle     Indicates whether to invoke the {@link #handle(Event)}
     *                   method or not.
     * @see application.pane.PaneAnimator.Handler#handleOnce(ButtonBase)
     */
    public static void handleOnce(ButtonBase buttonBase, String pathToFXML,
                                  boolean handle) {
        new JavaFXAppHandler(pathToFXML, handle).handleOnce(buttonBase);
    }

    /**
     * This <b>static</b> method makes sure to {@link PaneAnimator.Handler#handle(Event)}
     * a {@link ButtonBase} with an {@link javafx.animation.Animation}.
     *
     * @param buttonBase the {@link ButtonBase} to be configured.
     * @param pathToFXML the <i>path</i> to the <tt>.fxml</tt> file for the new
     *                   scene to be shown.
     * @param handle     Indicates whether to invoke the {@link #handle(Event)}
     *                   method or not.
     * @see application.pane.PaneAnimator.Handler#handle(ButtonBase)
     */
    public static void handle(ButtonBase buttonBase, String pathToFXML,
                              boolean handle) {
        new JavaFXAppHandler(pathToFXML, handle).handle(buttonBase);
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
     * @param runnable   Stores here a {@link Runnable} to invoke its {@link
     *                   Runnable#run()} method, right after invoking the {@link
     *                   #handle(Event)}'s body.
     * @see application.pane.PaneAnimator.Handler#handleOnce(ButtonBase)
     */
    public static void handleOnce(ButtonBase buttonBase, String pathToFXML,
                                  Runnable runnable) {
        new JavaFXAppHandler(pathToFXML, runnable).handleOnce(buttonBase);
    }

    /**
     * This <b>static</b> method makes sure to {@link PaneAnimator.Handler#handle(Event)}
     * a {@link ButtonBase} with an {@link javafx.animation.Animation}.
     *
     * @param buttonBase the {@link ButtonBase} to be configured.
     * @param pathToFXML the <i>path</i> to the <tt>.fxml</tt> file for the new
     *                   scene to be shown.
     * @param runnable   Stores here a {@link Runnable} to invoke its {@link
     *                   Runnable#run()} method, right after invoking the {@link
     *                   #handle(Event)}'s body.
     * @see application.pane.PaneAnimator.Handler#handle(ButtonBase)
     */
    public static void handle(ButtonBase buttonBase, String pathToFXML,
                              Runnable runnable) {
        new JavaFXAppHandler(pathToFXML, runnable).handle(buttonBase);
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
     * @param handle     Indicates whether to invoke the {@link #handle(Event)}
     *                   method or not.
     * @param runnable   Stores here a {@link Runnable} to invoke its {@link
     *                   Runnable#run()} method, right after invoking the {@link
     *                   #handle(Event)}'s body.
     * @see application.pane.PaneAnimator.Handler#handleOnce(ButtonBase)
     */
    public static void handleOnce(ButtonBase buttonBase, String pathToFXML,
                                  boolean handle, Runnable runnable) {
        new JavaFXAppHandler(pathToFXML, handle, runnable)
                .handleOnce(buttonBase);
    }

    /**
     * This <b>static</b> method makes sure to {@link PaneAnimator.Handler#handle(Event)}
     * a {@link ButtonBase} with an {@link javafx.animation.Animation}.
     *
     * @param buttonBase the {@link ButtonBase} to be configured.
     * @param pathToFXML the <i>path</i> to the <tt>.fxml</tt> file for the new
     *                   scene to be shown.
     * @param handle     Indicates whether to invoke the {@link #handle(Event)}
     *                   method or not.
     * @param runnable   Stores here a {@link Runnable} to invoke its {@link
     *                   Runnable#run()} method, right after invoking the {@link
     *                   #handle(Event)}'s body.
     * @see application.pane.PaneAnimator.Handler#handle(ButtonBase)
     */
    public static void handle(ButtonBase buttonBase, String pathToFXML,
                              boolean handle, Runnable runnable) {
        new JavaFXAppHandler(pathToFXML, handle, runnable).handle(buttonBase);
    }
}
