package engine;

import application.Controller;
import engine.collection.EngineCollection;
import load.Descriptor;
import message.Message;
import message.builder.err.BuildError;
import message.print.MessagePrint;
import order.Order;
import order.OrderDirection;
import order.OrderType;
import stock.Stock;
import stock.Stocks;
import stock.database.StockDataBase;
import transaction.Transaction;
import user.User;
import user.Users;
import user.holding.Holdings;
import user.holding.item.Item;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * The Main Engine of the program.
 * <ul>
 * <li>Stores the data of the program.</li>
 * <li>Manages all commands given.</li>
 * </ul>
 *
 * @version 2.0
 */
public class Engine {

    /**
     * The program's stocks.
     */
    private static Stocks stocks;

    /**
     * The program's users.
     */
    private static Users users;

    /**
     * Empty constructor.
     * <blockquote><b>private constructor restricted to this class
     * itself</b></blockquote>
     */
    private Engine() {}

    /**
     * this method checks whether there is an ambiguity in <i>symbol(s)</i> and
     * <i>companyName(s)</i> in the stocks.
     *
     * @param collectionToCheck the collection to check.
     * @throws IOException with an appropriate message in case of an invalid
     *                     occurrence.
     */
    public static void checkValidStocks(
            EngineCollection<List<Stock>, Stock> collectionToCheck)
            throws IOException {

        // get the collection:
        List<Stock> list = collectionToCheck.getCollection();

        // bubble - compare:
        for (int i = 0; i < list.size(); ++i) {

            // TODO - Note: updating the JavaFX progress bar:
            Controller.setProgressBarValue(i, list.size());

            String i_symbol = list.get(i).getSymbol();
            String i_companyName = list.get(i).getCompanyName();
            for (int j = list.size() - 1; j > i; --j) {
                String j_symbol = list.get(j).getSymbol();
                String j_companyName = list.get(j).getCompanyName();
                if (i_symbol.equalsIgnoreCase(j_symbol)) {

                    /*
                     * found an equality of Strings between Symbols,
                     * means this File is invalid:
                     */
                    throw new IOException(Message.Err.XML.Load
                            .stocksInvalid_SymbolsAmbiguity() + "'" + i_symbol +
                            "' and '" + j_symbol + "'");
                }
                if (i_companyName.equalsIgnoreCase(j_companyName)) {

                    /*
                     * found an equality of Strings between companyNames,
                     * means this File is invalid:
                     */
                    throw new IOException(Message.Err.XML.Load
                            .stocksInvalid_CompanyNameAmbiguity() + "'" +
                            i_companyName + "' and '" + j_companyName + "'");
                }
            }
        }

        // passed all checks, thus valid.

    }

    /**
     * this method checks whether there is an ambiguity and validity in the
     * {@code users} {@code Collection}:
     * <ul>
     *     <li><i>checks for an ambiguity in the name(s)</i> of the {@code users}</li>
     *     <li>checks whether the <i>symbol(s)</i> of the stocks provided are
     *         truly stocks <i>symbol(s)</i> that already in the {@code Engine}.</li>
     * </ul>
     *
     * <p>Note: this must be invoked only <b>after</b> there are valid
     * {@link Stocks} in the {@link Engine}.</p>
     *
     * <p>checking equality of the {@link Item}(s) {@code Symbol} with the
     * {@link Stock} {@code Symbol} with case-sensitive.</p>
     *
     * @param collectionToCheck the collection to check.
     * @throws IOException with an appropriate message in case of an invalid
     *                     occurrence.
     */
    public static void checkValidUsers(
            EngineCollection<List<User>, User> collectionToCheck)
            throws IOException {

        // get the collection:
        List<User> usersCollection = collectionToCheck.getCollection();

        // bubble - compare:
        for (int i = 0; i < usersCollection.size(); ++i) {
            String i_name = usersCollection.get(i).getName();
            for (int j = usersCollection.size() - 1; j > i; --j) {
                String j_name = usersCollection.get(j).getName();
                if (i_name.equalsIgnoreCase(j_name)) {

                    /*
                     * found an equality of Strings between Names of users,
                     * means this File is invalid:
                     */
                    throw new IOException(
                            "Invalid Stocks - There is an ambiguity in the 'name' [String]s of the users: " +
                                    "'" + i_name + "' and '" + j_name +
                                    "'");  // TODO: make a message: usersInvalid_NamesAmbiguity !!
                }
            }

            // check the user's holdings:
            checkValidHoldings(usersCollection.get(i));
        }

        // passed all checks, thus valid.
    }

