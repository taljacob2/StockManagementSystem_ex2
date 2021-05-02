import engine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import message.print.MessagePrint;
import stock.Stock;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockTablePaneController implements Initializable {


    // ObservableList of all the stocks in the program:
    private static ObservableList<Stock> stockObservableList;

    // table:
    @FXML private TableView stockTableView;

    public StockTablePaneController() {
        try {
            stockObservableList = FXCollections
                    .observableArrayList(Engine.getStocks().getCollection());
        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }
        //
        // // set symbolColumn:
        // symbolColumn.setMinWidth(200);
        // symbolColumn.setCellValueFactory(
        //         new PropertyValueFactory<Stock, String>("symbol"));
        //
        // // set companyNameColumn:
        // companyNameColumn.setMinWidth(200);
        // companyNameColumn.setCellValueFactory(
        //         new PropertyValueFactory<Stock, String>("companyName"));
        //
        // // set priceColumn:
        // priceColumn.setMinWidth(200);
        // priceColumn.setCellValueFactory(
        //         new PropertyValueFactory<Stock, Long>("price"));

        // initialize 'stockTableView':
        stockTableView = new TableView<>(stockObservableList);


        // // set content of table:
        // stockTableView.setItems(stockObservableList);
    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        // columns:
        TableColumn<Stock, String> symbolColumn = new TableColumn<>("Symbol");

        TableColumn<Stock, String> companyNameColumn =
                new TableColumn<>("Company Name");

        TableColumn<Stock, Long> priceColumn = new TableColumn<>("Price");

        // stockTableView.getColumns().addAll()

    }
}
