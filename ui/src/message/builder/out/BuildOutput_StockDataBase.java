package message.builder.out;

import message.Message;
import message.builder.BuildMessage;
import message.builder.err.BuildError;
import message.print.MessagePrint;
import order.Order;
import transaction.Transaction;

/**
 * This class is a sub-class in the message building process.
 * <p>Represents a part of an {@code Output} message.</p>
 *
 * @version 1.0
 * @see BuildMessage
 * @see BuildOutput
 */
public class BuildOutput_StockDataBase extends BuildOutput {

    public String printEmpty(TypeOfCollection typeOfCollection) {

        // append the message.
        if (typeOfCollection ==
                TypeOfCollection.SUCCESSFULLY_FINISHED_TRANSACTIONS) {
            stringBuilder
                    .append("There have been no Transactions with this stock yet.\n ");
        } else if (typeOfCollection == TypeOfCollection.AWAITING_BUY_ORDERS) {
            stringBuilder.append("There are no " + "awaiting 'Buy' Orders" +
                    " for this stock right now.\n");
        } else if (typeOfCollection == TypeOfCollection.AWAITING_SELL_ORDERS) {
            stringBuilder.append("There are no " + "awaiting 'Sell' Orders" +
                    " for this stock right now.\n");
        }

        // return the String.
        return getMessage();
    }

    /**
     * @param object {@link transaction.Transaction} or {@link order.Order}. the
     *               method detects the {@code Object} given.
     * @return message of a successful addition.
     */
    public String newSuccessAdd(Object object) {

        // append the message.
        if (object instanceof Transaction) {
            Transaction transaction = (Transaction) object;
            stringBuilder
                    .append(Message.Out.Input.success("Transaction creation"))
                    .append(":\n\t").append(transaction.toString());

        } else if (object instanceof Order) {
            Order order = (Order) object;

            stringBuilder.append(Message.Out.Input.success("Order creation"))
                    .append(":\n\t").append(order.toString());

        } else {
            MessagePrint.println(MessagePrint.Stream.ERR,
                    new BuildError().getMessage() +
                            "Problem when adding to the data-base.");
        }

        // return the String.
        return getMessage();
    }

    public String printOrderPerformedInItsEntirety() {

        // append the message.

        stringBuilder
                .append("The Order was successfully performed in its entirety, and was removed.");

        // return the String.
        return getMessage();
    }

    /**
     * Defines the message the method {@link #printEmpty(TypeOfCollection)}
     * prints.
     */
    public enum TypeOfCollection {
        AWAITING_BUY_ORDERS, AWAITING_SELL_ORDERS,
        SUCCESSFULLY_FINISHED_TRANSACTIONS
    }

}