    /**
     * This method checks if a given {@link User}'s {@link Holdings} are valid.
     *
     * @param userToCheck the {@link User} to check its {@link Holdings}
     *                    validity.
     * @throws IOException if there is in as Error.
     */
    private static void checkValidHoldings(User userToCheck)
            throws IOException {
        List<Stock> stockCollection = stocks.getCollection();
        Holdings userHoldings = userToCheck.getHoldings();
        if (userHoldings != null) {
            List<Item> itemCollection = userHoldings.getCollection();

            /*
             * bubble - compare:
             * check if the Symbol of each Item is not equal to the
             * Symbols of the Stocks that are already in the Engine:
             */
            for (Item item : itemCollection) {
                String currentItemSymbol = item.getSymbol();

                for (int n = 0; n < stockCollection.size(); ++n) {

                    // check for equality of Strings with case-sensitive:
                    if (currentItemSymbol
                            .equals(stockCollection.get(n).getSymbol())) {

                        /*
                         * if there is an equality of Strings with
                         * case-sensitive thus this is a valid item.
                         * so there is no need to continue check.
                         */
                        break;
                    } else if ((n == (stockCollection.size() - 1)) &&
                            (!(currentItemSymbol.equals(stockCollection.get(n)
                                    .getSymbol())))) {

                        /*
                         * this is the last Stock available, and there
                         * is no equality of Strings between the Symbols,
                         * means this File is invalid:
                         */
                        throw new IOException(
                                "found a problem with the user: " + "'" +
                                        userToCheck.getName() + "': " +
                                        "the 'symbol' of the item: " + "'" +
                                        currentItemSymbol + "'" +
                                        " was not found as a Stock 'symbol' in the system"); // TODO: make a message: usersInvalid_SymbolsNotAvailable !! remember to name the 'name' of the current 'i_name' user.
                    }

                }

            }

            // else, all symbols in the 'holdings' collection are valid.
        }
    }

    /**
     * @param symbol the key to find the requested {@link Stock}: case
     *               in-sensitive.
     * @return the {@link Stock} that has the {@code Symbol} that was provided.
     * @throws IOException <ul>
     *                     <li>if we didn't find the {@link Stock} that has
     *                     the given {@code Symbol}.</li>
     *                     <li>if there are no {@link #stocks} at all.</li>
     *                     </ul>
     */
    public static Stock getStockBySymbol(String symbol) throws IOException {

        try {

            /*
             * get the collection:
             * (may throw NullPointerException if there are no Stocks already)
             */
            List<Stock> list = stocks.getCollection();

            // search for the given Symbol:
            for (Stock i : list) {
                String i_symbol = i.getSymbol();
                if (i_symbol.equalsIgnoreCase(symbol)) {

                    /*
                     * found an equality of Strings between Symbols,
                     * means we found the desired stock successfully.
                     */
                    return i;
                }
            }
        } catch (NullPointerException e) {
            throw new IOException(Message.Err.Stocks.getEmpty());
        }
        throw new IOException(Message.Err.Stocks.unFoundSymbol(symbol));
    }

    /**
     * @return wrap to a {@link Descriptor} that contains all the fields of
     * {@code this} class, such as:
     * <ul>
     *     <li>{@link #stocks} field.</li>
     * </ul>
     */
    public static Descriptor createDescriptor() {
        Descriptor descriptor = new Descriptor();
        descriptor.setStocks(stocks);
        descriptor.setUsers(users);
        return descriptor;
    }

