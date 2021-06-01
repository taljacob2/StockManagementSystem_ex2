package main;

import engine.Engine;
import load.LoadSaveXML;
import message.Message;
import message.print.MessagePrint;
import order.Order;
import order.OrderDirection;
import order.OrderType;
import stock.Stock;
import stock.Stocks;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This {@code class} defines the Menu's User-Interface.
 *
 * @version 1.2
 */
public class MenuUI {

    /**
     * A {@link Scanner} to the whole program.
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * A static field initialized with {@code false} value.
     * <p>Used for the indication of when to exit the MenuUI.</p>
     */
    private static boolean exit = false;

    /**
     * Print UI menu.
     */
    private static void printMenu() {

        // Print border:
        MessagePrint.println(MessagePrint.Stream.OUT,
                "-------------------------------------------------------------");

        // Print User Instruction:
        MessagePrint.println(MessagePrint.Stream.OUT,
                "Please choose your command: " + "(" +
                        "enter a number between " + Keys.EXIT + " and " +
                        Keys.LOAD_SAVED_XML_FILE + "): " + "\n");

        // Print Menu Key Commands:
        MessagePrint.println(MessagePrint.Stream.OUT,
                Keys.LOAD_XML_FILE + ". " + "Load a '.xml' file.");
        MessagePrint.println(MessagePrint.Stream.OUT,
                Keys.PRINT_STOCKS + ". " + "Print stocks.");
        MessagePrint.println(MessagePrint.Stream.OUT,
                Keys.PRINT_INFO_ABOUT_A_STOCK + ". " +
                        "Print information about a stock.");
        MessagePrint.println(MessagePrint.Stream.OUT,
                Keys.EXECUTE_TRANSACTION_ORDER + ". " +
                        "Execute a transaction order.");
        MessagePrint.println(MessagePrint.Stream.OUT,
                Keys.PRINT_LISTS_OF_ALL_ORDERS_AND_TRANSACTIONS + ". " +
                        "Print lists of all orders and transactions.");
        MessagePrint.println(MessagePrint.Stream.OUT,
                Keys.SAVE_XML_FILE + ". " + "Save to a '.xml' file.");
        MessagePrint.println(MessagePrint.Stream.OUT,
                Keys.LOAD_SAVED_XML_FILE + ". " + "Load a saved '.xml' file.");
        MessagePrint.println(MessagePrint.Stream.OUT,
                Keys.EXIT + ". " + "Exit program.");
    }

    /**
     * this method determines what command to invoke according to the user's
     * number input.
     *
     * <p><b>Note:</b> the {@link Keys#LOAD_SAVED_XML_FILE} command is the same
     * as the {@link Keys#LOAD_XML_FILE} command.</p>
     */
    private static void commandViaInput() {
        try {

            // get input
            byte input = scanner.nextByte();

            if (input == Keys.LOAD_XML_FILE) {
                command_LOAD_XML_FILE();
            } else if (input == Keys.PRINT_STOCKS) {
                command_PRINT_STOCKS();
            } else if (input == Keys.PRINT_INFO_ABOUT_A_STOCK) {
                command_INFO_ABOUT_A_STOCK();
            } else if (input == Keys.EXECUTE_TRANSACTION_ORDER) {
                command_EXECUTE_TRANSACTION_ORDER();
            } else if (input ==
                    Keys.PRINT_LISTS_OF_ALL_ORDERS_AND_TRANSACTIONS) {
                command_PRINT_LISTS_OF_ALL_ORDERS_AND_TRANSACTIONS();
            } else if (input == Keys.SAVE_XML_FILE) {

                // BONUS!
                command_SAVE_XML_FILE();
            } else if (input == Keys.LOAD_SAVED_XML_FILE) {

                // BONUS!
                // Note: Same method as the 'LOAD_XML_FILE' Key:
                command_LOAD_XML_FILE();
            } else if (input == Keys.EXIT) {
                command_EXIT();
            }
        } catch (InputMismatchException e) {
            command_InputMismatchException_Handle();
        }
    }

    public static void command_LOAD_XML_FILE() {
        scanner = new Scanner(System.in); // reset scanner.

        MessagePrint.println(MessagePrint.Stream.OUT,
                Message.Out.Input.please("[String]", "path"));
        String input = scanner.nextLine().trim();

        try {
            LoadSaveXML.unmarshal(input); // unmarshal from file
        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }
    }

    public static void command_LOAD_XML_FILE(String absolutePathOfFile) {
        try {
            LoadSaveXML.unmarshal(absolutePathOfFile); // unmarshal from file
        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }
    }

