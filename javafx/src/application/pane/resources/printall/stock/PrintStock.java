package application.pane.resources.printall.stock;

import application.pane.resources.printall.stock.container.PrintStockContainer;
import application.pane.table.TableUtils;
import engine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import message.print.MessagePrint;
import order.Order;
import stock.Stock;
import transaction.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p><i>Presents</i> the {@link TableView} of all the {@link Order}s
 * and {@link Transaction}s that were made <i>with a specific {@link
 * stock.Stock}</i ></p>
 */
public class PrintStock implements Initializable {

    /**
     * Store here the {@code current} {@link Stock} that is being iterated by
     * {@link application.pane.resources.printall.PrintAll}, via {@code getting}
     * the correct {@code current} <i>index</i> from the {@link
     * PrintStockContainer#getIndex()}.
     */
    private Stock thisStock;


    /**
     * {@link ObservableList} of all {@link Transaction}s made in a {@link
     * stock.Stock}.
     */
    private ObservableList<Transaction> transactionsMadeObservableList;

    /**
     * The {@link TableView} needed to be shown.
     */
    @FXML private TableView<Transaction> transactionTableView;

    /**
     * A column in the {@link TableView} of {@link Transaction}s.s.
     */
    @FXML private TableColumn<Transaction, String> transactionTimeStampColumn;

    /**
     * A column in the {@link TableView} of {@link Transaction}s.s.
     */
    @FXML private TableColumn<Transaction, Long> transactionQuantityColumn;

    /**
     * A column in the {@link TableView} of {@link Transaction}s.s.
     */
    @FXML private TableColumn transactionPriceColumn;

    /**
     * A <i>dynamic</i> column in the {@link TableView}.
     */
    @FXML private TableColumn transactionBuyingUserColumn;

    /**
     * A <i>dynamic</i> column in the {@link TableView}.
     */
    @FXML private TableColumn transactionSellingUserColumn;

    /**
     * A <i>dynamic</i> column in the {@link TableView}
     */
    @FXML private TableColumn transactionPeriodColumn;


    /**
     * {@link ObservableList} of all <i>buy</i> {@link Order}s of a {@link
     * stock.Stock}.
     */
    private ObservableList<Order> buyOrdersObservableList;

    /**
     * The <i>buy</i> {@link Order}s {@link TableView} needed to be shown.
     */
    @FXML private TableView<Order> buyOrderTableView;

    /**
     * A column in the {@link TableView} of <i>buy</i> {@link Order}s.
     */
    @FXML private TableColumn<Order, String> buyOrderTimeStampColumn;

    /**
     * A column in the {@link TableView} of <i>buy</i> {@link Order}s.
     */
    @FXML private TableColumn<Order, String> buyOrderDirectionColumn;

    /**
     * A column in the {@link TableView} of <i>buy</i> {@link Order}s.
     */
    @FXML private TableColumn<Order, String> buyOrderTypeColumn;

    /**
     * A column in the {@link TableView} of <i>buy</i> {@link Order}s.
     */
    @FXML private TableColumn<Order, Long> buyOrderQuantityColumn;

    /**
     * A column in the {@link TableView} of <i>buy</i> {@link Order}s.
     */
    @FXML private TableColumn<Order, Long> buyOrderDesiredLimitPriceColumn;

    /**
     * A <i>dynamic</i> column in the {@link TableView} of <i>buy</i> {@link
     * Order}s.
     */
    @FXML private TableColumn buyOrderRequestingUserColumn;


    /**
     * {@link ObservableList} of all <i>sell</i> {@link Order}s of a {@link
     * stock.Stock}.
     */
    private ObservableList<Order> sellOrdersObservableList;

    /**
     * The <i>sell</i> {@link Order}s {@link TableView} needed to be shown.
     */
    @FXML private TableView<Order> sellOrderTableView;

    /**
     * A column in the {@link TableView} of <i>sell</i> {@link Order}s.
     */
    @FXML private TableColumn<Order, String> sellOrderTimeStampColumn;

    /**
     * A column in the {@link TableView} of <i>sell</i> {@link Order}s.
     */
    @FXML private TableColumn<Order, String> sellOrderDirectionColumn;

    /**
     * A column in the {@link TableView} of <i>sell</i> {@link Order}s.
     */
    @FXML private TableColumn<Order, String> sellOrderTypeColumn;

    /**
     * A column in the {@link TableView} of <i>sell</i> {@link Order}s.
     */
    @FXML private TableColumn<Order, Long> sellOrderQuantityColumn;

