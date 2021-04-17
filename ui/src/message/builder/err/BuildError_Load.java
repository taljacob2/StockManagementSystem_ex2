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
public class BuildError_Load extends BuildError_XML {

    public String stocks() {

        // append the message.
        stringBuilder
                .append("The stocks Collection couldn't be read properly.");

        // return the String.
        return getMessage();
    }

    public String fail() {

        // append the message.
        stringBuilder.append("Program cannot read from it properly.");

        // return the String.
        return getMessage();
    }

    public String fileDoesNotExist() {

        // append the message.
        stringBuilder.append("File does not exist.");

        // return the String.
        return getMessage();
    }

    public String stocksInvalid_SymbolsAmbiguity() {

        // append the message.
        stringBuilder
                .append("Invalid Stocks - There is an ambiguity in the 'symbol' [String]s: ");

        // return the String.
        return getMessage();
    }

    public String stocksInvalid_CompanyNameAmbiguity() {

        // append the message.
        stringBuilder
                .append("Invalid Stocks - There is an ambiguity in the 'company-name' [String]s: ");

        // return the String.
        return getMessage();
    }

}