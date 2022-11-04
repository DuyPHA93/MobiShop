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
import model.PagingQuery;
import model.Product;

public class OrderDAO {
	public static Order findById(int id) throws Exception {
		Order item = null;

		Connection conn = DBContext.getConnection();
		String sql = "select * from orders where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			String orderNo = rs.getString("order_no");
			Date orderDate = rs.getTimestamp("order_date");
			Integer personOrderId = rs.getInt("person_order_id");
			String contactFirstName = rs.getString("contact_first_name");
			String contactLastName = rs.getString("contact_last_name");
			String contactEmail = rs.getString("contact_email");
			String contactPhone = rs.getString("contact_phone");
			String contactAddress = rs.getString("contact_address");
			String note = rs.getString("note");
			String status = rs.getString("status");
			String reasonCancelOrder = rs.getString("reason_for_cancel_order");
			Integer totalQuantity = rs.getInt("total_quantity");
			Double totalPrice = rs.getDouble("total_price");
			String warehousePickup = rs.getString("warehouse_pickup");
			String shippingCode = rs.getString("shipping_code");
			String transporter = rs.getString("transporter");
			String shippingStatus = rs.getString("shipping_status");
			Double totalWeight = rs.getDouble("total_weight");
			Date deliveryDate = rs.getTimestamp("delivery_date");
			Date deliveredDate = rs.getTimestamp("delivered_date");
			Date receiveOrderDate = rs.getTimestamp("receive_order_date");
			Date confirmCompleteOrderDate = rs.getTimestamp("confirm_complete_order_date");
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new Order(id, orderNo, orderDate, personOrderId, contactFirstName, contactLastName, 
					contactEmail, contactPhone, contactAddress, note, status, reasonCancelOrder, 
					totalQuantity, totalPrice, warehousePickup, shippingCode, transporter, shippingStatus,
					totalWeight, deliveryDate, deliveredDate, receiveOrderDate, confirmCompleteOrderDate, 
					createdAt, updatedAt);
		}

		stmt.close();
		conn.close();

