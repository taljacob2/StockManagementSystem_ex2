package transaction;

import currency.Currency;
import engine.collection.Periodable;
import order.Order;
import stock.Stock;
import user.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * This {@code class} represents a {@code Transaction} of {@link stock.Stock}s.
 * <p>A {@code Transaction} occurs when two {@link Order}(s) happen to
 * match successfully:</p>
 *
 * <p>A '{@code Buy}' {@link Order} and a '{@code Sell}' {@link
 * Order}.</p>
 *
 * <p>annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.</p>
 *
 * @version 1.0
 */
@XmlRootElement(name = "rse-transaction") @XmlAccessorType(XmlAccessType.FIELD)
public class Transaction implements Comparable<Transaction>, Periodable {

    /**
     * The {@code TimeStamp} of the {@code Transaction}'s execution.
     */
    @XmlElement(name = "time-stamp", required = true) private String timeStamp;

    /**
     * The quantity of the {@link stock.Stock}s sold in the {@code
     * Transaction}.
     */
    @XmlElement(name = "quantity", required = true) private long quantity;

    /**
     * Price of each sold {@link stock.Stock}.
     */
    @XmlElement(name = "price", required = true) private long price;

    /**
     * The {@link user.User} who <i>sold</i> a {@link Stock} in the current
     * {@code Transaction}.
     */
    @XmlElement(name = "buying-user") private User buyingUser;

    /**
     * The {@link user.User} who <i>bought</i> a {@link Stock} in the current
     * {@code Transaction}.
     */
    @XmlElement(name = "selling-user") private User sellingUser;

    /**
     * The <i>serial-time</i> that the {@code Transaction} was created, when
     * viewing in a {@link application.pane.resources.afterexecutionsummary.AfterExecutionSummary}
     * {@code Pane}.
     */
    private long serialTime;

    public Transaction(Stock stock, String timeStamp, long quantity, long price,
                       User buyingUser, User sellingUser, long serialTime) {
        this.timeStamp = timeStamp;
        this.quantity = quantity;
        this.price = price;
        this.buyingUser = buyingUser;
        this.sellingUser = sellingUser;
        this.serialTime = serialTime;

        // forces update of the Stock's price:
        stock.setPrice(price);

        // forces update of the Stock's graph:
        stock.getStockGraph().getPriceList().add(stock.getPrice());
        stock.getStockGraph().getTimeStampList().add(timeStamp);
    }

    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public Transaction() {}

    public long getSerialTime() {
        return serialTime;
    }

    public void setSerialTime(long serialTime) {
        this.serialTime = serialTime;
    }

    public User getBuyingUser() {
        return buyingUser;
    }

    public void setBuyingUser(User buyingUser) {
        this.buyingUser = buyingUser;
    }

    public User getSellingUser() {
        return sellingUser;
    }

    public void setSellingUser(User sellingUser) {
        this.sellingUser = sellingUser;
    }

    @Override public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Transaction that = (Transaction) o;
        return quantity == that.quantity && price == that.price &&
                Objects.equals(timeStamp, that.timeStamp);
    }

    @Override public int hashCode() {
        return Objects.hash(timeStamp, quantity, price);
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override public String toString() {
        return "Transaction{" + "timeStamp='" + timeStamp + '\'' +
                ", quantity=" + quantity + ", price=" +
                Currency.numberFormat.format(price) + ", transactionPeriod=" +
                Currency.numberFormat.format(getPeriod()) + ", buyingUserName" +
                "=" + buyingUser.getName() + ", sellingUserName=" +
                sellingUser.getName() + '}';
    }

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

    /**
     * This method compares by the {@link #timeStamp} values of the {@code
     * Transaction}s.
     *
     * @param o the <i> other </i> {@code Transaction} to be compared to.
     * @return an {@code int} indicates if {@code this} {@code Transaction} is
     * less, equals, or greater than the <i> other </i> {@code Transaction}
     * given.
     */
    @Override public int compareTo(Transaction o) {

        // compare by 'timeStamp': the first is the most recent.
        int result = o.getTimeStamp().compareTo(this.getTimeStamp());
        if (result == 0) {

            // if 'timeStamps' are equal, insert the most recent on top.
            return -1;
        } else { return result; }
    }

    /**
     * The total <i>price worth</i> of this {@code Transaction} is: the
     * <tt>{@link #quantity}</tt> of the {@link stock.Stock}s times the
     * {@link #price} of each {@link stock.Stock} in the {@code Transaction}.
     *
     * @return {@code Transaction-Worth} <i>price</i> = <tt>Period</tt>.
     */
    @Override public long getPeriod() {
        return quantity * price;
    }

}
