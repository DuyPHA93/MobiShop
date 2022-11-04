package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import context.DBContext;
import model.PagingQuery;
import model.ProductType;

public class ProductTypeDAO {

	public static ProductType findById(int id) throws Exception {
		ProductType item = null;

		Connection conn = DBContext.getConnection();
		String sql = "select * from product_types where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			String code = rs.getString("code");
			String name = rs.getString("name");
			boolean active = Integer.parseInt(rs.getString("active")) == 1 ? true : false;
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new ProductType(id, code, name, active, createdAt, updatedAt);
		}

		stmt.close();
		conn.close();

		return item;
	}
	
	public static int getTotalRecords(PagingQuery paging) {
		try {
			Connection conn = DBContext.getConnection();
			String sql = "select count(*) as count "
					+ "from product_types "
					+ "left join files on files.model_id = product_types.id and files.model_name ='ProductType' "
					+ "where product_types.id like ? or product_types.code like ? or product_types.name like ? ";

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

	public static List<ProductType> search(PagingQuery paging) throws Exception {
		List<ProductType> list = new ArrayList<>();
		ProductType item = null;
		
		int start = (paging.getCurrentPage() - 1) * paging.getPerPage();

		Connection conn = DBContext.getConnection();
		String sql = "select product_types.id as id, product_types.code as code, product_types.name as name,"
				+ "files.name as file_name, product_types.active as active, "
				+ "product_types.created_at as created_at, product_types.updated_at as updated_at "
				+ "from product_types "
				+ "left join files on files.model_id = product_types.id and files.model_name ='ProductType' "
				+ "where product_types.id like ? or product_types.code like ? or product_types.name like ? "
				+ "order by id "
				+ "limit ?, ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, "%" + paging.getQuery() + "%");
		stmt.setString(2, "%" + paging.getQuery() + "%");
		stmt.setString(3, "%" + paging.getQuery() + "%");
		stmt.setInt(4, start);
		stmt.setInt(5, paging.getPerPage());

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			item = new ProductType(rs.getInt("id"), rs.getString("code"), rs.getString("name"), 
					rs.getBoolean("active"), rs.getDate("created_at"), rs.getDate("updated_at"));
			item.setFileName(rs.getString("file_name"));
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}
	
	public static List<ProductType> getAllByActived() throws Exception {
		List<ProductType> list = new ArrayList<>();
		ProductType item = null;

		Connection conn = DBContext.getConnection();
		String sql = "select * from product_types "
				+ "where active = 1 "
				+ "order by id ";

		PreparedStatement stmt = conn.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			item = new ProductType(rs.getInt("id"), rs.getString("code"), rs.getString("name"), 
					rs.getBoolean("active"), rs.getDate("created_at"), rs.getDate("updated_at"));
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}

	public static int create(ProductType item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "insert into product_types (code, name, active, created_at) values(?, ?, ?, ?)";

		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, item.getCode());
		stmt.setString(2, item.getName());
		stmt.setBoolean(3, item.isActive());
		java.util.Date date = new Date();
		stmt.setTimestamp(4, new java.sql.Timestamp(date.getTime()));

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
	}

	public static int update(ProductType item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "update product_types set name = ?, active = ?, updated_at = ? where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, item.getName());
		stmt.setBoolean(2, item.isActive());
		java.util.Date date = new Date();
		stmt.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
		stmt.setInt(4, item.getId());

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static int delete(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "delete from product_types where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static int disable(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "update product_types set active = 0, updated_at = ? where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		java.util.Date date = new Date();
		stmt.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
		stmt.setInt(2, id);

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}

	public static boolean checkExists(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "select count(*) as count from product_types where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();

		int count = 0;

		if (rs.next()) {
			count = rs.getInt("count");
		}

		stmt.close();
		conn.close();

		return count == 0 ? false : true;
	}

	public static boolean checkExists(String code) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "select count(*) as count from product_types where code = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, code);

		ResultSet rs = stmt.executeQuery();

		int count = 0;

		if (rs.next()) {
			count = rs.getInt("count");
		}

		stmt.close();
		conn.close();

		return count == 0 ? false : true;
	}
}
