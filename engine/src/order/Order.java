package order;

import currency.Currency;
import engine.collection.Periodable;
import timestamp.TimeStamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * This class represents a Buy or Sell Order.
 *
 * @version 1.0
 */
@XmlRootElement(name = "rse-order") @XmlAccessorType(XmlAccessType.FIELD)
public class Order implements Comparable<Order>, Periodable {

    /**
     * Timestamp of current execution.
     */
    @XmlElement(name = "time-stamp", required = true)
    private String timeStamp;

    @XmlElement(name = "order-direction", required = true)
    private OrderDirection orderDirection;

    @XmlElement(name = "order-type", required = true)
    private OrderType orderType;

    @XmlElement(name = "quantity", required = true)
    private long quantity;

    /**
     * Initialized only if the {@link #orderType} is not {@link OrderType#LMT},
     * else initialized to {@code 0}.
     */
    @XmlElement(name = "desired-limit-price", required = true)
    private long desiredLimitPrice;

    public Order(OrderDirection direction, OrderType type, long quantity,
                 long desiredLimitPrice) {
        timeStamp = TimeStamp.getTimeStamp();
        this.quantity = quantity;
        this.orderDirection = direction;
        this.orderType = type;
        this.desiredLimitPrice = desiredLimitPrice;
    }

    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public Order() {}

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public OrderDirection getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(OrderDirection orderDirection) {
        this.orderDirection = orderDirection;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public long getDesiredLimitPrice() {
        return desiredLimitPrice;
    }

    public void setDesiredLimitPrice(long desiredLimitPrice) {
        this.desiredLimitPrice = desiredLimitPrice;
    }

    @Override public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Order order = (Order) o;
        return quantity == order.quantity &&
                desiredLimitPrice == order.desiredLimitPrice &&
                Objects.equals(timeStamp, order.timeStamp) &&
                orderDirection == order.orderDirection &&
                orderType == order.orderType;
    }

    @Override public int hashCode() {
        return Objects.hash(timeStamp, orderDirection, orderType, quantity,
                desiredLimitPrice);
    }

    @Override public String toString() {
        return "Order{" + "timeStamp='" + timeStamp + '\'' +
                ", orderDirection=" + orderDirection + ", orderType=" +
                orderType + ", quantity=" + quantity + ", desiredLimitPrice=" +
                Currency.numberFormat.format(desiredLimitPrice) +
                ", orderPeriod=" + Currency.numberFormat.format(getPeriod()) +
                '}';
    }

    /**
     * This method compares by the {@link #desiredLimitPrice} values of the
     * {@code Order}s. but, if the {@link #desiredLimitPrice}s are equal, the
     * method compares by the {@link #timeStamp} values of the {@code Order}s.
     *
     * @param o the <i> other </i> {@code Order} to be compared to.
     * @return an {@code int} indicates if {@code this} {@code Order} is less,
     * equals, or greater than the <i> other </i> {@code Order} given.
     */
    @Override public int compareTo(Order o) {

        // first, compare by 'desiredLimitPrice':
        /*
         * if the Orders are 'Buy' Orders, prioritize the highest price at the top.
         * if the Orders are 'Sell' Orders, prioritize the lowest price at the top.
         */
        int result;
        if ((this.getOrderDirection() == OrderDirection.BUY) &&
                (o.getOrderDirection() == OrderDirection.BUY)) {
            result = Long.compare(o.getDesiredLimitPrice(),
                    this.getDesiredLimitPrice());
        } else if ((this.getOrderDirection() == OrderDirection.SELL) &&
                (o.getOrderDirection() == OrderDirection.SELL)) {
            result = Long.compare(this.getDesiredLimitPrice(),
                    o.getDesiredLimitPrice());
        } else {

            // Orders have different Directions, compare by 'timeStamps' only:
            result = 0;
        }


        // if the prices are equal:
        if (result == 0) {

            // compare by 'timeStamp':
            result = this.timeStamp.compareTo(o.timeStamp);
            if (result == 0) {

                // if 'timeStamps' are equal, insert the most recent on top.
                return -1;
            } else { return result; }
        }
        return result;
    }

    /**
     * The total <i>price worth</i> of this {@code Order} is: the
     * <tt>{@link #quantity}</tt> of the {@link stock.Stock}s times the
     * {@link #desiredLimitPrice} of each {@link stock.Stock} in the {@code
     * Order}.
     *
     * @return {@code Order-Worth} desiredLimitPrice = <tt>Period</tt>.
     */
    @Override public long getPeriod() {
        return quantity * desiredLimitPrice;
    }

}
