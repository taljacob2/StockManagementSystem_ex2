package application.pane.container;


import javafx.beans.property.SimpleObjectProperty;

/**
 * This {@code Class} saves a {@link Object} in a {@link SimpleObjectProperty}.
 * Mainly, in order to transfer it to another page.
 */
public class SimpleContainer {

    private static SimpleObjectProperty<Object> object;

    public static Object getObject() {
        return object.get();
    }

    public static void setObject(Object object) {
        SimpleContainer.object.set(object);
    }

    public static SimpleObjectProperty<Object> selectedObjectProperty() {

        // similar to Singleton getInstance():
        if (object == null) {
            object = new SimpleObjectProperty();
        }
        return object;
    }
}

