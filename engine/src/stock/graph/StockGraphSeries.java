package stock.graph;

import engine.collection.EngineCollection;
import javafx.scene.chart.XYChart;
import stock.Stock;
import transaction.Transaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;

/**
 * {@link XYChart.Data}s {@code Collection}, wrapped in a special class. Has a
 * {@code Collection} field of all the {@link XYChart.Data}s together.
 * <p>
 * annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 * </p>
 * <p>
 * This {@code} class stores the information required in order to present a
 * <i>graph</i> for a {@link stock.Stock}'s changing {@link Stock#getPrice()}
 * per {@link Transaction#getTimeStamp()}.
 *
 * @version 1.0
 * @see application.pane.resources.viewstocksgraphs.stockgraph.StockGraph
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rse-stock-graph-series") public class StockGraphSeries
        extends
        EngineCollection<LinkedList<XYChart.Data<String, Long>>, XYChart.Data<String, Long>> {

    /**
     * <b><i>important:</i></b>
     * The <i>default</i> {@code Constructor} is <i>initializing</i> the {@link
     * LinkedList}.
     */
    public StockGraphSeries() {
        setCollection(new LinkedList<>());
    }

    @Override public LinkedList<XYChart.Data<String, Long>> getCollection() {
        return super.getCollection();
    }

    @XmlElement(name = "rse-stock-graph-data") public void setCollection(
            LinkedList<XYChart.Data<String, Long>> collection) {
        super.setCollection(collection);
    }

}



