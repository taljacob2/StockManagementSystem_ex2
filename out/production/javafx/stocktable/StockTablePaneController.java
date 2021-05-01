import engine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import message.print.MessagePrint;
import stock.Stock;

import java.io.IOException;

public class StockTablePaneController {

    // columns:
    @FXML private static final TableColumn<Stock, String> symbolColumn =
            new TableColumn<>("Symbol");

    @FXML private static final TableColumn<Stock, String> companyNameColumn =
            new TableColumn<>("Company Name");

    @FXML private static final TableColumn<Stock, Long> priceColumn =
            new TableColumn<>("Price");

    // ObservableList of all the stocks in the program:
    private static ObservableList<Stock> stockObservableList;

    // table:
    private static TableView<Stock> stockTableView;

    public StockTablePaneController() {
        try {
            stockObservableList = FXCollections
                    .observableArrayList(Engine.getStocks().getCollection());
        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }

        // set symbolColumn:
        symbolColumn.setMinWidth(200);
        symbolColumn.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("symbol"));

        // set companyNameColumn:
        companyNameColumn.setMinWidth(200);
        companyNameColumn.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("companyName"));

        // set priceColumn:
        priceColumn.setMinWidth(200);
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<Stock, Long>("price"));
    }

    public static TableView<Stock> getTable() {
        return stockTableView;
    }
}
