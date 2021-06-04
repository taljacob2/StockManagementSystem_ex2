package application.pane.resources.printall;

import application.pane.PaneReplacer;
import application.pane.resources.printall.stock.container.PrintStockContainer;
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

    /**
     * The <b>core</b> method of this {@code class}.
     *
     * <p>
     * This method gets all the {@link Stock}s from the {@link Engine}, and sets
     * each {@link Stock} of it to a {@link TitledPane} of its own.
     * </p>
     * <p>
     * It does so, by using a <i>index</i> iterator counter, that indicates the
     * <i>current</i> {@link Stock} being iterated in the {@code
     * stockList.forEach} method. In each iteration:
     * <ul>
     *     <li>the <i>index</i> of {@link PrintStockContainer#getIndex()} is
     *     being increased.</li>
     *     <li>the <i>correct</i> {@link Stock} is being transferred
     *     into a new {@link TitledPane} and is being {@code add}ed to the
     *     <i>result</i> {@code List<TitledPane>}.</li>
     * </ul>
     * <blockquote><b>Warning:</b> the <i>index</i> of
     * {@link PrintStockContainer#getIndex()}<b>must</b> be
     * initialized to '0' before/after the invocation of the  to {@code
     * stockList.forEach} method.
     * </blockquote>
     *
     * @return the correct {@code List<TitledPane>} of all the {@link Stock}s in
     * the {@link Engine}.
     * @see PrintStockContainer
     * @see application.pane.resources.printall.stock.PrintStock
     */
    private List<TitledPane> createTitledPanesForEachStock() {
        List<TitledPane> titledPanes = new LinkedList<>();
        try {
            List<Stock> stockList = Engine.getStocks().getCollection();

            int index = 0;
            stockList.forEach(new Consumer<Stock>() {
                @Override public void accept(Stock stock) {

                    // Important: Set the iteration index to '0'.
                    PrintStockContainer.setIndex(index);

                    /*
                     * Call the correct "Print Stock" with this "index",
                     * and add it to the List.
                     */
                    titledPanes.add(new TitledPane(stock.getSymbol(),
                            getPane("/application/pane/resources/printall/stock/PrintStock.fxml")));
                }
            });

            /*
             * Important: Set the iteration index to "0",
             * just to make sure future users won't fall into an error.
             */
            PrintStockContainer.setIndex(0);

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