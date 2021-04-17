package currency;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This class sets the print <i>format</i> for <i>currencies</i>.
 *
 * @version 1.0
 */
public class Currency {
    private static final Locale locale = Locale.US;

    public static final NumberFormat numberFormat =
            NumberFormat.getCurrencyInstance(locale);

    static {
        numberFormat.setMaximumFractionDigits(0);
    }
}