    /**
     * A column in the {@link TableView} of <i>sell</i> {@link Order}s.
     */
    @FXML private TableColumn<Order, Long> sellOrderDesiredLimitPriceColumn;

    /**
     * A <i>dynamic</i> column in the {@link TableView} of <i>sell</i> {@link
     * Order}s.
     */
    @FXML private TableColumn sellOrderRequestingUserColumn;


    public PrintStock() {}

    @Override public void initialize(URL location, ResourceBundle resources) {

        // Init all fields:
        try {
            thisStock = Engine.getStocks().getCollection()
                    .get(PrintStockContainer.getIndex());

            buyOrdersObservableList = FXCollections.observableArrayList(
                    thisStock.getDataBase().getAwaitingBuyOrders()
                            .getCollection());

            sellOrdersObservableList = FXCollections.observableArrayList(
                    thisStock.getDataBase().getAwaitingSellOrders()
                            .getCollection());

            transactionsMadeObservableList = FXCollections.observableArrayList(
                    thisStock.getDataBase()
                            .getSuccessfullyFinishedTransactions()
                            .getCollection());

            initAllTables();
        } catch (IOException e) {

            /*
             * Note: this exception should not happen thanks to the initial
             * check of stocks.
             */
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }
    }

    private void initAllTables() {
        initTransactionsTable();
        initBuyOrdersTable();
        initSellOrdersTable();
    }

    private void initTransactionsTable() {

        // transactionTableView: initialize columns:
        transactionTimeStampColumn.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("timeStamp"));
        transactionQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<Transaction, Long>("quantity"));
        transactionPriceColumn.setCellValueFactory(
                new PropertyValueFactory<Transaction, Long>("price"));

        // initialize dynamic-column:
        TableUtils.setDynamicColumn(transactionBuyingUserColumn);
        TableUtils.initTransactionBuyingUserColumn(transactionBuyingUserColumn);

        // initialize dynamic-column:
        TableUtils.setDynamicColumn(transactionSellingUserColumn);
        TableUtils
                .initTransactionSellingUserColumn(transactionSellingUserColumn);

        // initialize dynamic-column:
        TableUtils.setDynamicColumn(transactionPeriodColumn);
        TableUtils.initTransactionPeriodColumn(transactionPeriodColumn);

        // set the 'tableView' to the columns provided:
        transactionTableView.setItems(transactionsMadeObservableList);
    }

    private void initBuyOrdersTable() {

        // buyOrderTableView: initialize columns:
        buyOrderTimeStampColumn.setCellValueFactory(
                new PropertyValueFactory<Order, String>("timeStamp"));
        buyOrderDirectionColumn.setCellValueFactory(
                new PropertyValueFactory<Order, String>("orderDirection"));
        buyOrderTypeColumn.setCellValueFactory(
                new PropertyValueFactory<Order, String>("orderType"));
        buyOrderQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<Order, Long>("quantity"));
        buyOrderDesiredLimitPriceColumn.setCellValueFactory(
                new PropertyValueFactory<Order, Long>("desiredLimitPrice"));

        // initialize dynamic-column:
        TableUtils.setDynamicColumn(buyOrderRequestingUserColumn);
        TableUtils.initOrderRequestingUserColumn(buyOrderRequestingUserColumn);


        // set the 'tableView' to the columns provided:
        buyOrderTableView.setItems(buyOrdersObservableList);
    }

    private void initSellOrdersTable() {

        // sellOrderTableView: initialize columns:
        sellOrderTimeStampColumn.setCellValueFactory(
                new PropertyValueFactory<Order, String>("timeStamp"));
        sellOrderDirectionColumn.setCellValueFactory(
                new PropertyValueFactory<Order, String>("orderDirection"));
        sellOrderTypeColumn.setCellValueFactory(
                new PropertyValueFactory<Order, String>("orderType"));
        sellOrderQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<Order, Long>("quantity"));
        sellOrderDesiredLimitPriceColumn.setCellValueFactory(
                new PropertyValueFactory<Order, Long>("desiredLimitPrice"));

        // initialize dynamic-column:
        TableUtils.setDynamicColumn(sellOrderRequestingUserColumn);
        TableUtils.initOrderRequestingUserColumn(sellOrderRequestingUserColumn);

        // set the 'tableView' to the columns provided:
        sellOrderTableView.setItems(sellOrdersObservableList);


    }

}
