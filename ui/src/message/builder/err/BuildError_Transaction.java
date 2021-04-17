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
public class BuildError_Transaction extends BuildError {

    public String buildFail() {

        // append the message.
        stringBuilder.append("Transaction failed to be built.");

        // return the String.
        return getMessage();
    }

}
