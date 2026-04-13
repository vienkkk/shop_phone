package ra.phonestore.presentation;

import ra.phonestore.dao.OrderDAO;
import ra.phonestore.dao.ProductDAO;
import ra.phonestore.model.CartItem;
import ra.phonestore.model.Product;
import ra.phonestore.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
    private final Scanner sc = new Scanner(System.in);
    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final List<CartItem> cart = new ArrayList<>();

    public void show(User user) {
        while (true) {
            System.out.println("\n========= MENU KHÁCH HÀNG =========");
            System.out.println("1. Xem danh sách sản phẩm còn hàng");
            System.out.println("2. Thêm sản phẩm vào giỏ hàng");
            System.out.println("3. Xem giỏ hàng & Đặt hàng");
            System.out.println("0. Đăng xuất");
            System.out.print("Lựa chọn: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> showAvailableProducts();
                case "2" -> addToCart();
                case "3" -> viewCartAndOrder(user);
                case "0" -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void showAvailableProducts() {
        List<Product> products = productDAO.findAvailable();
        System.out.printf("%-5s | %-20s | %-12s | %-5s%n", "ID", "Tên", "Giá", "Kho");
        products.forEach(p -> System.out.printf("%-5d | %-20s | %-12.0f | %-5d%n",
                p.getId(), p.getName(), p.getPrice(), p.getStock()));
    }

    private void addToCart() {
        System.out.print("Nhập ID sản phẩm muốn mua: ");
        int id = Integer.parseInt(sc.nextLine());
        Product p = productDAO.findById(id);

        if (p == null || p.getStock() <= 0) {
            System.out.println("Sản phẩm không tồn tại hoặc đã hết hàng!");
            return;
        }

        System.out.print("Nhập số lượng mua: ");
        int qty = Integer.parseInt(sc.nextLine());

        if (qty > p.getStock()) {
            System.out.println("Số lượng trong kho không đủ!");
            return;
        }

        cart.add(new CartItem(p, qty));
        System.out.println("Đã thêm vào giỏ hàng!");
    }

    private void viewCartAndOrder(User user) {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng đang trống.");
            return;
        }
        double total = 0;
        for (CartItem item : cart) {
            double subTotal = item.getProduct().getPrice() * item.getQuantity();
            System.out.println(item.getProduct().getName() + " x" + item.getQuantity() + " = " + subTotal);
            total += subTotal;
        }
        System.out.println("TỔNG TIỀN: " + total);
        System.out.print("Xác nhận đặt hàng? (Y/N): ");
        if (sc.nextLine().equalsIgnoreCase("Y")) {
            // Lưu đơn hàng và tự động trừ kho
            if (orderDAO.placeOrder(user.getId(), total, cart)) {
                System.out.println("Đặt hàng thành công!");
                cart.clear();
            } else {
                System.out.println("Đặt hàng thất bại!");
            }
        }
    }
}