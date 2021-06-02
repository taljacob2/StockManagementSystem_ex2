package message.print;

/**
 * Save here all the log output of the program, showing all the
 * <i>Messages</i> that were printed.
 */
public class Log {

    private static StringBuilder messageLog = new StringBuilder();

    public static StringBuilder getMessageLog() {
        return messageLog;
    }

    public static void setMessageLog(StringBuilder messageLog) {
        Log.messageLog = messageLog;
    }
}
