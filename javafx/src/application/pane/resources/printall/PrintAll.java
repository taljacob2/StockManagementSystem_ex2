package application.pane.resources.printall;

import application.pane.PaneReplacer;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import message.print.MessagePrint;
import stock.Stock;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} of showing all the
 * {@link order.Order}s and {@link transaction.Transaction}s in the system, for
 * each {@link stock.Stock}.
 * </p>
 */
public class PrintAll implements Initializable, PaneReplacer {

    @FXML private Accordion accordion;

    public PrintAll() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        List<TitledPane> titledPaneList = createTitledPanesForEachStock();
        accordion.getPanes().addAll(titledPaneList);
    }

    private List<TitledPane> createTitledPanesForEachStock() {
        List<TitledPane> titledPanes = new LinkedList<>();
        try {
            List<Stock> stockList = Engine.getStocks().getCollection();

            stockList.forEach(new Consumer<Stock>() {
                @Override public void accept(Stock stock) {
                    titledPanes.add(new TitledPane(stock.getSymbol(),
                            getPane("/application/pane/resources/welcome/Welcome.fxml")));
                }
            });

        } catch (IOException e) {

            /*
             * Note: this exception should not happen thanks to the initial
             * check of stocks.
             */
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
        }

        return titledPanes;
    }


}