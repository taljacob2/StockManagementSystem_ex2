package message.print;

import application.Controller;
import application.dialog.FxDialogs;

import static message.print.MessagePrint.Stream.ERR;
import static message.print.MessagePrint.Stream.OUT;

/**
 * This class bundles all program's prints to a {@link java.io.PrintStream} and
 * prints all the given messages.
 *
 * @version 1.0
 */
public class MessagePrint {

    /**
     * private Constructor, to prevent others to make instances of this class.
     */
    private MessagePrint() {}

    public static void println(Stream stream, String message) {
        if (stream == OUT) {
            // System.out.println(message);
            FxDialogs.showInformation("INFO", message);
        } else if (stream == ERR) {
            // System.err.println(message);
            // Controller.consoleOutput.setText(message); // TODO need to color red the ERROR messages with CSS
            FxDialogs.showError("ERROR",
                    message); // TODO need to color red the ERROR messages with CSS
        }

        // TODO - Note: re-setting the JavaFX progress bar:
        Controller.resetProgressBar();
    }

    public static void print(Stream stream, String message) {
        if (stream == OUT) {
            System.out.print(message);
        } else if (stream == ERR) {
            System.err.print(message);
        }
    }

    /**
     * Defines in what {@link java.io.PrintStream} to print the messages.
     */
    public enum Stream {OUT, ERR}
}
