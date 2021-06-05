package application.pane.resources.afterexecutionsummary.container;

import application.pane.resources.afterexecutionsummary.AfterExecutionSummary;
import engine.collection.list.SortableLinkedList;
import order.Order;
import transaction.Transaction;

/**
 * Contains all {@link Order}s and {@link Transaction}s made after
 * <i>order-execution.</i>
 *
 * @see application.pane.resources.orderexecution.OrderExecution
 * @see AfterExecutionSummary
 */
public class AfterExecutionOrderAndTransactionContainer {

    /**
     * {@link java.util.List} of all {@link Order}s remained after
     * <i>order-execution.</i>
     */
    private SortableLinkedList<Order> remainderOrders;

    /**
     * {@link java.util.List} of all {@link Transaction}s made after
     * <i>order-execution.</i>
     */
    private SortableLinkedList<Transaction> transactions;

    public AfterExecutionOrderAndTransactionContainer() {
        remainderOrders = new SortableLinkedList<>();
        transactions = new SortableLinkedList<>();
    }

    public SortableLinkedList<Order> getRemainderOrders() {
        return remainderOrders;
    }

    public void setRemainderOrders(SortableLinkedList<Order> remainderOrders) {
        this.remainderOrders = remainderOrders;
    }

    public SortableLinkedList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(SortableLinkedList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
