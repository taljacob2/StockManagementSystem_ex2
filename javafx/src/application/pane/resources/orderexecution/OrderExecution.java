package application.pane.resources.orderexecution;

import application.JavaFXAppController;
import application.pane.PaneAnimator;
import application.pane.resources.login.selecteduser.SelectedUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import stock.Stock;
import user.User;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This {@code class} is a {@code Controller} loaded from a <tt>.fxml</tt>
 * file.
 *
 * <p>Represents the {@link javafx.scene.layout.Pane} of performing an <i>order
 * execution</i>.</p>
 *
 * <p>
 * This {@code class} uses the {@link User} that has been selected in the
 * previous <i>Login page</i>.
 * </p>
 */
public class OrderExecution implements Initializable {

    @FXML private ComboBox<String> buySellComboBox;
    @FXML private ComboBox<Stock> stockComboBox;
    @FXML private ComboBox<String> orderTypeComboBox;
    @FXML private Button executeOrderButton;

    public OrderExecution() {}

    @Override public void initialize(URL location, ResourceBundle resources) {
        buySellComboBox.getItems().addAll("Buy", "Sell");
        orderTypeComboBox.getItems().addAll("LMT", "MKT");

        initExecuteOrderButton(JavaFXAppController.getStaticBorderPane(),
                JavaFXAppController.getParentContainer(),
                JavaFXAppController.getAnimationType());
    }

    private void initExecuteOrderButton(BorderPane borderPane,
                                        Pane parentContainer,
                                        PaneAnimator.AnimationType animationType) {

        // define 'orderExecutionButton':
        buySellComboBox.setOnAction(
                new PaneAnimator.Handler(borderPane, parentContainer,
                        "/application/pane/resources/orderexecution" +
                                "/OrderExecution.fxml", animationType));

        //     TODO: check:
        System.out.println("selectedUser = " + SelectedUser.getSelectedUser());
    }

}