    /**
     * This method checks if there are valid stocks loaded in the system.
     *
     * @return boolean: <ul>
     * <li>true if the stocks are valid.</li>
     * <li>else false.</li>
     * </ul>
     */
    public static boolean isStocks() {

        // first of all check if there are Stocks available in the system:
        try {
            Engine.getStocks();
            return true;
        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            return false;
        }
    }

    public static boolean isUsers() {

        // first of all check if there are Users available in the system:
        try {
            Engine.getUsers();
            return true;
        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            return false;
        }
    }

    /**
     * @return {@link #stocks} of the program.
     * @throws IOException if the {@link #stocks} are {@code null} - means
     *                     uninitialized.
     */
    public static Stocks getStocks() throws IOException {
        if (stocks != null) {
            return stocks;
        } else {
            throw new IOException(Message.Err.Stocks.getEmpty());
        }
    }

    public static void setStocks(Stocks stocks) {
        Engine.stocks = stocks;
    }

    /**
     * @return {@link #users} of the program.
     * @throws IOException if the {@link #users} are {@code null} - means
     *                     uninitialized.
     */
    public static Users getUsers() throws IOException {
        if (users != null) {
            return users;
        } else {
            throw new IOException(Message.Err.Stocks
                    .getEmpty()); // TODO: change to Message.Err.Users.printEmpty() ---- need to implement!!!
        }
    }

    public static void setUsers(Users users) {
        Engine.users = users;
    }

    /**
     * <b>The {@code Engine}'s core method.</b>
     * <p>
     * This method checks a single {@link Stock} (by passing its {@code Symbol}
     * as a parameter), reads all its {@link order.Order}(s) lists, and
     * calculates whether it is possible to create a {@link
     * transaction.Transaction} between two {@link order.Order}(s).
     * </p>
     *
     * @param stock        the stock the user wishes to check.
     * @param arrivedOrder place here the <i>last placed</i> {@link Order} of in
     *                     the stock's data-base. this means, that on the
     *                     calculation process of interaction between two
     *                     opposite already placed orders, this <i>last
     *                     placed</i> order would match the desiredLimitPrice
     *                     placed in another <i>opposite already placed</i>
     *                     order. thus means, the {@link transaction.Transaction}'s
     *                     desiredLimitPrice would be determined by the
     *                     <i>opposite already placed</i> order
     *                     desiredLimitPrice.
     * @see #checkForOppositeAlreadyPlacedOrders
     * @see #makeATransaction
     * @see #checkRemainders
     * @see #checkOppositeAlreadyPlacedOrderRemainder
     * @see #checkArrivedOrderRemainder
     */
    public static void calcOrdersOfASingleStock(Stock stock,
                                                Order arrivedOrder) {

        // get the dataBase of this Stock:
        StockDataBase dataBase = stock.getDataBase();

        /*
         * get the 'Buy' Orders Collection, and the 'Sell' Orders Collection,
         * sorted by desiredLimitPrice/timeStamp priority:
         *
         * Orders are sorted with the highest desiredLimitPrice at the top (= first),
         * and the lowest desiredLimitPrice at the bottom (= last).
         * upon finding that prices are equal, they are sorted by timeStamp priority.
         */
        List<Order> buyOrders = dataBase.getAwaitingBuyOrders().getCollection();
        List<Order> sellOrders =
                dataBase.getAwaitingSellOrders().getCollection();

        // if the arrived Order is a 'Buy' Order:
        if (arrivedOrder.getOrderDirection() == OrderDirection.BUY) {
            checkForOppositeAlreadyPlacedOrders(stock, sellOrders,
                    arrivedOrder);

            // if the arrived Order is a 'Sell' Order:
        } else if (arrivedOrder.getOrderDirection() == OrderDirection.SELL) {
            checkForOppositeAlreadyPlacedOrders(stock, buyOrders, arrivedOrder);
        }

    }

