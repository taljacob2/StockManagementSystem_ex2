package application.pane.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

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
}
