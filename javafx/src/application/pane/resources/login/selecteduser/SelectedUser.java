package application.pane.resources.login.selecteduser;

import javafx.beans.property.SimpleObjectProperty;
import user.User;

/**
 * This {@code Class} saves the {@link user.User} that was selected in the
 * <i>Login page</i> in a {@link SimpleObjectProperty}, in order to transfer it
 * to the <i>Order-Execution page</i>.
 */
public class SelectedUser {

    private static SimpleObjectProperty<User> selectedUser;

    public static User getSelectedUser() {
        return selectedUser.get();
    }

    public static void setSelectedUser(User selectedUser) {
        SelectedUser.selectedUser.set(selectedUser);
    }

    public static SimpleObjectProperty<User> selectedUserProperty() {

        // similar to Singleton getInstance():
        if (selectedUser == null) {
            selectedUser = new SimpleObjectProperty();
        }
        return selectedUser;
    }
}