    private static void checkForOppositeAlreadyPlacedOrders(Stock stock,
                                                            List<Order> oppositeAlreadyPlacedOrders,
                                                            Order arrivedOrder) {

        /*
         * search the 'opposite already placed' Orders of this Stock
         * (by descending desiredLimitPrice/timeStamp):
         */
        for (Iterator<Order> it = oppositeAlreadyPlacedOrders.iterator();
             it.hasNext(); ) {
            Order oppositeAlreadyPlacedOrder = it.next();

            /*
             * the 'arrivedOrder' is a 'Sell' Order.
             * compare orders: if 'buy' >= 'sell':
             */
            if (checkForOppositeBuyAlreadyPlacedOrders(stock, arrivedOrder, it,
                    oppositeAlreadyPlacedOrder)) {}

            /*
             * the 'arrivedOrder' is a 'Buy' Order.
             * compare orders: if 'buy' >= 'sell':
             */
            else if (checkForOppositeSellAlreadyPlacedOrders(stock,
                    arrivedOrder, it, oppositeAlreadyPlacedOrder)) {}

            /*
             * we found that there are no matching 'opposite already placed' Orders,
             * so we do not make a Transaction,
             * and the 'arrived' Order stays as it was in the data-base.
             */
        }
    }

    private static boolean checkForOppositeBuyAlreadyPlacedOrders(Stock stock,
                                                                  Order arrivedOrder,
                                                                  Iterator<Order> it,
                                                                  Order oppositeAlreadyPlacedOrder) {

        /*
         * the 'arrivedOrder' is a 'Sell' Order.
         * compare orders: if 'buy' >= 'sell':
         */
        if ((oppositeAlreadyPlacedOrder.getOrderDirection() ==
                OrderDirection.BUY) &&
                (oppositeAlreadyPlacedOrder.getDesiredLimitPrice() >=
                        arrivedOrder.getDesiredLimitPrice())) {

            // only if the 'arrivedOrder' wasn't removed from the data-base yet:
            checkForOppositeAlreadyPlacedOrders_DependencyOnDirection(stock,
                    arrivedOrder, it, oppositeAlreadyPlacedOrder,
                    stock.getDataBase().getAwaitingSellOrders()
                            .getCollection());
            return true;
        } else {return false;}
    }

    private static boolean checkForOppositeSellAlreadyPlacedOrders(Stock stock,
                                                                   Order arrivedOrder,
                                                                   Iterator<Order> it,
                                                                   Order oppositeAlreadyPlacedOrder) {

        /*
         * the 'arrivedOrder' is a 'Buy' Order.
         * compare orders: if 'buy' >= 'sell':
         */
        if ((oppositeAlreadyPlacedOrder.getOrderDirection() ==
                OrderDirection.SELL) &&
                (oppositeAlreadyPlacedOrder.getDesiredLimitPrice() <=
                        arrivedOrder.getDesiredLimitPrice())) {

            // only if the 'arrivedOrder' wasn't removed from the data-base yet:
            checkForOppositeAlreadyPlacedOrders_DependencyOnDirection(stock,
                    arrivedOrder, it, oppositeAlreadyPlacedOrder,
                    stock.getDataBase().getAwaitingBuyOrders().getCollection());
            return true;
        } else { return false; }
    }

    private static void checkForOppositeAlreadyPlacedOrders_DependencyOnDirection(
            Stock stock, Order arrivedOrder, Iterator<Order> it,
            Order oppositeAlreadyPlacedOrder, List<Order> OrderList) {

        // only if the 'arrivedOrder' wasn't removed from the data-base yet:
        if (OrderList.contains(arrivedOrder)) {
            makeTransactionAndCheckRemainders(stock, it, arrivedOrder,
                    oppositeAlreadyPlacedOrder);
        }
    }

