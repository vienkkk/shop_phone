package ra.phonestore.dao;

import ra.phonestore.model.CartItem;
import ra.phonestore.util.DBConnection;
import java.sql.*;
import java.util.List;

public class OrderDAO {
    public boolean placeOrder(int userId, double totalAmount, List<CartItem> cart) {
        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            if (conn == null) return false;
            conn.setAutoCommit(false);

            String sqlOrder = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, 'PENDING')";
            PreparedStatement psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, userId);
            psOrder.setDouble(2, totalAmount);
            psOrder.executeUpdate();

            ResultSet rs = psOrder.getGeneratedKeys();
            int orderId = rs.next() ? rs.getInt(1) : 0;

            String sqlDetail = "INSERT INTO order_details (order_id, product_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?)";
            String sqlUpdateStock = "UPDATE products SET stock = stock - ? WHERE id = ?";

            for (CartItem item : cart) {
                PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                psDetail.setInt(1, orderId);
                psDetail.setInt(2, item.getProduct().getId());
                psDetail.setInt(3, item.getQuantity());
                psDetail.setDouble(4, item.getProduct().getPrice());
                psDetail.executeUpdate();

                // Trừ kho hàng
                PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock);
                psStock.setInt(1, item.getQuantity());
                psStock.setInt(2, item.getProduct().getId());
                psStock.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        }
    }
}