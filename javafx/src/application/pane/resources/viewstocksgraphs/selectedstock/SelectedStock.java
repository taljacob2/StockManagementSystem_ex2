package application.pane.resources.viewstocksgraphs.selectedstock;

import javafx.beans.property.SimpleObjectProperty;
import stock.Stock;

/**
 * This {@code Class} saves the {@link Stock} that was selected in the
 * <i>View Stocks Graphs page</i> in a {@link SimpleObjectProperty}, in order
 * to transfer it to the <i>View-Graph page</i>.
 */
public class SelectedStock {

    private static SimpleObjectProperty<Stock> selectedStock;

    public static Stock getSelectedUser() {
        return selectedStock.get();
    }

    public static void setSelectedUser(Stock selectedUser) {
        SelectedStock.selectedStock.set(selectedUser);
    }

    public static SimpleObjectProperty<Stock> selectedUserProperty() {

        // similar to Singleton getInstance():
        if (selectedStock == null) {
            selectedStock = new SimpleObjectProperty();
        }
        return selectedStock;
    }
}

