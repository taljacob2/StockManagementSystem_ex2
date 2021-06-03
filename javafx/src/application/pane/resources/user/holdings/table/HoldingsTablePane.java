package application.pane.resources.user.holdings.table;

import application.pane.resources.login.selecteduser.SelectedUser;
import engine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import user.holding.item.Item;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p><i>Controls</i> the {@link TableView} of all the
 * {@link user.holding.Holdings} of a specific {@link SelectedUser#getSelectedUser()}.</p>
 */
public class HoldingsTablePane implements Initializable {

    /**
     * {@link ObservableList} of all the stocks in the program:
     */
    private ObservableList<Item> holdingsObservableList;

    /**
     * The {@link TableView} needed to be shown.
     */
    @FXML private TableView<Item> tableView;

    /**
     * A column in the {@link TableView}.
     */
    @FXML private TableColumn<Item, String> symbolColumn;

    /**
     * A column in the {@link TableView}.
     */
    @FXML private TableColumn<Item, Long> quantityColumn;

    /**
     * A column in the {@link TableView}.
     */
    // @FXML private TableColumn<Stock, Long> priceColumn;;

    /**
     * Constructor. try to get the {@link stock.Stocks} in the {@link Engine}.
     */
    public HoldingsTablePane() {
        holdingsObservableList = FXCollections.observableArrayList(
                SelectedUser.getSelectedUser().getHoldings().getCollection());
    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        // initialize columns:
        symbolColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("symbol"));
        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<Item, Long>("quantity"));
        // priceColumn.setCellValueFactory(
        //         new PropertyValueFactory<Stock, Long>("price"));


        // set the 'tableView' to the columns provided:
        tableView.setItems(holdingsObservableList);
    }

}
