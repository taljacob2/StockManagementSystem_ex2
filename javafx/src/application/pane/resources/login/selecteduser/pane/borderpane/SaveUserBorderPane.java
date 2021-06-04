package application.pane.resources.login.selecteduser.pane.borderpane;

import javafx.scene.layout.BorderPane;

/**
 * This class stores the {@link BorderPane} that is used as the {@code
 * borderPaneToShowTheAnotherInnerPane} in the {@link application.pane.resources.login.selecteduser.pane.UserPane},
 * for using it afterwards in {@link application.pane.resources.orderexecution.OrderExecution#setPane(BorderPane,
 * String)}
 */
public class SaveUserBorderPane {
    private static BorderPane borderPaneToShowTheAnotherInnerPane;

    public static BorderPane getBorderPaneToShowTheAnotherInnerPane() {
        return borderPaneToShowTheAnotherInnerPane;
    }

    public static void setBorderPaneToShowTheAnotherInnerPane(BorderPane borderPaneToShowTheAnotherInnerPane) {
        SaveUserBorderPane.borderPaneToShowTheAnotherInnerPane =
                borderPaneToShowTheAnotherInnerPane;
    }
}