    public static void command_PRINT_STOCKS() {
        try {

            // print stocks:
            MessagePrint.println(MessagePrint.Stream.OUT,
                    Engine.getStocks().toString());
        } catch (IOException e) {

            /*
             * stocks haven't been initialized.
             * print an Error message:
             */
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }
    }

    public static void command_INFO_ABOUT_A_STOCK() {

        // first of all check if there are Stocks available in the system:
        if (Engine.isStocks()) {

            MessagePrint.println(MessagePrint.Stream.OUT, Message.Out.Input
                    .please("[String]", "Symbol",
                            "of the stock you wish to see"));

            // get the Symbol:
            String input = scanner.next();

            try {

                // get the desired stock which has this Symbol:
                Stock stock = Engine.getStockBySymbol(input);

                // print the desired stock:
                MessagePrint.println(MessagePrint.Stream.OUT, stock.toString());
                MessagePrint.println(MessagePrint.Stream.OUT,
                        stock.getTransactionsToString("\t", "\t\t\t"));
            } catch (IOException e) {
                MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            }
        }
    }

    public static void command_EXECUTE_TRANSACTION_ORDER() {

        // first of all check if there are Stocks available in the system:
        if (Engine.isStocks()) {

            // get Parameters of the Order, and insert the new Order to Database:

            try {
                // //TODO check print of all Users:
                // MessagePrint.println(MessagePrint.Stream.OUT,
                //         Engine.getUsers().toString());

                // insert the Symbol, and get the matching stock to it:
                Stock stock = inputOrderSymbolAndGetMatchingStock();

                // get the orderDirection:
                OrderDirection orderDirection = getOrderDirection();

                // get the orderType:
                OrderType orderType = getOrderType();

                // get the Quantity:
                Long quantity = getOrderQuantity();

                // initialize 'desiredLimitPrice' to 0.
                Long desiredLimitPrice = 0L;

                // get the desiredLimitPrice (only if the orderType is 'LMT'):
                if (orderType == OrderType.LMT) {
                    desiredLimitPrice = getOrderDesiredLimit();
                } else if (orderType == OrderType.MKT) {

                    // set the 'desiredLimitPrice':
                    desiredLimitPrice =
                            Engine.calcDesiredLimitPriceOfMKTOrder(stock,
                                    orderDirection);
                }

                // create the instance of the Order and insert it to DataBase:
                Order order =
                        insertOrder(stock, orderDirection, orderType, quantity,
                                desiredLimitPrice);

                // calc this newly placed order with the matching already placed Orders:
                Engine.calcOrdersOfASingleStock(stock, order);

            } catch (IOException e) {
                MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            }

        }
    }

    public static void command_EXECUTE_TRANSACTION_ORDER(Stock stock,
                                                         OrderDirection orderDirection,
                                                         OrderType orderType,
                                                         Long quantity,
                                                         Long insertedDesiredLimitPrice) {

        // first of all check if there are Stocks available in the system:
        if (Engine.isStocks()) {

            // get Parameters of the Order, and insert the new Order to Database:

            try {

                // initialize 'desiredLimitPrice' to 0.
                Long desiredLimitPrice = 0L;

                // get the desiredLimitPrice (only if the orderType is 'LMT'):
                if (orderType == OrderType.LMT) {
                    desiredLimitPrice = insertedDesiredLimitPrice;
                } else if (orderType == OrderType.MKT) {

                    // set the 'desiredLimitPrice':
                    desiredLimitPrice =
                            Engine.calcDesiredLimitPriceOfMKTOrder(stock,
                                    orderDirection);
                }

                // create the instance of the Order and insert it to DataBase:
                Order order =
                        insertOrder(stock, orderDirection, orderType, quantity,
                                desiredLimitPrice);

                // calc this newly placed order with the matching already placed Orders:
                Engine.calcOrdersOfASingleStock(stock, order);

            } catch (IOException e) {
                MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            }

        }
    }

    /**
     * This method gets a {@link String} {@code Stock-Symbol} from the user.
     *
     * @return {@link Stock} with the matching {@code Symbol} (if exist).
     * @throws IOException in case of an incorrect input - means that there is
     *                     no such {@code Symbol} available in the {@link
     *                     Engine}'s {@code Stocks}.
     */
    private static Stock inputOrderSymbolAndGetMatchingStock()
            throws IOException {

        // get the Symbol:
        MessagePrint.println(MessagePrint.Stream.OUT, Message.Out.Input
                .please("[String]", "Symbol", "of the stock in the order"));

        String symbol = scanner.next();

        try {

            // get the desired stock which has this Symbol:
            return Engine.getStockBySymbol(symbol);
        } catch (IOException e) {
            scanner = new Scanner(System.in); // reset scanner.
            throw e;
        }

    }