    private static void makeTransactionAndCheckRemainders(Stock stock,
                                                          Iterator<Order> it,
                                                          Order arrivedOrder,
                                                          Order oppositeAlreadyPlacedOrder) {

        Transaction transaction = makeATransaction(stock, arrivedOrder,
                oppositeAlreadyPlacedOrder);

        // check if there are remainders:
        checkRemainders(stock, it, arrivedOrder, oppositeAlreadyPlacedOrder,
                transaction);
    }

    private static Transaction makeATransaction(Stock stock, Order arrivedOrder,
                                                Order oppositeAlreadyPlacedOrder) {

        /*
         * make a Transaction:
         * its timeStamp is the arrivedOrder's timeStamp.
         * its quantity is the minimum Quantity between the two Orders.
         * its desiredLimitPrice is the 'opposite already placed' Order.
         */

        // calculate the Transaction's Quantity:
        long quantityOfTransaction = Math.min(arrivedOrder.getQuantity(),
                oppositeAlreadyPlacedOrder.getQuantity());

        // create Transaction:
        Transaction transaction =
                new Transaction(stock, arrivedOrder.getTimeStamp(),
                        quantityOfTransaction,
                        oppositeAlreadyPlacedOrder.getDesiredLimitPrice());

        // add Transaction:
        stock.getDataBase().getSuccessfullyFinishedTransactions()
                .getCollection().addFirst(transaction);
        MessagePrint.println(MessagePrint.Stream.OUT,
                Message.Out.StockDataBase.newSuccessAdd(transaction));

        return transaction;
    }

    private static void checkRemainders(Stock stock, Iterator<Order> it,
                                        Order arrivedOrder,
                                        Order oppositeAlreadyPlacedOrder,
                                        Transaction transaction) {

        // check if there is a remainder in the 'opposite already placed' Order:
        checkOppositeAlreadyPlacedOrderRemainder(it, oppositeAlreadyPlacedOrder,
                transaction);

        // check if there is a remainder in the arrivedOrder:
        checkArrivedOrderRemainder(stock, arrivedOrder, transaction);
    }

    private static void checkOppositeAlreadyPlacedOrderRemainder(
            Iterator<Order> it, Order oppositeAlreadyPlacedOrder,
            Transaction transaction) {

        // check if there is a remainder in the 'opposite already placed' Order:
        long alreadyRemainderQuantity =
                oppositeAlreadyPlacedOrder.getQuantity() -
                        transaction.getQuantity();
        if (alreadyRemainderQuantity > 0) {

            /*
             * there is a remainder in the oppositeAlreadyPlacedOrder,
             * set the Quantity of it to the updated 'alreadyRemainderQuantity':
             */
            oppositeAlreadyPlacedOrder.setQuantity(alreadyRemainderQuantity);
        } else {

            /*
             * if the 'opposite already placed' Order's quantity remainder is no more than 0,
             * remove the 'opposite already placed' Order from data-base:
             */
            it.remove();
        }
    }

    private static void checkArrivedOrderRemainder(Stock stock,
                                                   Order arrivedOrder,
                                                   Transaction transaction) {

        // check if there is a remainder in the arrivedOrder:
        long arrivedRemainderQuantity =
                arrivedOrder.getQuantity() - transaction.getQuantity();
        if (arrivedRemainderQuantity > 0) {

            /*
             * there is a remainder in the 'arrivedOrder',
             * set the Quantity of the 'arrivedOrder'
             * to the updated 'arrivedRemainderQuantity':
             */
            arrivedOrder.setQuantity(arrivedRemainderQuantity);

            /*
             * if the 'arrivedOrder' Type is 'MKT', set the remainder of
             * this arrivedOrder's 'desiredLimitPrice' to be calculated again:
             */
            if (arrivedOrder.getOrderType() == OrderType.MKT) {
                arrivedOrder.setDesiredLimitPrice(
                        calcDesiredLimitPriceOfMKTOrder(stock,
                                arrivedOrder.getOrderDirection()));
            }
            MessagePrint.println(MessagePrint.Stream.OUT,
                    "The Order has a remainder:\n\t" + arrivedOrder);
        } else {

            /*
             * if the 'arrived' Order's quantity remainder is no more than 0,
             * remove the 'arrived' Order from data-base:
             */
            checkArrivedOrderRemainder_RemoveArrivedOrder(stock.getDataBase(),
                    arrivedOrder);
        }
    }

