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
public class BuildError_Stocks extends BuildError {

    public String printEmpty() {

        // append the message.
        stringBuilder.append("There are no Stocks available in the system.");

        // return the String.
        return getMessage();
    }

    public String unFoundSymbol(String symbol) {

        // append the message.
        stringBuilder.append("The '").append(symbol)
                .append("' Symbol was not found in the stocks Collection.");

        // return the String.
        return getMessage();
    }

}
