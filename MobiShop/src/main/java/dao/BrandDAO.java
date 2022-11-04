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
import model.Brand;
import model.PagingQuery;
import model.ProductType;

public class BrandDAO {
	public static Brand findById(int id) throws Exception {
		Brand item = null;

		Connection conn = DBContext.getConnection();
		String sql = "select brands.code as code, brands.name as name, brands.active as active, "
				+ "brands.product_type_id as product_type_id, product_types.name as product_type_name, "
				+ "brands.created_at as created_at, brands.updated_at as updated_at "
				+ "from brands "
				+ "inner join product_types on product_types.id = brands.product_type_id "
				+ "where brands.id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			String code = rs.getString("code");
			String name = rs.getString("name");
			int productTypeId = rs.getInt("product_type_id");
			String productTypeName = rs.getString("product_type_name");
			boolean active = Integer.parseInt(rs.getString("active")) == 1 ? true : false;
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new Brand(id, code, name, active, productTypeId, productTypeName, createdAt, updatedAt);
		}

		stmt.close();
		conn.close();

		return item;
	}
	
	public static int getTotalRecords(PagingQuery paging) {
		try {
			Connection conn = DBContext.getConnection();
			String sql = "select count(*) as count "
					+ "from brands "
					+ "inner join product_types on product_types.id = brands.product_type_id "
					+ "where brands.id like ? or brands.code like ? or brands.name like ?  or product_types.name like ? ";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, "%" + paging.getQuery() + "%");
			stmt.setString(2, "%" + paging.getQuery() + "%");
			stmt.setString(3, "%" + paging.getQuery() + "%");
			stmt.setString(4, "%" + paging.getQuery() + "%");

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

	public static List<Brand> search(PagingQuery paging) throws Exception {
		List<Brand> list = new ArrayList<>();
		Brand item = null;
		
		int start = (paging.getCurrentPage() - 1) * paging.getPerPage();

		Connection conn = DBContext.getConnection();
		String sql = "select brands.id as id, brands.code as code, brands.name as name,"
				+ "product_types.id as product_type_id, product_types.name as product_type_name, brands.active as active, "
				+ "brands.created_at as created_at, brands.updated_at as updated_at "
				+ "from brands "
				+ "inner join product_types on product_types.id = brands.product_type_id "
				+ "where brands.id like ? or brands.code like ? or brands.name like ?  or product_types.name like ? "
				+ "order by id "
				+ "limit ?, ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, "%" + paging.getQuery() + "%");
		stmt.setString(2, "%" + paging.getQuery() + "%");
		stmt.setString(3, "%" + paging.getQuery() + "%");
		stmt.setString(4, "%" + paging.getQuery() + "%");
		stmt.setInt(5, start);
		stmt.setInt(6, paging.getPerPage());

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			item = new Brand(rs.getInt("id"), rs.getString("code"), rs.getString("name"), 
					rs.getBoolean("active"), rs.getInt("product_type_id"), rs.getString("product_type_name"), 
					rs.getDate("created_at"), rs.getDate("updated_at"));
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}
	
	public static List<Brand> getAllByActived() throws Exception {
		List<Brand> list = new ArrayList<>();
		Brand item = null;

		Connection conn = DBContext.getConnection();
		String sql = "select brands.id as id, brands.code as code, brands.name as name,"
				+ "product_types.id as product_type_id, product_types.name as product_type_name, brands.active as active, "
				+ "brands.created_at as created_at, brands.updated_at as updated_at "
				+ "from brands "
				+ "inner join product_types on product_types.id = brands.product_type_id "
				+ "where brands.active = 1 "
				+ "order by brands.id ";

		PreparedStatement stmt = conn.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			item = new Brand(rs.getInt("id"), rs.getString("code"), rs.getString("name"), 
					rs.getBoolean("active"), rs.getInt("product_type_id"), rs.getString("product_type_name"), 
					rs.getDate("created_at"), rs.getDate("updated_at"));
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}
	
	public static List<Brand> getByProductTypeId(int productTypeId) throws Exception {
		List<Brand> list = new ArrayList<>();
		Brand item = null;

		Connection conn = DBContext.getConnection();
		String sql = "select brands.id as id, brands.code as code, brands.name as name,"
				+ "product_types.id as product_type_id, product_types.name as product_type_name, brands.active as active, "
				+ "brands.created_at as created_at, brands.updated_at as updated_at "
				+ "from brands "
				+ "inner join product_types on product_types.id = brands.product_type_id "
				+ "where brands.product_type_id = ? and brands.active = 1 "
				+ "order by brands.id ";

		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setInt(1, productTypeId);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			item = new Brand(rs.getInt("id"), rs.getString("code"), rs.getString("name"), 
					rs.getBoolean("active"), rs.getInt("product_type_id"), rs.getString("product_type_name"), 
					rs.getDate("created_at"), rs.getDate("updated_at"));
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}

	public static int create(Brand item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "insert into brands (code, name, active, product_type_id, created_at) values(?, ?, ?, ?, ?)";

		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, item.getCode());
		stmt.setString(2, item.getName());
		stmt.setBoolean(3, item.isActive());
		stmt.setInt(4, item.getProductTypeId());
		java.util.Date date = new Date();
		stmt.setTimestamp(5, new java.sql.Timestamp(date.getTime()));

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

	public static int update(Brand item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "update brands set product_type_id = ?, name = ?, active = ?, updated_at = ? where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, item.getProductTypeId());
		stmt.setString(2, item.getName());
		stmt.setBoolean(3, item.isActive());
		java.util.Date date = new Date();
		stmt.setTimestamp(4, new java.sql.Timestamp(date.getTime()));
		stmt.setInt(5, item.getId());

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static int delete(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "delete from brands where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static int disable(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "update brands set active = 0, updated_at = ? where id = ?";

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
		String sql = "select count(*) as count from brands where id = ?";

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
		String sql = "select count(*) as count from brands where code = ?";

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
