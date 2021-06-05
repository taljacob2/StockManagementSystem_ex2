package application.pane.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import order.Order;
import transaction.Transaction;

/**
 * This class bundles some {@code static} methods for handling
 * <i>dynamic</i> <tt>JavaFX</tt> {@link TableColumn}s.
 */
public class TableUtils {

    public static void setDynamicColumn(TableColumn tableColumn) {
        tableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override public ObservableValue call(
                            TableColumn.CellDataFeatures p) {
                        return new ReadOnlyObjectWrapper(p.getValue());
                    }
                });

    }


    /**
     * Used for printing <i>dynamic</i> {@link Order#getRequestingUser()}'s
     * {@code Symbol}.
     *
     * @param tableColumn a {@link TableColumn} of {@link order.Order}s.
     */
    public static void initOrderRequestingUserColumn(TableColumn tableColumn) {
        tableColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override public TableCell call(TableColumn p) {
                return new TableCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if ((this.getTableRow() != null) && (item != null)) {

                            int currentRowIndex = this.getTableRow().getIndex();

                            Order currentRowOrder =
                                    (Order) this.getTableView().getItems()
                                            .get(currentRowIndex);

                            setText(currentRowOrder.getRequestingUser()
                                    .getName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }

    /**
     * Used for printing <i>dynamic</i> {@link Order#getRequestingUser()}'s
     * {@code Symbol}.
     *
     * @param tableColumn a {@link TableColumn} of {@link transaction.Transaction}s.
     */
    public static void initTransactionBuyingUserColumn(
            TableColumn tableColumn) {
        tableColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override public TableCell call(TableColumn p) {
                return new TableCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if ((this.getTableRow() != null) && (item != null)) {

                            int currentRowIndex = this.getTableRow().getIndex();

                            Transaction currentRowTransaction =
                                    (Transaction) this.getTableView().getItems()
                                            .get(currentRowIndex);

                            setText(currentRowTransaction.getBuyingUser()
                                    .getName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }

    /**
     * Used for printing <i>dynamic</i> {@link Order#getRequestingUser()}'s
     * {@code Symbol}.
     *
     * @param tableColumn a {@link TableColumn} of {@link transaction.Transaction}s.
     */
    public static void initTransactionSellingUserColumn(
            TableColumn tableColumn) {
        tableColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override public TableCell call(TableColumn p) {
                return new TableCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if ((this.getTableRow() != null) && (item != null)) {

                            int currentRowIndex = this.getTableRow().getIndex();

                            Transaction currentRowTransaction =
                                    (Transaction) this.getTableView().getItems()
                                            .get(currentRowIndex);

                            setText(currentRowTransaction.getSellingUser()
                                    .getName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }

}
