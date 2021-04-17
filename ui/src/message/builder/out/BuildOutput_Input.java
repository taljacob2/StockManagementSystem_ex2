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
public class BuildOutput_Input extends BuildOutput {

    public String please(String typeOfVariable, String nameOfVariable) {

        // append the message.
        stringBuilder.append("Please provide a ").append(typeOfVariable)
                .append(" ").append(nameOfVariable).append(": ");

        // return the String.
        return getMessage();
    }

    public String please(String typeOfVariable, String nameOfVariable,
                         String appendMessage) {

        // append the message.
        stringBuilder.append("Please provide the ").append(typeOfVariable)
                .append(" ").append(nameOfVariable).append(" ")
                .append(appendMessage).append(": ");

        // return the String.
        return getMessage();
    }

    public String success(String firstMessage) {

        // append the message.
        stringBuilder.append(firstMessage).append(" ").append("succeeded");

        // return the String.
        return getMessage();
    }
}
