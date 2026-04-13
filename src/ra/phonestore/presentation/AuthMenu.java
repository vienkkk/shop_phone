package ra.phonestore.presentation;

import ra.phonestore.model.User;
import ra.phonestore.service.AuthService;
import ra.phonestore.util.Validator;
import java.util.Scanner;

public class AuthMenu {
    private AuthService authService = new AuthService();
    private Scanner sc = new Scanner(System.in);

    public User showMenuAuth() {
        while (true) {
            System.out.println("""
            ============ SMARTPHONE STORE ============
            |  1. ĐĂNG KÝ   |  2. ĐĂNG NHẬP  |  0. THOÁT |
            ==========================================""");
            System.out.print("Lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> register();
                case 2 -> {
                    User user = login();
                    if (user != null) return user;
                }
                case 0 -> System.exit(0);
            }
        }
    }

    private void register() {
        User user = new User();
        System.out.print("Nhập Fullname: "); String fullName = sc.nextLine();
        if (!Validator.requireNotEmpty(fullName, "Fullname")) return;

        System.out.print("Nhập Username: "); String username = sc.nextLine();
        if (!Validator.requireNotEmpty(username, "Username")) return;

        System.out.print("Nhập Email: "); String email = sc.nextLine();
        if (!Validator.requireValidEmail(email)) return;

        System.out.print("Nhập Password: "); String pass = sc.nextLine();
        if (!Validator.requireNotEmpty(pass, "Password")) return;

        System.out.print("Nhập Phone: "); String phone = sc.nextLine();
        if (!Validator.requireValidPhone(phone)) return;

        user.setFullName(fullName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(pass);
        user.setPhone(phone);

        if (authService.register(user)) System.out.println("Đăng ký thành công!");
        else System.out.println("Đăng ký thất bại!");
    }

    private User login() {
        System.out.print("Username: "); String user = sc.nextLine();
        System.out.print("Password: "); String pass = sc.nextLine();
        User loggedUser = authService.login(user, pass);
        if (loggedUser == null) System.out.println("Sai tài khoản hoặc mật khẩu!");
        return loggedUser;
    }
}