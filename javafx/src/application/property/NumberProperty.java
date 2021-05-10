package application.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * This {@code class} <i>wraps</i> a {@code double} number as a {@link
 * DoubleProperty}. The class is mainly used for {@code binding} a
 * <tt>JavaFX</tt> {@code Property} to a {@code double} number.
 */
public class NumberProperty {

    private DoubleProperty number;

    public final double getNumber() {
        if (number != null) {
            return number.get();
        } else { return 0; }
    }

    public final void setNumber(double number) {
        this.getProperty().set(number);
    }

    public final DoubleProperty getProperty() {

        // similar to Singleton getInstance():
        if (number == null) {
            number = new SimpleDoubleProperty(0);
        }
        return number;
    }
}
