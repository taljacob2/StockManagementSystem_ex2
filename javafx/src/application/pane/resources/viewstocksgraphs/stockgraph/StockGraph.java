package application.pane.resources.viewstocksgraphs.stockgraph;

import application.pane.resources.viewstocksgraphs.selectedstock.container.SelectedStockContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import stock.Stock;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p><i>Presents</i> a {@link javafx.scene.chart.LineChart}s that is made
 * <i>for a specific {@link Stock}</i></p>
 */
public class StockGraph implements Initializable {

    private final XYChart.Series<String, Long> series =
            new XYChart.Series<String, Long>();

    private ObservableList<XYChart.Data<String, Long>> dataObservableList;

    /**
     * The <i>X</i> axis in the {@link javafx.scene.chart.LineChart}.
     */
    @FXML private CategoryAxis timeStampAxis;

    /**
     * The <i>Y</i> axis in the {@link javafx.scene.chart.LineChart}.
     */
    @FXML private NumberAxis stockPriceAxis;

    @FXML private LineChart<String, Long> lineChart;

    public StockGraph() {}

    @Override public void initialize(URL location, ResourceBundle resources) {

        // Create an empty list:
        dataObservableList = FXCollections.observableArrayList(
                new LinkedList<XYChart.Data<String, Long>>());

        /*
         * Fill 'dataObservableList' of series, with the Entries in the
         * selected-stock's 'StockGraphSeries'.
         */
        SelectedStockContainer.getSelectedStock().getStockGraphSeries()
                .getCollection().stream().forEach(stringLongSimpleEntry -> {
            dataObservableList
                    .add(new XYChart.Data<>(stringLongSimpleEntry.getKey(),
                            stringLongSimpleEntry.getValue()));
        });


        series.getData().addAll(dataObservableList);

        lineChart.getData().addAll(series);
    }

}
