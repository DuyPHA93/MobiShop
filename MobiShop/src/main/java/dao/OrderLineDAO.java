package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import context.DBContext;
import model.Order;
import model.OrderLine;
import model.Product;

public class OrderLineDAO {
	public static List<OrderLine> findByOrderNo(String orderNo) {
		List<OrderLine> item = new ArrayList<>();
		
		try {
			Connection conn = DBContext.getConnection();
			String sql = "select * from order_lines where order_no = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, orderNo);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Integer productId = rs.getInt("product_id");
				String productCode = rs.getString("product_code");
				String productName = rs.getString("product_name");
				Integer productQuantity = rs.getInt("product_quantity");
				Double productPrice = rs.getDouble("product_price");
				Double productTotalPrice = rs.getDouble("product_total_price");
				String fileName = rs.getString("file_name");
				String status = rs.getString("status");
				Date createdAt = rs.getTimestamp("created_at");
				Date updatedAt = rs.getTimestamp("updated_at");

				OrderLine line = new OrderLine(productId, orderNo, productCode, productName, productQuantity,  
						productPrice, productTotalPrice, fileName, status, createdAt, updatedAt);
				
				item.add(line);
			}

			stmt.close();
			conn.close();

			return item;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public static int create(OrderLine item) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "insert into order_lines(order_no, product_id, product_code, product_name, product_quantity, product_price, "
					+ "product_total_price, file_name, status, created_at) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, item.getOrderNo());
			stmt.setInt(2, item.getProductId());
			stmt.setString(3, item.getProductCode());
			stmt.setString(4, item.getProductName());
			stmt.setInt(5, item.getProductQuantity());
			stmt.setDouble(6, item.getProductPrice());
			stmt.setDouble(7, item.getProductTotalPrice());
			stmt.setString(8, item.getFileName());
			stmt.setString(9, item.getStatus());
			java.util.Date date = new Date();
			stmt.setTimestamp(10, new java.sql.Timestamp(date.getTime()));
			
			int result = stmt.executeUpdate();
			
			int id = -1;
			
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                id = generatedKeys.getInt(1);
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }

			stmt.close();
			conn.close();

			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static Integer updateReceivedOrderStatus(String orderNo) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "update order_lines set status = ?, updated_at = ? where order_no = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "da_nhan_hang");
			java.util.Date date = new Date();
			stmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			stmt.setString(3, orderNo);
			
			int resutl = stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
			return resutl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer updateDeliveryOrderStatus(String orderNo) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "update order_lines set status = ?, updated_at = ? where order_no = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "dang_giao_hang");
			java.util.Date date = new Date();
			stmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			stmt.setString(3, orderNo);
			
			int resutl = stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
			return resutl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer updateDeliveredOrderStatus(String orderNo) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "update order_lines set status = ?, updated_at = ? where order_no = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "da_giao_hang");
			java.util.Date date = new Date();
			stmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			stmt.setString(3, orderNo);
			
			int resutl = stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
			return resutl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer updateCompleteOrderStatus(String orderNo) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "update order_lines set status = ?, updated_at = ? where order_no = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "hoan_thanh");
			java.util.Date date = new Date();
			stmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			stmt.setString(3, orderNo);
			
			int resutl = stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
			return resutl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer updateCancelOrderStatus(String orderNo) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "update order_lines set status = ?, updated_at = ? where order_no = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "da_huy");
			java.util.Date date = new Date();
			stmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			stmt.setString(3, orderNo);
			
			int resutl = stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
			return resutl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
