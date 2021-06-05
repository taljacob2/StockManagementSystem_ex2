package application.pane.resources.viewstocksgraphs.selectedstock.container;

import javafx.beans.property.SimpleObjectProperty;
import stock.Stock;

/**
 * This {@code Class} saves the {@link Stock} that was selected in the
 * <i>View Stocks Graphs page</i> in a {@link SimpleObjectProperty}, in order
 * to transfer it to the <i>View-Graph page</i>.
 */
public class SelectedStockContainer {

    private static SimpleObjectProperty<Stock> selectedStock;

    public static Stock getSelectedStock() {
        return selectedStock.get();
    }

    public static void setSelectedStock(Stock selectedUser) {
        SelectedStockContainer.selectedStock.set(selectedUser);
    }

    public static SimpleObjectProperty<Stock> selectedStockProperty() {

        // similar to Singleton getInstance():
        if (selectedStock == null) {
            selectedStock = new SimpleObjectProperty();
        }
        return selectedStock;
    }
}