    /**
     * This method gets a {@link OrderDirection} from the user.
     *
     * @return {@link OrderDirection}.
     * @throws IOException in case of an incorrect input.
     */
    private static OrderDirection getOrderDirection() throws IOException {

        // get the orderDirection:
        MessagePrint.println(MessagePrint.Stream.OUT, Message.Out.Input
                .please("[String] Order-direction", ": 'Buy' or 'Sell'"));

        String stringInput = scanner.next();

        if (stringInput.equalsIgnoreCase("Buy") ||
                stringInput.equalsIgnoreCase("Sell")) {
            return OrderDirection.valueOf(stringInput.toUpperCase());
        } else {
            scanner = new Scanner(System.in); // reset scanner.

            throw new IOException(Message.Err.Input
                    .mismatch("[String] 'Buy' or 'Sell'",
                            "different [String]"));
        }
    }

    /**
     * This method gets a {@link OrderType} from the user.
     *
     * @return {@link OrderType}.
     * @throws IOException in case of an incorrect input.
     */
    private static OrderType getOrderType() throws IOException {

        // get the orderType:
        MessagePrint.println(MessagePrint.Stream.OUT, Message.Out.Input
                .please("[String] Order-type", ": 'LMT' or 'MKT'"));

        String stringInput = scanner.next();

        if (stringInput.equalsIgnoreCase("LMT") ||
                stringInput.equalsIgnoreCase("MKT")) {
            return OrderType.valueOf(stringInput.toUpperCase());
        } else {
            scanner = new Scanner(System.in); // reset scanner.

            throw new IOException(Message.Err.Input
                    .mismatch("[String] 'LMT' or 'MKT'", "different [String]"));
        }
    }

    /**
     * This method gets a {@link Long} {@code Order-Quantity} from the user.
     *
     * @return {@link Long} {@code Order-Quantity}.
     * @throws IOException in case of an incorrect input.
     */
    private static Long getOrderQuantity() throws IOException {
        MessagePrint.println(MessagePrint.Stream.OUT, Message.Out.Input
                .please("[long]", "Quantity",
                        "of the stock in the order (a number greater than 0)"));

        try {
            return getPositiveLong();
        } catch (InputMismatchException e) {
            scanner = new Scanner(System.in);     // reset scanner.
            throw new IOException(
                    Message.Err.Input.mismatch("[long]", "[String]"));
        }

    }

    /**
     * This method gets a {@link Long} {@code Order-Desired-Price-Limit} from
     * the user.
     *
     * @return {@link Long} {@code Order-Desired-Price-Limit}.
     * @throws IOException in case of an incorrect input.
     */
    private static Long getOrderDesiredLimit() throws IOException {
        MessagePrint.println(MessagePrint.Stream.OUT, Message.Out.Input
                .please("[long]", "Desired-price-limit",
                        "of the stock in the order (a non-negative number)"));

        try {
            return getNonNegativeLong();
        } catch (InputMismatchException e) {
            scanner = new Scanner(System.in); // reset scanner.
            throw new IOException(
                    Message.Err.Input.mismatch("[long]", "[String]"));
        }

    }

    /**
     * This method gets a long <tt>input</tt> from the user, and checks if it is
     * a positive number.
     *
     * @return a positive {@code long} number.
     * @throws IOException if the <tt>input</tt> provided was not a positive
     *                     number.
     */
    private static long getPositiveLong() throws IOException {
        long input = scanner.nextLong();
        if (input <= 0) {

            // input is invalid:
            scanner = new Scanner(System.in); // reset scanner.
            throw new IOException(Message.Err.Input
                    .mismatch("number greater than 0", "negative number or 0"));

        } else {

            // input is valid:
            return input;
        }
    }

    /**
     * This method gets a long <tt>input</tt> from the user, and checks if it is
     * a non-negative number.
     *
     * @return a non-negative {@code long} number.
     * @throws IOException if the <tt>input</tt> provided was not a non-negative
     *                     number.
     */
    private static long getNonNegativeLong() throws IOException {
        long input = scanner.nextLong();
        if (input < 0) {

            // input is invalid:
            scanner = new Scanner(System.in); // reset scanner.
            throw new IOException(Message.Err.Input
                    .mismatch("non-negative number", "negative number"));

        } else {

            // input is valid:
            return input;
        }
    }

