package application.pane.resources.login.selecteduser.pane.borderpane;

import javafx.scene.layout.BorderPane;

public class SaveUserBorderPane {
    private static BorderPane borderPaneToShowTheAnotherInnerPane;

    public static BorderPane getBorderPaneToShowTheAnotherInnerPane() {
        return borderPaneToShowTheAnotherInnerPane;
    }

    public static void setBorderPaneToShowTheAnotherInnerPane(
            BorderPane borderPaneToShowTheAnotherInnerPane) {
        SaveUserBorderPane.borderPaneToShowTheAnotherInnerPane =
                borderPaneToShowTheAnotherInnerPane;
    }
}