		return item;
	}
	
	public static List<Order> findByUserId(int id) throws Exception {
		List<Order> list = new ArrayList<>();

		Connection conn = DBContext.getConnection();
		String sql = "select * from orders where person_order_id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Integer orderId = rs.getInt("id");
			String orderNo = rs.getString("order_no");
			Date orderDate = rs.getTimestamp("order_date");
			Integer personOrderId = rs.getInt("person_order_id");
			String contactFirstName = rs.getString("contact_first_name");
			String contactLastName = rs.getString("contact_last_name");
			String contactEmail = rs.getString("contact_email");
			String contactPhone = rs.getString("contact_phone");
			String contactAddress = rs.getString("contact_address");
			String note = rs.getString("note");
			String status = rs.getString("status");
			String reasonCancelOrder = rs.getString("reason_for_cancel_order");
			Integer totalQuantity = rs.getInt("total_quantity");
			Double totalPrice = rs.getDouble("total_price");
			String warehousePickup = rs.getString("warehouse_pickup");
			String shippingCode = rs.getString("shipping_code");
			String transporter = rs.getString("transporter");
			String shippingStatus = rs.getString("shipping_status");
			Double totalWeight = rs.getDouble("total_weight");
			Date deliveryDate = rs.getTimestamp("delivery_date");
			Date deliveredDate = rs.getTimestamp("delivered_date");
			Date receiveOrderDate = rs.getTimestamp("receive_order_date");
			Date confirmCompleteOrderDate = rs.getTimestamp("confirm_complete_order_date");
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			Order item = new Order(orderId, orderNo, orderDate, personOrderId, contactFirstName, contactLastName, 
					contactEmail, contactPhone, contactAddress, note, status, reasonCancelOrder, 
					totalQuantity, totalPrice, warehousePickup, shippingCode, transporter, shippingStatus,
					totalWeight, deliveryDate, deliveredDate, receiveOrderDate, confirmCompleteOrderDate, 
					createdAt, updatedAt);
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}
	
	public static int getTotalRecords(PagingQuery paging) {
		try {
			Connection conn = DBContext.getConnection();
			String sql = "select count(*) as count "
					+ "from orders "
					+ "where order_no like ? or contact_address like ? or total_price = ? ";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, "%" + paging.getQuery() + "%");
			stmt.setString(2, "%" + paging.getQuery() + "%");
			stmt.setString(3, "%" + paging.getQuery() + "%");

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				return rs.getInt("count");
			}

			stmt.close();
			conn.close();

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// Search Product Admin
	public static List<Order> search(PagingQuery paging) throws Exception {
		List<Order> list = new ArrayList<>();
		Order item = null;
		
		int start = (paging.getCurrentPage() - 1) * paging.getPerPage();

		Connection conn = DBContext.getConnection();
		String sql ="select id, order_no, contact_address, order_date, total_quantity, total_price, status, "
				+ "created_at, updated_at "
				+ "from orders "
				+ "where order_no like ? or contact_address like ? or total_price = ? "
				+ "order by order_date desc "
				+ "limit ?, ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, "%" + paging.getQuery() + "%");
		stmt.setString(2, "%" + paging.getQuery() + "%");
		stmt.setString(3, "%" + paging.getQuery() + "%");
		stmt.setInt(4, start);
		stmt.setInt(5, paging.getPerPage());

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Integer id = rs.getInt("id");
			String orderNo = rs.getString("order_no");
			Date orderDate = rs.getTimestamp("order_date");
			String contactAddress = rs.getString("contact_address");
			Integer totalQuantity = rs.getInt("total_quantity");
			Double totalPrice = rs.getDouble("total_price");
			String status = rs.getString("status");
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new Order(id, orderNo, orderDate, contactAddress, status, totalQuantity, 
					totalPrice, createdAt, updatedAt);
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}
	
	public static int create(Order item) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "insert into orders(order_no, order_date, person_order_id, contact_first_name, contact_last_name, "
					+ "contact_email, contact_phone, contact_address, note, status, total_quantity, total_price, "
					+ "created_at) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, item.getOrderNo());
			stmt.setTimestamp(2, new java.sql.Timestamp(item.getOrderDate().getTime()));
			stmt.setInt(3, item.getPersonOrderId());
			stmt.setString(4, item.getContactFirstName());
			stmt.setString(5, item.getContactLastName());
			stmt.setString(6, item.getContactEmail());
			stmt.setString(7, item.getContactPhone());
			stmt.setString(8, item.getContactAddress());
			stmt.setString(9, item.getNote());
			stmt.setString(10, item.getStatus());
			stmt.setInt(11, item.getTotalQuantity());
			stmt.setDouble(12, item.getTotalPrice());
			java.util.Date date = new Date();
			stmt.setTimestamp(13, new java.sql.Timestamp(date.getTime()));
			
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
	
	public static Integer getMaxTailOrderNo(String prefix) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "select MAX(trim(leading '0' from SUBSTRING(order_no, LENGTH(?) + 1, LENGTH(order_no)))) as maxTail from orders where order_no like ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, prefix);
			stmt.setString(2, prefix + "%");
			
			ResultSet rs = stmt.executeQuery();
			Integer maxTail = -1;
			
			if (rs.next()) {
				maxTail = rs.getInt("maxTail");
			}
			
			stmt.close();
			conn.close();
			
			return maxTail;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer updateReceivedOrderStatus(String orderNo) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "update orders set status = ?, receive_order_date = ?, updated_at = ? where order_no = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "da_nhan_hang");
			java.util.Date date = new Date();
			stmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			stmt.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			stmt.setString(4, orderNo);
			
			int resutl = stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
			return resutl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer updateDeliveryOrderStatus(String orderNo, Order item) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "update orders set status = ?, delivery_date = ?, warehouse_pickup = ?, shipping_code = ?, "
					+ "transporter = ?, shipping_status = ?, total_weight = ?, updated_at = ? "
					+ "where order_no = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "dang_giao_hang");
			java.util.Date date = new Date();
			stmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			stmt.setString(3, item.getWarehousePickup());
			stmt.setString(4, item.getShippingCode());
			stmt.setString(5, item.getTransporter());
			stmt.setString(6, "dang_giao_hang");
			stmt.setDouble(7, item.getTotalWeight());
			stmt.setTimestamp(8, new java.sql.Timestamp(date.getTime()));
			stmt.setString(9, orderNo);
			
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
			
			String sql = "update orders set status = ?, shipping_status = ?, delivered_date = ?, updated_at = ? where order_no = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "da_giao_hang");
			stmt.setString(2, "da_giao_hang");
			java.util.Date date = new Date();
			stmt.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			stmt.setTimestamp(4, new java.sql.Timestamp(date.getTime()));
			stmt.setString(5, orderNo);
			
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
			
			String sql = "update orders set status = ?, confirm_complete_order_date = ?, updated_at = ? where order_no = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "hoan_thanh");
			java.util.Date date = new Date();
			stmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			stmt.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			stmt.setString(4, orderNo);
			
			int resutl = stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
			return resutl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer updateCancelOrderStatus(String orderNo, String reasonCancelOrder) {
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "update orders set status = ?, reason_for_cancel_order = ?, updated_at = ? where order_no = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, "da_huy");
			stmt.setString(2, reasonCancelOrder);
			java.util.Date date = new Date();
			stmt.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			stmt.setString(4, orderNo);
			
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
