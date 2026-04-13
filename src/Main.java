import ra.phonestore.model.User;
import ra.phonestore.presentation.AdminMenu;
import ra.phonestore.presentation.AuthMenu;
import ra.phonestore.presentation.CustomerMenu;

void main() {
    AuthMenu authMenu = new AuthMenu();
    while (true) {
        User currentUser = authMenu.showMenuAuth();
        if (currentUser != null) {
            if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
                new AdminMenu().show();
            } else {
                new CustomerMenu().show(currentUser);
            }
        }
    }
}