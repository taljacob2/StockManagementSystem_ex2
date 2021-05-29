package message.builder.err;

import message.builder.BuildMessage;

/**
 * This {@code class} is a sub-class in the message building process.
 * <p>Represents a part of an {@code Error} message.</p>
 *
 * @version 1.0
 * @see BuildMessage
 * @see BuildError
 */
public class BuildError_Users extends BuildError {

    public String getEmpty() {

        // append the message.
        stringBuilder.append("There are no Users available in the system.");

        // return the String.
        return getMessage();
    }

}
