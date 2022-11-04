package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import context.DBContext;
import model.Brand;
import model.FeaturedProduct;
import model.PagingQuery;

public class FeaturedProductDAO {
	public static FeaturedProduct findById(int id) throws Exception {
		FeaturedProduct item = null;

		Connection conn = DBContext.getConnection();
		String sql = "select featured_products.product_id as product_id, "
				+ "products.code as product_code, products.name as product_name, "
				+ "featured_products.start_date as start_date, featured_products.end_date as end_date, "
				+ "featured_products.created_at as created_at, featured_products.updated_at as updated_at, "
				+ "case when CURDATE() between featured_products.start_date and featured_products.end_date then true "
				+ "else false end as active "
				+ "from featured_products "
				+ "inner join products on featured_products.product_id = products.id "
				+ "where featured_products.id = ? ";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			Integer productId = rs.getInt("product_id");
			String productCode = rs.getString("product_code");
			String productName = rs.getString("product_name");
			Date startDate = rs.getTimestamp("start_date");
			Date endDate = rs.getTimestamp("end_date");
			boolean active = rs.getBoolean("active");
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new FeaturedProduct(id, productId, productCode, productName, startDate, endDate, active, createdAt, updatedAt);
		}

		stmt.close();
		conn.close();

		return item;
	}
	
	public static int getTotalRecords(PagingQuery paging) {
		try {
			
			String conditionQuery = "1=1 ";
			
			if (paging.getQuery().equals("") || paging.getQuery().equals("1") ) {
				conditionQuery = "CURDATE() between featured_products.start_date and featured_products.end_date ";
			} else if (paging.getQuery().equals("2")) {
				conditionQuery = "CURDATE() not between featured_products.start_date and featured_products.end_date ";
			} else if (paging.getQuery().equals("3")) {
				conditionQuery = "CURDATE() > featured_products.end_date ";
			}
			
			Connection conn = DBContext.getConnection();
			String sql = "select count(*) as count "
					+ "from featured_products "
					+ "inner join products on products.id = featured_products.product_id "
					+ "where " + conditionQuery;

			PreparedStatement stmt = conn.prepareStatement(sql);

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

	public static List<FeaturedProduct> search(PagingQuery paging) throws Exception {
		List<FeaturedProduct> list = new ArrayList<>();
		FeaturedProduct item = null;
		
		int start = (paging.getCurrentPage() - 1) * paging.getPerPage();
		
		String conditionQuery = "1=1 ";
		
		if (paging.getQuery().equals("") || paging.getQuery().equals("1") ) {
			conditionQuery = "CURDATE() between featured_products.start_date and featured_products.end_date ";
		} else if (paging.getQuery().equals("2")) {
			conditionQuery = "CURDATE() not between featured_products.start_date and featured_products.end_date ";
		} else if (paging.getQuery().equals("3")) {
			conditionQuery = "CURDATE() > featured_products.end_date ";
		}

		Connection conn = DBContext.getConnection();
		String sql = "select featured_products.id as id, products.id as product_id, "
				+ "products.code as product_code, products.name as product_name, files.name as file_name, "
				+ "featured_products.start_date as start_date, featured_products.end_date as end_date, "
				+ "featured_products.created_at as created_at, featured_products.updated_at as updated_at, "
				+ "case when CURDATE() between featured_products.start_date and featured_products.end_date then true "
				+ "else false end as active "
				+ "from featured_products "
				+ "inner join products on products.id = featured_products.product_id "
				+ "left join files on files.model_id = featured_products.product_id and files.model_name ='Product' "
				+ "where " + conditionQuery + " "
				+ "order by featured_products.product_id, featured_products.start_date "
				+ "limit ?, ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, start);
		stmt.setInt(2, paging.getPerPage());

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Integer id = rs.getInt("id");
			Integer productId = rs.getInt("product_id");
			String productCode = rs.getString("product_code");
			String productName = rs.getString("product_name");
			Date startDate = rs.getTimestamp("start_date");
			Date endDate = rs.getTimestamp("end_date");
			boolean active = rs.getBoolean("active");
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new FeaturedProduct(id, productId, productCode, productName, startDate, endDate, active, createdAt, updatedAt);
			item.setFileName(rs.getString("file_name"));
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}

	public static int create(FeaturedProduct item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "insert into featured_products (product_id, start_date, end_date, created_at) values(?, ?, ?, ?)";

		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stmt.setInt(1, item.getProductId());
		stmt.setDate(2, new java.sql.Date(item.getStartDate().getTime()));
		stmt.setDate(3, new java.sql.Date(item.getEndDate().getTime()));
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

	public static int update(FeaturedProduct item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "update featured_products set end_date = ?, updated_at = ? where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setDate(1, new java.sql.Date(item.getEndDate().getTime()));
		java.util.Date date = new Date();
		stmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
		stmt.setInt(3, item.getId());

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static int delete(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "delete from featured_products where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static boolean checkExists(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "select count(*) as count from featured_products where id = ?";

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

	public static boolean checkExists(int productId, Date startDate, Date endDate, Integer excludeId) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "select count(*) as count "
				+ "from featured_products "
				+ "where id <> ? and product_id = ? and "
				+ "((? between start_date and end_date) or (? between start_date and end_date) or "
				+ "(start_date between ? and ?) or (end_date between ? and ?)) "
				+ "order by start_date";

		PreparedStatement stmt = conn.prepareStatement(sql);
		
		String pattern = "yyyyMMdd";
		DateFormat df = new SimpleDateFormat(pattern);

		stmt.setInt(1, excludeId);
		stmt.setInt(2, productId);
		stmt.setString(3, df.format(startDate));
		stmt.setString(4, df.format(endDate));
		stmt.setString(5, df.format(startDate));
		stmt.setString(6, df.format(endDate));
		stmt.setString(7, df.format(startDate));
		stmt.setString(8, df.format(endDate));

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
