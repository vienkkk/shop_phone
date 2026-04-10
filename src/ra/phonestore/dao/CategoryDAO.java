package ra.phonestore.dao;

import ra.phonestore.model.Category;
import ra.phonestore.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // Lấy danh sách danh mục chưa bị xóa (is_deleted = 0)
    public List<Category> findAllActive() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE is_deleted = 0";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name"), false));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // Khắc phục lỗi: isNameExists
    public boolean isNameExists(String name) {
        String sql = "SELECT COUNT(*) FROM categories WHERE name = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) { return false; }
    }

    // Khắc phục lỗi: insert (cho phép thêm mới)
    public boolean insert(String name) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    // Khắc phục lỗi: softDelete (Cập nhật trạng thái thay vì xóa thật)
    public boolean softDelete(int id) {
        String sql = "UPDATE categories SET is_deleted = 1 WHERE id = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public Category findById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ? AND is_deleted = 0";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Category(rs.getInt("id"), rs.getString("name"), false);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean update(Category c) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setInt(2, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}