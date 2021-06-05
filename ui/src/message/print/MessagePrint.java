package message.print;

import application.javafxapp.JavaFXAppController;

import static message.print.MessagePrint.Stream.ERR;
import static message.print.MessagePrint.Stream.OUT;

/**
 * This {@code class} bundles all program's prints to a {@link
 * java.io.PrintStream} and prints all the given messages.
 *
 * @version 1.0
 */
public class MessagePrint {

    /**
     * {@code private} Constructor, to prevent others to make instances of this
     * class.
     */
    private MessagePrint() {}

    public static void println(Stream stream, String message,
                               boolean appendToLog) {
        if (stream == OUT) {
            // DEPRECATED: console
            // System.out.println(message);

            // DEPRECATED: pop-up
            // FxDialogs.showInformation("INFO", message);

            // set text and color of text in the statusLabel:
            JavaFXAppController.getStaticStatusLabel()
                    .setStyle("-fx-text-fill: rgb(202, 200, 197)");
            JavaFXAppController.getStaticStatusLabel().setText(message);
        } else if (stream == ERR) {
            // DEPRECATED: console
            // System.err.println(message);

            // DEPRECATED: pop-up
            // FxDialogs.showError("ERROR", message);

            // set text and color of text in the statusLabel:
            JavaFXAppController.getStaticStatusLabel()
                    .setStyle("-fx-text-fill: red");
            JavaFXAppController.getStaticStatusLabel().setText(message);
        }
        if (appendToLog) { Log.getMessageLog().append(message); }
    }

    /**
     * Default is to <i>append the message to Log</i>.
     *
     * @param stream  the {@code PrintStream} to output the <i>message</i> to.
     * @param message the <i>message</i> to print.
     */
    public static void println(Stream stream, String message) {
        println(stream, message, true);
    }

    /**
     * Defines in what {@link java.io.PrintStream} to print the messages.
     */
    public enum Stream {OUT, ERR}
}
