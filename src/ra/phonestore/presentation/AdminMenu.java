package ra.phonestore.presentation;

import ra.phonestore.dao.CategoryDAO;
import ra.phonestore.dao.ProductDAO;
import ra.phonestore.model.Category;
import ra.phonestore.model.Product;
import ra.phonestore.util.Validator;
import ra.phonestore.model.Category;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private Scanner sc = new Scanner(System.in);
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    public void show() {
        while (true) {
            System.out.println("\n========= QUẢN LÝ ADMIN =========");
            System.out.println("1. Quản lý Danh mục");
            System.out.println("2. Quản lý Sản phẩm");
            System.out.println("3. Quản lý Đơn hàng");
            System.out.println("0. Đăng xuất");
            System.out.print("Lựa chọn: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> categoryManager();
                case "2" -> productManager();
                case "3" -> System.out.println("Tính năng đang phát triển...");
                case "0" -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= QUẢN LÝ DANH MỤC =================
    private void categoryManager() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ DANH MỤC ---");
            System.out.println("1. Hiển thị danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa (Xóa mềm)");
            System.out.println("0. Quay lại");
            System.out.print("Lựa chọn: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> showCategories();
                case "2" -> addCategory();
                case "3" -> updateCategory();
                case "4" -> deleteCategory();
                case "0" -> { return; }
            }
        }
    }

    private void showCategories() {
        List<Category> list = categoryDAO.findAllActive();
        System.out.println("\n---------------------------------");
        System.out.printf("| %-5s | %-20s |%n", "ID", "Tên Hãng");
        System.out.println("---------------------------------");
        list.forEach(c -> System.out.printf("| %-5d | %-20s |%n", c.getId(), c.getName()));
        System.out.println("---------------------------------");
    }

    private void addCategory() {
        System.out.print("Nhập tên danh mục: ");
        String name = sc.nextLine();
        if (!Validator.requireNotEmpty(name, "Tên danh mục")) return;
        if (categoryDAO.isNameExists(name)) {
            System.out.println("Lỗi: Tên danh mục đã tồn tại!");
            return;
        }
        if (categoryDAO.insert(name)) System.out.println("Thêm thành công!");
    }

    private void updateCategory() {
        System.out.print("Nhập ID danh mục cần sửa: ");
        int id = Integer.parseInt(sc.nextLine());
        Category old = categoryDAO.findById(id);
        if (old == null) {
            System.out.println("Không tìm thấy ID!");
            return;
        }
        System.out.println("Tên cũ: " + old.getName());
        System.out.print("Nhập tên mới: ");
        String newName = sc.nextLine();
        if (Validator.requireNotEmpty(newName, "Tên mới")) {
            old.setName(newName);
            if (categoryDAO.update(old)) System.out.println("Cập nhật thành công!");
        }
    }

    private void deleteCategory() {
        System.out.print("Nhập ID cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Xác nhận xóa mềm danh mục này? (Y/N): ");
        if (sc.nextLine().equalsIgnoreCase("Y")) {
            if (categoryDAO.softDelete(id)) System.out.println("Đã xóa!");
        }
    }

    // ================= QUẢN LÝ SẢN PHẨM =================
    private void productManager() {
        int page = 0;
        int size = 5;
        String sortDir = "ASC";

        while (true) {
            showAllProducts(size, page * size, sortDir);
            System.out.println("\n1. Thêm | 2. Sửa | 3. Xóa | 4. Tìm kiếm | 5. Đổi hướng sắp xếp | 6. Trang sau | 7. Trang trước | 0. Quay lại");
            System.out.print("Lựa chọn: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> addProduct();
                case "2" -> editProduct();
                case "3" -> deleteProduct();
                case "4" -> searchProduct();
                case "5" -> sortDir = sortDir.equals("ASC") ? "DESC" : "ASC";
                case "6" -> page++;
                case "7" -> { if (page > 0) page--; }
                case "0" -> { return; }
            }
        }
    }

    private void showAllProducts(int limit, int offset, String dir) {
        List<Product> list = productDAO.findAll(limit, offset, "price", dir);
        System.out.println("\n----------------------------------------------------------------------------------");
        System.out.printf("| %-3s | %-20s | %-10s | %-10s | %-12s | %-5s |%n", "ID", "Tên sản phẩm", "Dung lượng", "Màu", "Giá", "Kho");
        System.out.println("----------------------------------------------------------------------------------");
        list.forEach(p -> System.out.printf("| %-3d | %-20s | %-10s | %-10s | %-12.0f | %-5d |%n",
                p.getId(), p.getName(), p.getCapacity(), p.getColor(), p.getPrice(), p.getStock()));
        System.out.println("----------------------------------------------------------------------------------");
    }

    private void addProduct() {
        Product p = new Product();
        System.out.print("Tên sản phẩm: "); p.setName(sc.nextLine());
        System.out.print("ID Danh mục: "); p.setCategoryId(Integer.parseInt(sc.nextLine()));
        System.out.print("Dung lượng: "); p.setCapacity(sc.nextLine());
        System.out.print("Màu sắc: "); p.setColor(sc.nextLine());

        System.out.print("Giá bán (>0): ");
        double price = Double.parseDouble(sc.nextLine());
        if (price <= 0) { System.out.println("Giá không hợp lệ!"); return; }
        p.setPrice(price);

        System.out.print("Số lượng tồn kho: ");
        int stock = Integer.parseInt(sc.nextLine());
        if (stock < 0) { System.out.println("Số lượng không hợp lệ!"); return; }
        p.setStock(stock);

        if (productDAO.insert(p)) System.out.println("Thêm sản phẩm thành công!");
    }

    public void editProduct() {
        System.out.print("Nhập ID sản phẩm cần sửa: ");
        int id = Integer.parseInt(sc.nextLine());
        Product old = productDAO.findById(id);
        if (old == null) { System.out.println("Không tìm thấy!"); return; }

        System.out.println("Thông tin cũ: " + old.getName() + " [" + old.getPrice() + "]");
        System.out.print("Tên mới (Enter để bỏ qua): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) old.setName(name);

        System.out.print("Giá mới (0 để bỏ qua): ");
        double price = Double.parseDouble(sc.nextLine());
        if (price > 0) old.setPrice(price);

        if (productDAO.update(old)) System.out.println("Cập nhật thành công!");
    }

    public void deleteProduct() {
        System.out.print("Nhập ID cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Xác nhận xóa vĩnh viễn sản phẩm này? (Y/N): ");
        if (sc.nextLine().equalsIgnoreCase("Y")) {
            if (productDAO.delete(id)) System.out.println("Đã xóa thành công!");
        }
    }

    private void searchProduct() {
        System.out.print("Nhập tên sản phẩm cần tìm: ");
        String keyword = sc.nextLine();
        List<Product> results = productDAO.searchByName(keyword);
        if (results.isEmpty()) System.out.println("Không tìm thấy kết quả.");
        else results.forEach(p -> System.out.println(p.getId() + ". " + p.getName() + " - " + p.getPrice()));
    }
}