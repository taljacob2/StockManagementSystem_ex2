package order;

/**
 * This class defines all the types of orders.
 * <p> Each type has the {@link #isLMT()} method to check if a
 * desiredLimitPrice limit is required for an order.</p>
 *
 * @version 1.0
 */
public enum OrderType {
    LMT {
        @Deprecated @Override public boolean isLMT() {
            return true;
        }
    }, MKT {
        @Deprecated @Override public boolean isLMT() {
            return false;
        }
    }, FOX {
        @Deprecated @Override public boolean isLMT() {
            return false;
        }
    }, IOC {
        @Deprecated @Override public boolean isLMT() {
            return false;
        }
    };

    public abstract boolean isLMT();
}
