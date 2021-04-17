package stock.database;

import engine.collection.AddTabsCollection;
import engine.collection.Periodable;
import engine.collection.list.SortableLinkedList;
import order.BuyOrders;
import order.Order;
import order.SellOrders;
import transaction.Transaction;
import transaction.Transactions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/**
 * This class manages all the {@link Order}(s) and {@link Transaction}(s) of a
 * specific {@link stock.Stock}.
 * <p>There are 3 main {@code Collection}s:</p>
 * <ul>
 *     <li>{@link #awaitingBuyOrders}.</li>
 *     <li>{@link #awaitingSellOrders}.</li>
 *     <li>{@link #successfullyFinishedTransactions}.</li>
 * </ul>
 *
 * @version 1.2
 */
@XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "rse-data-base")
public class StockDataBase {

    /**
     * Element = {@link Order}.
     */
    @XmlElement(name = "rse-buy-orders") private BuyOrders awaitingBuyOrders;

    /**
     * Element = {@link Order}.
     */
    @XmlElement(name = "rse-sell-orders") private SellOrders awaitingSellOrders;

    /**
     * Element = {@link Transaction}.
     */
    @XmlElement(name = "rse-successfully-finished-transactions")
    private Transactions successfullyFinishedTransactions;

    /**
     * Constructor:
     * <p>initializes all fields.</p>
     * <p>Note:</p>
     * <ul>
     *     <li> <i>Must</i> initialize the fields with their {@code
     *          Constructor}s.</li>
     *     <li>{@link SortableLinkedList} {@code Overrides} each {@code toString()}.</li>
     * </ul>
     *
     * @see AddTabsCollection
     */
    public StockDataBase() {

        awaitingBuyOrders = new BuyOrders();

        awaitingSellOrders = new SellOrders();

        successfullyFinishedTransactions = new Transactions();

    }

    public BuyOrders getAwaitingBuyOrders() {
        return awaitingBuyOrders;
    }

    public void setAwaitingBuyOrders(BuyOrders awaitingBuyOrders) {
        this.awaitingBuyOrders = awaitingBuyOrders;
    }

    public SellOrders getAwaitingSellOrders() {
        return awaitingSellOrders;
    }

    public void setAwaitingSellOrders(SellOrders awaitingSellOrders) {
        this.awaitingSellOrders = awaitingSellOrders;
    }

    public Transactions getSuccessfullyFinishedTransactions() {
        return successfullyFinishedTransactions;
    }

    public void setSuccessfullyFinishedTransactions(
            Transactions successfullyFinishedTransactions) {
        this.successfullyFinishedTransactions =
                successfullyFinishedTransactions;
    }

    /**
     * This method calculates the {@code Total-Collection-Period} of all the
     * {@code Elements} in the {@link Collection} and returns it.
     *
     * @param collection is the {@link Collection} to calculate its
     *                   <i>period</i>.
     * @param <E>        is the element in the {@code collection} given.
     * @return The {@code Total-Collection-Period}.
     */
    public <E extends Periodable> long getTotalPeriod(
            Collection<E> collection) {
        long totalPeriod = 0L;
        for (E i : collection) {
            totalPeriod += i.getPeriod();
        }
        return totalPeriod;
    }

}

