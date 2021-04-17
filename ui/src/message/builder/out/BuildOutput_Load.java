package message.builder.out;

import message.builder.BuildMessage;

/**
 * This class is a sub-class in the message building process.
 * <p>Represents a part of an {@code Output} message.</p>
 *
 * @version 1.0
 * @see BuildMessage
 * @see BuildOutput
 */
public class BuildOutput_Load extends BuildOutput_XML {

    public String success(String filePath) {

        // append the message.
        stringBuilder.append("has been successfully loaded from: ")
                .append(filePath);

        // return the String.
        return getMessage();
    }

}
