package application.pane.resources.afterexecution;

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
public class OrderTableAndTransactionTable implements Initializable {

    /**
     * {@link ObservableList} of all {@link Order}s remained after
     * <i>order-execution.</i>
     */
    private ObservableList<Order> orderObservableList;

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
     * {@link ObservableList} of all {@link Transaction}s made after
     * <i>order-execution.</i>
     */
    private ObservableList<Transaction> transactionObservableList;

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
     * Constructor. try to get the {@link stock.Stocks} in the {@link Engine}.
     */
    public OrderTableAndTransactionTable() {
        orderObservableList = FXCollections.observableArrayList(
                Engine.getAfterExecuteOrderAndTransactionContainer()
                        .getRemainderOrders());

        transactionObservableList = FXCollections.observableArrayList(
                Engine.getAfterExecuteOrderAndTransactionContainer()
                        .getTransactions());
    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        // orderTableView: initialize columns:
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

        // set the 'tableView' to the columns provided:
        orderTableView.setItems(orderObservableList);


        // transactionTableView: initialize columns:
        transactionTimeStampColumn.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("timeStamp"));
        transactionQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<Transaction, Long>("quantity"));
        transactionPriceColumn.setCellValueFactory(
                new PropertyValueFactory<Transaction, Long>("price"));

        // set the 'tableView' to the columns provided:
        transactionTableView.setItems(transactionObservableList);
    }

}