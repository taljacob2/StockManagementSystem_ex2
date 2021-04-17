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
public class BuildError_Input extends BuildError {

    public String mismatch(String requiredType, String providedType) {

        // append the message.
        stringBuilder.append("You should have provided a ").append(requiredType)
                .append(", but you have provided a ").append(providedType)
                .append(".");

        // return the String.
        return getMessage();
    }
}
