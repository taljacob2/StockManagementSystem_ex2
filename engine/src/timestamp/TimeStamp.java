package timestamp;

import order.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represents the format of a TimeStamp.
 * <p>Has the method {@link #getTimeStamp()} to get a timeStamp of current
 * execution.</p>
 *
 * @version 1.0
 */
public class TimeStamp {

    /**
     * The date format of printing.
     */
    private static final String timeStampFormat = "HH:mm:ss:SSS";

    /**
     * Prevent others to call the Constructor. Thus, private Constructor.
     */
    private TimeStamp() {}

    /**
     * The timeStamp of the {@link Order}'s execution.
     *
     * @return current timeStamp of the system.
     */
    public static String getTimeStamp() {
        return DateTimeFormatter.ofPattern(timeStampFormat)
                .format(LocalDateTime.now());
    }

}
