package application.animation;

/**
 * This class stores a {@code boolean} value that according to it the
 * <tt>JavaFX</tt> {@code Animation} would occur.
 * <p>
 * In case the user wishes to <i>disable</i> the <tt>JavaFX</tt> {@code
 * Animation}, he can use the {@link #setAnimation(boolean)} method to do so.
 */
public class Animation {

    /**
     * Stores here the current <tt>JavaFX</tt> {@code Animation} state.
     * <p>
     * Note: Default state of animation is {@code true}.
     */
    private static boolean animation = true;

    /**
     * @return the current <tt>JavaFX</tt> {@code Animation} state.
     */
    public static boolean getAnimation() {
        return animation;
    }

    /**
     * Sets the <tt>JavaFX</tt> {@code Animation} state.
     *
     * @param value the new state.
     */
    public static void setAnimation(boolean value) {
        animation = value;
    }
}
