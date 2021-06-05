package application.pane.resources.viewstocksgraphs;

import application.javafxapp.JavaFXAppHandler;
import application.pane.resources.login.selecteduser.SelectedUser;
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
public class ViewStocksGraphs implements Initializable {

    @FXML private ComboBox<String> stockSymbolComboBox;

    public ViewStocksGraphs() {}

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

        new JavaFXAppHandler(
                "/application/pane/resources/login/selecteduser/pane/UserPane.fxml")
                .handleOnce(stockSymbolComboBox.getValue());

        SelectedUser.selectedUserProperty().bind(userComboBox.valueProperty());

        loginButton.disableProperty()
                .bind(userComboBox.valueProperty().isNull());
    }

}
