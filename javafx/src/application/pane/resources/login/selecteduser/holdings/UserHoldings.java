package application.pane.resources.login.selecteduser.holdings;

import application.pane.ContainsAnotherPane;
import application.pane.resources.login.selecteduser.SelectedUser;
import currency.Currency;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} of showing the
 * <i>Status</i> of a <i>User</i> in the system.</p>
 */
public class UserHoldings extends ContainsAnotherPane {

    @FXML private Label totalHoldingsWorthLabel;

    public UserHoldings() {

        // Show 'StockTablePane' on the inner BorderPane's CENTER:
        super("/application/pane/resources/login/selecteduser/holdings/table/HoldingsTablePane.fxml");
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        // calculate total holdings worth, and set label:
        long totalHoldingsWorth =
                SelectedUser.getSelectedUser().calculateTotalHoldingsWorth();
        totalHoldingsWorthLabel.setText("[Total Holdings Worth = " +
                Currency.numberFormat.format(totalHoldingsWorth) + "]");
    }

}
