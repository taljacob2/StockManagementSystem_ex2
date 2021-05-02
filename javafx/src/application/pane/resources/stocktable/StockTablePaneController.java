package application.pane.resources.stocktable;

import engine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import message.print.MessagePrint;
import stock.Stock;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockTablePaneController implements Initializable {

    // ObservableList of all the stocks in the program:
    private ObservableList<Stock> stockObservableList;

    @FXML private TableView<Stock> tableView;
    @FXML private TableColumn<Stock, String> symbolColumn;
    @FXML private TableColumn<Stock, String> companyNameColumn;
    @FXML private TableColumn<Stock, Long> priceColumn;

    public StockTablePaneController() {
        try {
            stockObservableList = FXCollections
                    .observableArrayList(Engine.getStocks().getCollection());
        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }
    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        // columns:
        symbolColumn.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("symbol"));
        companyNameColumn.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("companyName"));
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<Stock, Long>("price"));

        tableView.setItems(stockObservableList);

    }

}