    private static void checkArrivedOrderRemainder_RemoveArrivedOrder(
            StockDataBase dataBase, Order arrivedOrder) {

        /*
         * if the 'arrived' Order's quantity remainder is no more than 0,
         * remove the 'arrived' Order from data-base:
         */
        if (arrivedOrder.getOrderDirection() == OrderDirection.BUY) {
            if (dataBase.getAwaitingBuyOrders().getCollection()
                    .remove(arrivedOrder)) {
                MessagePrint.println(MessagePrint.Stream.OUT,
                        Message.Out.StockDataBase
                                .printOrderPerformedInItsEntirety());
            } else {
                MessagePrint.println(MessagePrint.Stream.ERR,
                        new BuildError().getMessage() +
                                Message.Err.Order.removeFail());
            }
        } else if (arrivedOrder.getOrderDirection() == OrderDirection.SELL) {

            if (dataBase.getAwaitingSellOrders().getCollection()
                    .remove(arrivedOrder)) {
                MessagePrint.println(MessagePrint.Stream.OUT,
                        Message.Out.StockDataBase
                                .printOrderPerformedInItsEntirety());
            } else {
                MessagePrint.println(MessagePrint.Stream.ERR,
                        new BuildError().getMessage() +
                                Message.Err.Order.removeFail());
            }
        }
    }

    /**
     * <p>This method is calculating the <i>{@code desiredLimitPrice}</i> for
     * the current {@code MKT} {@link Order}, based on its {@link
     * OrderDirection}.</p>
     *
     * <p>The method searches if there are <i>opposite {@code Direction}
     * already placed</i> {@link Order}s in the given {@link Stock}'s {@code
     * data-base}.</p>
     *
     * <p>If there are such <i>opposite already placed</i> {@link Order}s, the
     * method calculates the
     * <i>{@code desiredLimitPrice}</i> of the {@code MKT} {@link Order} to be
     * as the <tt>first</tt> <i>opposite already placed</i> {@link Order}'s
     * <i>{@code desiredLimitPrice}</i>.</p>
     *
     * <p>If there are <tt>no</tt> such <i>opposite already placed</i> {@link
     * Order}s, the method calculates the
     * <i>{@code desiredLimitPrice}</i> of the {@code MKT} {@link Order} to be
     * as the <i>current</i> <i>{@code price}</i> of the given {@link
     * Stock}.</p>
     *
     * @param stock          the current {@link Stock} to deal with.
     * @param orderDirection the current {@code MKT} {@link Order}'s {@code
     *                       Direction}.
     * @return the calculated <i>{@code desiredLimitPrice}</i> of the current
     * {@code MKT} {@link Order}.
     */
    public static long calcDesiredLimitPriceOfMKTOrder(Stock stock,
                                                       OrderDirection orderDirection) {
        long desiredLimitPrice;
        if ((orderDirection == OrderDirection.BUY) &&
                (stock.getDataBase().getAwaitingSellOrders().getCollection()
                        .size() > 0)) {
            desiredLimitPrice =
                    stock.getDataBase().getAwaitingSellOrders().getCollection()
                            .getFirst().getDesiredLimitPrice();
        } else if ((orderDirection == OrderDirection.SELL) &&
                (stock.getDataBase().getAwaitingBuyOrders().getCollection()
                        .size() > 0)) {
            desiredLimitPrice =
                    stock.getDataBase().getAwaitingBuyOrders().getCollection()
                            .getFirst().getDesiredLimitPrice();
        } else {
            desiredLimitPrice = stock.getPrice();
        }

        return desiredLimitPrice;
    }
}
