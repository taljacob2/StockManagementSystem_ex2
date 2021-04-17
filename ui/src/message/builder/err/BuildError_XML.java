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
public class BuildError_XML extends BuildError {

    public BuildError_XML() {

        // append the message.
        stringBuilder.append("There is a problem with your '.xml' file: ");
    }

    public String suffix() {

        // append the message.
        stringBuilder.append("It does not have the '.xml' suffix.");

        // return the String.
        return getMessage();
    }
}
