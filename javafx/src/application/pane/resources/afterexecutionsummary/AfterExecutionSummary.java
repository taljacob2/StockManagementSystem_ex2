package application.pane.resources.afterexecutionsummary;

import application.pane.table.TableUtils;
import engine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import order.Order;
import transaction.Transaction;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p><i>Presents</i> the {@link TableView} of all the {@link order.Order}s
 * and {@link transaction.Transaction}s that were made <i>after an order
 * execution.</i></p>
 */
public class AfterExecutionSummary implements Initializable {

    /**
     * {@link ObservableList} of all {@link Transaction}s made after
     * <i>order-execution.</i>
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
     * A column in the {@link TableView} of {@link Transaction}s.
     */
    @FXML private TableColumn<Transaction, Long> transactionPriceColumn;

    /**
     * A column in the {@link TableView} of {@link Transaction}s.
     */
    @FXML private TableColumn<Transaction, Long> transactionSerialTimeColumn;

    /**
     * A <i>dynamic</i> column in the {@link TableView}.
     */
    @FXML private TableColumn transactionBuyingUserColumn;

    /**
     * A <i>dynamic</i> column in the {@link TableView}.
     */
    @FXML private TableColumn transactionSellingUserColumn;

    /**
     * A <i>dynamic</i> column in the {@link TableView}.
     */
    @FXML private TableColumn transactionPeriodColumn;


    /**
     * {@link ObservableList} of all {@link Order}s remained after
     * <i>order-execution.</i>
     */
    private ObservableList<Order> remainedOrdersObservableList;

    /**
     * The {@link TableView} needed to be shown.
     */
    @FXML private TableView<Order> orderTableView;

    /**
     * A column in the {@link TableView} of {@link order.Order}s.
     */
    @FXML private TableColumn<Order, String> orderTimeStampColumn;

    /**
     * A column in the {@link TableView} of {@link order.Order}s.
     */
    @FXML private TableColumn<Order, String> orderDirectionColumn;

    /**
     * A column in the {@link TableView} of {@link order.Order}s.
     */
    @FXML private TableColumn<Order, String> orderTypeColumn;

    /**
     * A column in the {@link TableView} of {@link order.Order}s.
     */
    @FXML private TableColumn<Order, Long> orderQuantityColumn;

    /**
     * A column in the {@link TableView} of {@link order.Order}s.
     */
    @FXML private TableColumn<Order, Long> orderDesiredLimitPriceColumn;

    /**
     * A column in the {@link TableView} of {@link order.Order}s.
     */
    @FXML private TableColumn<Order, Long> orderSerialTimeColumn;

    /**
     * A <i>dynamic</i> column in the {@link TableView} of {@link Order}s.
     */
    @FXML private TableColumn orderRequestingUserColumn;


    /**
     * Constructor. try to get the {@link stock.Stocks} in the {@link Engine}.
     */
    public AfterExecutionSummary() {
        remainedOrdersObservableList = FXCollections.observableArrayList(
                Engine.getAfterExecuteOrderAndTransactionContainer()
                        .getRemainderOrders());

        transactionsMadeObservableList = FXCollections.observableArrayList(
                Engine.getAfterExecuteOrderAndTransactionContainer()
                        .getTransactions());
    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        // transactionTableView: initialize columns:
        transactionSerialTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Transaction, Long>("serialTime"));
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


        // orderTableView: initialize columns:
        orderSerialTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Order, Long>(
                        "serialTimeOfRemainedOrder"));
        orderTimeStampColumn.setCellValueFactory(
                new PropertyValueFactory<Order, String>("timeStamp"));
        orderDirectionColumn.setCellValueFactory(
                new PropertyValueFactory<Order, String>("orderDirection"));
        orderTypeColumn.setCellValueFactory(
                new PropertyValueFactory<Order, String>("orderType"));
        orderQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<Order, Long>("quantity"));
        orderDesiredLimitPriceColumn.setCellValueFactory(
                new PropertyValueFactory<Order, Long>("desiredLimitPrice"));

        // initialize dynamic-column:
        TableUtils.setDynamicColumn(orderRequestingUserColumn);
        TableUtils.initOrderRequestingUserColumn(orderRequestingUserColumn);


        // set the 'tableView' to the columns provided:
        orderTableView.setItems(remainedOrdersObservableList);
    }

}
