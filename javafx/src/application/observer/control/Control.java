package application.observer.control;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public interface Control {

    default void FXMLLoad(String FXMLFile) {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource(FXMLFile));
        // fxmlLoader.setRoot(this);
        // fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}