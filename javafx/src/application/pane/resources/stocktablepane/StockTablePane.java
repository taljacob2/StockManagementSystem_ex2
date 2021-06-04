package application.pane.resources.stocktablepane;

import engine.Engine;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import message.print.MessagePrint;
import stock.Stock;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p><i>Controls</i> the {@link TableView} of all the {@link stock.Stocks} in
 * the program.</p>
 */
public class StockTablePane implements Initializable {

    /**
     * {@link ObservableList} of all the stocks in the program:
     */
    private ObservableList<Stock> stockObservableList;

    /**
     * The {@link TableView} needed to be shown.
     */
    @FXML private TableView<Stock> tableView;

    /**
     * A column in the {@link TableView}.
     */
    @FXML private TableColumn<Stock, String> symbolColumn;

    /**
     * A column in the {@link TableView}.
     */
    @FXML private TableColumn<Stock, String> companyNameColumn;

    /**
     * A column in the {@link TableView}.
     */
    @FXML private TableColumn<Stock, Long> priceColumn;

    @FXML private TableColumn numOfTotalTransactionsColumn;

    @FXML private TableColumn totalTransactionsPeriodColumn;


    /**
     * Constructor. try to get the {@link stock.Stocks} in the {@link Engine}.
     */
    public StockTablePane() {

        // if there are any Stocks in the system, get them:
        try {
            stockObservableList = FXCollections
                    .observableArrayList(Engine.getStocks().getCollection());
        } catch (IOException e) {

            // or else, there are no Stocks available: print an error:
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }
    }


    @Override public void initialize(URL location, ResourceBundle resources) {

        // initialize columns:
        symbolColumn.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("symbol"));
        companyNameColumn.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("companyName"));
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<Stock, Long>("price"));


        setDynamicColumns();

        initNumOfTotalTransactionsColumn();

        initTotalTransactionsPeriodColumn();

        // set the 'tableView' to the columns provided:
        tableView.setItems(stockObservableList);
    }

    private void initNumOfTotalTransactionsColumn() {

        /*
         * Note: getItems() is a ObservableList of "Items".
         * Note: "Item" = an Object of a HeightCell in a specific column,
         * ordered from top to bottom.
         */
        numOfTotalTransactionsColumn
                .setCellFactory(new Callback<TableColumn, TableCell>() {
                    @Override public TableCell call(TableColumn p) {
                        return new TableCell() {
                            @Override protected void updateItem(Object item,
                                                                boolean empty) {
                                super.updateItem(item, empty);
                                if ((this.getTableRow() != null) &&
                                        (item != null)) {

                                    int currentRowIndex =
                                            this.getTableRow().getIndex();

                                    Stock currentRowStock =
                                            (Stock) this.getTableView()
                                                    .getItems()
                                                    .get(currentRowIndex);

                                    setText(Integer.toString(
                                            currentRowStock.getDataBase()
                                                    .getSuccessfullyFinishedTransactions()
                                                    .getCollection().size()));
                                } else {
                                    setText("");
                                }
                            }
                        };
                    }
                });
    }

    private void initTotalTransactionsPeriodColumn() {

        /*
         * Note: getItems() is a ObservableList of "Items".
         * Note: "Item" = an Object of a HeightCell in a specific column,
         * ordered from top to bottom.
         */
        totalTransactionsPeriodColumn
                .setCellFactory(new Callback<TableColumn, TableCell>() {
                    @Override public TableCell call(TableColumn p) {
                        return new TableCell() {
                            @Override protected void updateItem(Object item,
                                                                boolean empty) {
                                super.updateItem(item, empty);
                                if ((this.getTableRow() != null) &&
                                        (item != null)) {

                                    int currentRowIndex =
                                            this.getTableRow().getIndex();

                                    Stock currentRowStock =
                                            (Stock) this.getTableView()
                                                    .getItems()
                                                    .get(currentRowIndex);

                                    setText(Long.toString(
                                            (currentRowStock.getDataBase()
                                                    .getTotalPeriod(
                                                            currentRowStock
                                                                    .getDataBase()
                                                                    .getSuccessfullyFinishedTransactions()
                                                                    .getCollection()))));

                                } else {
                                    setText("");
                                }
                            }
                        };
                    }
                });
    }

    private void setDynamicColumns() {
        numOfTotalTransactionsColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override public ObservableValue call(
                            TableColumn.CellDataFeatures p) {
                        return new ReadOnlyObjectWrapper(p.getValue());
                    }
                });

        totalTransactionsPeriodColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override public ObservableValue call(
                            TableColumn.CellDataFeatures p) {
                        return new ReadOnlyObjectWrapper(p.getValue());
                    }
                });
    }


}
