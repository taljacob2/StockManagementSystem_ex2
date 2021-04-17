package message.builder;

import message.builder.err.BuildError;
import message.builder.out.BuildOutput;

/**
 * This class in the parent class of all the message building process. The class
 * uses {@link #stringBuilder} to {@link StringBuilder#append(String)} {@code
 * String}s according to the user's decision.
 *
 * <blockquote>The {@link StringBuilder#append(String)} is called for each
 * child in the inheritance chain.</blockquote>
 * <p>
 * There are two hierarchies:
 * <ul>
 *     <li>{@link BuildOutput} - for {@link System#out} {@code PrintStream} messages.</li>
 *     <li>{@link BuildError} - for {@link System#err} {@code PrintStream} messages.</li>
 * </ul>
 *
 * @version 1.0
 */

public class BuildMessage {

    /**
     * non-static, in order to create a new {@link StringBuilder} on each
     * Construct.
     */
    protected StringBuilder stringBuilder;

    public BuildMessage() {
        stringBuilder = new StringBuilder();
    }

    public String getMessage() {

        // return the String.
        return stringBuilder.toString();
    }
}


