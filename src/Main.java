import ra.phonestore.model.User;
import ra.phonestore.presentation.AdminMenu;
import ra.phonestore.presentation.AuthMenu;
import ra.phonestore.presentation.CustomerMenu;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    AuthMenu authMenu = new AuthMenu();
    while (true) {
        User currentUser = authMenu.showMenuAuth();
        if (currentUser != null) {
            if ("ADMIN".equals(currentUser.getRole())) {
                new AdminMenu().show();
            } else {
                new CustomerMenu().show();
            }
        }
    }
}
