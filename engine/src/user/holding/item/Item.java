package user.holding.item;

import currency.Currency;
import engine.Engine;
import message.print.MessagePrint;
import stock.Stock;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Objects;

/**
 * This {@code class} represents a Buy or Sell Order.
 *
 * @version 1.0
 */
@XmlRootElement(name = "rse-item") @XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Comparable<Item> {

    @XmlAttribute(name = "symbol", required = true) private String symbol;

    @XmlAttribute(name = "quantity", required = true) private long quantity;


    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public Item() {}

    public Item(String symbol, long quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override public String toString() {
        Stock stock = getStock();
        return "Item{" + "symbol='" + symbol + '\'' + ", quantity=" + quantity +
                ", price=" + Currency.numberFormat.format(stock.getPrice()) +
                '}';

    }

    @Override public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Item item = (Item) o;
        return quantity == item.quantity && Objects.equals(symbol, item.symbol);
    }

    @Override public int hashCode() {
        return Objects.hash(symbol, quantity);
    }

    @Override public int compareTo(Item o) { // TODO: need to implement??
        return 0;
    }

    public Stock getStock() {
        try {
            return Engine.getStockBySymbol(symbol);
        } catch (IOException e) {

            // Note: this exception should not happen thanks to the initial check of users.
            MessagePrint.println(MessagePrint.Stream.OUT, e.getMessage());
        }
        return null;
    }
}
