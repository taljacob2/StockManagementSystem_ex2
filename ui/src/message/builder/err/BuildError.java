package message.builder.err;

import message.builder.BuildMessage;

/**
 * This class is a sub-class in the message building process.
 * <p>This class in the parent class of all the {@code Error} messages in the
 * building process.</p>
 *
 * @version 1.0
 * @see BuildMessage
 */
public class BuildError extends BuildMessage {

    public BuildError() {

        // append the message.
        stringBuilder.append("ERROR: ");
    }

}
