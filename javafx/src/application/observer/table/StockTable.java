package application.observer.table;

import application.observer.control.Control;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import stock.Stock;

public class StockTable extends TableView<Stock> implements Control {

    @FXML Button testThisButton = new Button();

    public StockTable(String FXMLFile) {
        this.FXMLLoad(FXMLFile);
    }


}