    /**
     * This method creates an {@link Order} with the given parameters.
     *
     * @param stock             the {@link Stock} that matches the {@code
     *                          Symbol} given.
     * @param quantity          the {@code Quantity} of the stocks.
     * @param orderDirection    whether the order is to Buy or Sell.
     * @param orderType         one of the {@code Order-Types}:
     *                          <ul>
     *                          <li>{@link OrderType#LMT}.</li>
     *                          <li>{@link OrderType#MKT}.</li>
     *                          <li>{@link OrderType#FOX}.</li>
     *                          <li>{@link OrderType#IOC}.</li>
     *                          </ul>
     * @param desiredLimitPrice takes action only for {@link OrderType#LMT}
     *                          {@code OrderTypes}.
     * @return the newly placed order.
     * @throws IOException if the {@link Order} build process failed.
     */
    private static Order insertOrder(Stock stock, OrderDirection orderDirection,
                                     OrderType orderType, long quantity,
                                     long desiredLimitPrice)
            throws IOException {

        // create the instance of the Order:
        try {
            Order order = new Order(orderDirection, orderType, quantity,
                    desiredLimitPrice);

            // print success message:
            MessagePrint.println(MessagePrint.Stream.OUT,
                    Message.Out.StockDataBase.newSuccessAdd(order));

            /*
             * add the order to the according 'awaiting orders' Collection
             * in the stock's data-base:
             */
            if (orderDirection == OrderDirection.BUY) {
                stock.getDataBase().getAwaitingBuyOrders().getCollection()
                        .sortedAdd(order);
            } else if (orderDirection == OrderDirection.SELL) {
                stock.getDataBase().getAwaitingSellOrders().getCollection()
                        .sortedAdd(order);
            }
            return order;
        } catch (NullPointerException e) {

            // there is no need to reset scanner here.
            throw new IOException(Message.Err.Order.buildFail());
        }
    }

    public static void command_PRINT_LISTS_OF_ALL_ORDERS_AND_TRANSACTIONS() {
        try {
            Stocks stocks = Engine.getStocks();
            for (Stock i : stocks.getCollection()) {
                MessagePrint
                        .println(MessagePrint.Stream.OUT, i.toString() + ":");

                MessagePrint.println(MessagePrint.Stream.OUT,
                        i.getAwaitingBuyOrdersToString("\t", "\t\t\t"));
                MessagePrint.println(MessagePrint.Stream.OUT,
                        i.getAwaitingSellOrdersToString("\t", "\t\t\t"));
                MessagePrint.println(MessagePrint.Stream.OUT,
                        i.getTransactionsToString("\t", "\t\t\t"));

                // add an extra new-line:
                MessagePrint.println(MessagePrint.Stream.OUT, "\n");

            }

        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }
    }

    public static void command_SAVE_XML_FILE() {

        // if there are Stocks in the system
        if (Engine.isStocks()) {

            scanner = new Scanner(System.in); // reset scanner.

            MessagePrint.println(MessagePrint.Stream.OUT,
                    Message.Out.Input.please("String", "path"));
            String input = scanner.nextLine().trim();

            try {
                LoadSaveXML.marshal(input);   // marshal to file
            } catch (IOException e) {
                MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            }
        }
    }

    public static void command_SAVE_XML_FILE(String absolutePathOfFile) {

        try {
            LoadSaveXML.marshal(absolutePathOfFile);   // marshal to file
        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }

    }

    public static void command_EXIT() {
        exit = true;
        MessagePrint.println(MessagePrint.Stream.OUT, "Exited successfully.");
    }

    private static void command_InputMismatchException_Handle() {

        // print an error message:
        MessagePrint.println(MessagePrint.Stream.ERR,
                Message.Err.Input.mismatch("[Byte]", "[String]"));

        // reset the scanner:
        scanner = new Scanner(System.in);

        // recall the function:
        commandViaInput();

        // e.getStackTrace();
    }

    /**
     * Runs the MenuUI:
     * <ul>
     *     <li>prints MenuUI.</li>
     *     <li>gets an input from the user and executes a command.</li>
     * </ul>
     */
    public static void run() {
        while (!exit) {
            printMenu();
            commandViaInput();
        }
    }

    /**
     * Inner-class for all final Keys of the Menu UI.
     */
    public static class Keys {
        public static byte LOAD_XML_FILE = 1;
        public static byte PRINT_STOCKS = 2;
        public static byte PRINT_INFO_ABOUT_A_STOCK = 3;
        public static byte EXECUTE_TRANSACTION_ORDER = 4;
        public static byte PRINT_LISTS_OF_ALL_ORDERS_AND_TRANSACTIONS = 5;
        public static byte SAVE_XML_FILE = 6;       // BONUS!
        public static byte LOAD_SAVED_XML_FILE = 7; // BONUS!
        public static byte EXIT = 0;
    }

}
