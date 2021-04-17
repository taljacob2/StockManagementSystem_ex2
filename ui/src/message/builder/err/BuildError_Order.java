package message.builder.err;

import message.builder.BuildMessage;

/**
 * This class is a sub-class in the message building process.
 * <p>Represents a part of an {@code Error} message.</p>
 *
 * @version 1.0
 * @see BuildMessage
 * @see BuildError
 */
public class BuildError_Order extends BuildError {

    public String buildFail() {

        // append the message.
        stringBuilder.append("Order failed to be built.");

        // return the String.
        return getMessage();
    }

    public String removeFail() {

        // append the message.
        stringBuilder.append("The Order Failed to be removed as expected.");

        // return the String.
        return getMessage();
    }

}
