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
public class BuildError_Save extends BuildError_XML {

    public String noStocksToSave() {

        // append the message.
        stringBuilder.append("There are no Stocks to save.");

        // return the String.
        return getMessage();
    }

    public String writeFail() {

        // append the message.
        stringBuilder.append("Program cannot write to it properly.");

        // return the String.
        return getMessage();
    }


}
