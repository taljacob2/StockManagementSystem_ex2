package stock.graph;

import stock.Stock;
import transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * This {@code} class stores the information required in order to present a
 * <i>graph</i> for a {@link stock.Stock}'s changing {@link Stock#getPrice()}
 * per {@link Transaction#getTimeStamp()}.
 *
 * <blockquote><b>Note:</b> both {@link #priceList} and
 * {@link #timeStampList}'s {@code size}s must be the same {@code size}.
 * </blockquote>
 *
 * @version 1.0
 */
public class StockGraph {
    List<Long> priceList;
    List<String> timeStampList;

    public StockGraph() {
        priceList = new ArrayList<>();
        timeStampList = new ArrayList<>();
    }

    public List<Long> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Long> priceList) {
        this.priceList = priceList;
    }

    public List<String> getTimeStampList() {
        return timeStampList;
    }

    public void setTimeStampList(List<String> timeStampList) {
        this.timeStampList = timeStampList;
    }
}
