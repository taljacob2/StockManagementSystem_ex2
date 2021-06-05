package application.pane.resources.viewstocksgraphs;

import application.pane.ContainsAnotherPane;
import application.pane.resources.viewstocksgraphs.selectedstock.container.SelectedStockContainer;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import message.print.MessagePrint;
import stock.Stock;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} of the <i>View Stocks
 * Graphs</i> page.</p>
 */
public class ViewStocksGraphs extends ContainsAnotherPane
        implements Initializable {

    @FXML private ComboBox<String> stockSymbolComboBox;

    public ViewStocksGraphs() {

        // Set initial pane:
        super("/application/pane/resources" +
                "/viewstocksgraphs/welcome/StocksGraphsWelcome.fxml");
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        try {

            // Set all Symbols of the stocks in the Engine to the combo-box:
            stockSymbolComboBox.getItems()
                    .addAll(Engine.getStocks().getCollection().stream()
                            .map(Stock::getSymbol)
                            .collect(Collectors.toList()));
        } catch (IOException e) {

            /*
             * Note: this exception should not happen thanks to the initial
             * check of stocks.
             */
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }


        stockSymbolComboBox.valueProperty()
                .addListener((observable, oldValue, newValue) -> {

                    try {
                        if (stockSymbolComboBox.getValue() != null) {
                            Stock selectedStock = Engine.getStockBySymbol(
                                    stockSymbolComboBox.getValue());

                            // Store the selected Stock in 'SelectedStock':
                            SelectedStockContainer
                                    .setSelectedStock(selectedStock);

                            setPane(getBorderPaneToShowTheAnotherInnerPane(),
                                    "/application/pane/resources" +
                                            "/viewstocksgraphs/stockgraph" +
                                            "/StockGraph.fxml");
                        }
                    } catch (IOException e) {

                        /*
                         * Note: this exception should not happen thanks to the initial
                         * check of stocks.
                         */
                        MessagePrint.println(MessagePrint.Stream.ERR,
                                e.getMessage());
                    }

                });
    }

}
