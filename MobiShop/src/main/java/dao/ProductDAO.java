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
import model.HomeProductSP;
import model.PagingQuery;
import model.Product;
import model.ProductType;

public class ProductDAO {

	public static Product findById(int id) throws Exception {
		Product item = null;

		Connection conn = DBContext.getConnection();
		String sql = "select p.id as id, p.code as code, p.name as name, p.description as description, "
				+ "p.price as price, p.product_type_id as product_type_id, pt.name as product_type_name, "
				+ "p.brand_id as brand_id, b.name as brand_name, p.quantity as quantity, p.active as active, "
				+ "f.name as file_name, p.created_at as created_at, p.updated_at as updated_at "
				+ "from products p "
				+ "left join product_types pt on p.product_type_id = pt.id "
				+ "left join brands b on p.brand_id = b.id "
				+ "left join files f on f.model_id = p.id and f.model_name = 'Product' "
				+ "where p.id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			String code = rs.getString("code");
			String name = rs.getString("name");
			String description = rs.getString("description");
			double price = rs.getDouble("price");
			int productTypeId = rs.getInt("product_type_id");
			String productTypeName = rs.getString("product_type_name");
			int brandId = rs.getInt("brand_id");
			String brandName = rs.getString("brand_name");
			int quantity = rs.getInt("quantity");
			boolean active = Integer.parseInt(rs.getString("active")) == 1 ? true : false;
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new Product(id, code, name, description, price, productTypeId, 
					productTypeName, brandId, brandName, quantity, active, createdAt, updatedAt);
			item.setFileName(rs.getString("file_name"));
		}

		stmt.close();
		conn.close();

		return item;
	}
	
	public static int getTotalRecords(PagingQuery paging) {
		try {
			Connection conn = DBContext.getConnection();
			String sql = "select count(*) as count "
					+ "from products "
					+ "left join product_types on products.product_type_id = product_types.id "
					+ "where products.id like ? or products.code like ? or products.name like ? "
					+ "or products.price = ? or products.quantity = ? or product_types.name like ? ";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, "%" + paging.getQuery() + "%");
			stmt.setString(2, "%" + paging.getQuery() + "%");
			stmt.setString(3, "%" + paging.getQuery() + "%");
			stmt.setString(4, paging.getQuery());
			stmt.setString(5, paging.getQuery());
			stmt.setString(6, "%" + paging.getQuery() + "%");

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
	public static List<Product> search(PagingQuery paging) throws Exception {
		List<Product> list = new ArrayList<>();
		Product item = null;
		
		int start = (paging.getCurrentPage() - 1) * paging.getPerPage();

		Connection conn = DBContext.getConnection();
		String sql ="select p.id as id, p.code as code, p.name as name, p.description as description, "
				+ "p.price as price, p.product_type_id as product_type_id, pt.name as product_type_name, "
				+ "p.brand_id as brand_id, b.name as brand_name, p.quantity as quantity, p.active as active, "
				+ "f.name as file_name, p.created_at as created_at, p.updated_at as updated_at "
				+ "from products p "
				+ "left join product_types pt on p.product_type_id = pt.id "
				+ "left join brands b on p.brand_id = b.id "
				+ "left join files f on f.model_id = p.id and f.model_name ='Product' "
				+ "where p.id like ? or p.code like ? or p.name like ? "
				+ "or p.price = ? or p.quantity = ? or pt.name like ? "
				+ "order by id "
				+ "limit ?, ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, "%" + paging.getQuery() + "%");
		stmt.setString(2, "%" + paging.getQuery() + "%");
		stmt.setString(3, "%" + paging.getQuery() + "%");
		stmt.setString(4, paging.getQuery());
		stmt.setString(5, paging.getQuery());
		stmt.setString(6, "%" + paging.getQuery() + "%");
		stmt.setInt(7, start);
		stmt.setInt(8, paging.getPerPage());

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("id");
			String code = rs.getString("code");
			String name = rs.getString("name");
			String description = rs.getString("description");
			double price = rs.getDouble("price");
			int productTypeId = rs.getInt("product_type_id");
			String productTypeName = rs.getString("product_type_name");
			int brandId = rs.getInt("brand_id");
			String brandName = rs.getString("brand_name");
			int quantity = rs.getInt("quantity");
			boolean active = Integer.parseInt(rs.getString("active")) == 1 ? true : false;
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new Product(id, code, name, description, price, productTypeId, 
					productTypeName, brandId, brandName, quantity, active, createdAt, updatedAt);
			item.setFileName(rs.getString("file_name"));
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}
	
	// Search Home Page
	public static List<Product> searchProducts(PagingQuery paging) throws Exception {
		List<Product> list = new ArrayList<>();
		Product item = null;
		String conditionQuery = "";
		
		if (paging.getQuery() != null) {
			conditionQuery = "where p.name like ? ";
		}

		Connection conn = DBContext.getConnection();
		String sql ="select p.id as id, p.code as code, p.name as name, "
				+ "p.price as price, f.name as file_name "
				+ "from products p "
				+ "left join files f on f.model_id = p.id and f.model_name ='Product' "
				+ conditionQuery;

		PreparedStatement stmt = conn.prepareStatement(sql);

		if (paging.getQuery() != null) {
			stmt.setString(1, "%" + paging.getQuery() + "%");
		}
		
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("id");
			String code = rs.getString("code");
			String name = rs.getString("name");
			double price = rs.getDouble("price");
			String fileName = rs.getString("file_name");

			item = new Product(id, code, name, price, fileName);
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}
	
	public static List<Product> searchByCategory(PagingQuery paging, boolean isBrand, int categoryId) throws Exception {
		List<Product> list = new ArrayList<>();
		Product item = null;
		String conditionQuery = "";
		
		if (isBrand) {
			conditionQuery = "and p.brand_id = ? ";
		} else {
			conditionQuery = "and p.product_type_id = ? ";
		}

		Connection conn = DBContext.getConnection();
		String sql ="select p.id as id, p.code as code, p.name as name, "
				+ "p.price as price, f.name as file_name "
				+ "from products p "
				+ "left join files f on f.model_id = p.id and f.model_name ='Product' "
				+ "where 1=1 " + conditionQuery;

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, categoryId);
		
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("id");
			String code = rs.getString("code");
			String name = rs.getString("name");
			double price = rs.getDouble("price");
			String fileName = rs.getString("file_name");

			item = new Product(id, code, name, price, fileName);
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}
	
	public static List<Product> getAllByActived() throws Exception {
		List<Product> list = new ArrayList<>();
		Product item = null;

		Connection conn = DBContext.getConnection();
		String sql ="select p.id as id, p.code as code, p.name as name, p.description as description, "
				+ "p.price as price, p.product_type_id as product_type_id, pt.name as product_type_name, "
				+ "p.brand_id as brand_id, b.name as brand_name, p.quantity as quantity, p.active as active, "
				+ "p.created_at as created_at, p.updated_at as updated_at "
				+ "from products p "
				+ "left join product_types pt on p.product_type_id = pt.id "
				+ "left join brands b on p.brand_id = b.id "
				+ "where p.active = 1 "
				+ "order by p.id ";

		PreparedStatement stmt = conn.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("id");
			String code = rs.getString("code");
			String name = rs.getString("name");
			String description = rs.getString("description");
			double price = rs.getDouble("price");
			int productTypeId = rs.getInt("product_type_id");
			String productTypeName = rs.getString("product_type_name");
			int brandId = rs.getInt("brand_id");
			String brandName = rs.getString("brand_name");
			int quantity = rs.getInt("quantity");
			boolean active = Integer.parseInt(rs.getString("active")) == 1 ? true : false;
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new Product(id, code, name, description, price, productTypeId, 
					productTypeName, brandId, brandName, quantity, active, createdAt, updatedAt);
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}
	
	public static List<Product> getFeaturedProducts() {
		List<Product> list = new ArrayList<>();
		Product item = null;

		try {
			Connection conn = DBContext.getConnection();
			String sql ="select p.id as id, p.code as code, p.name as name, p.price as price, fl.name as file_name "
					+ "from products p "
					+ "inner join featured_products f on p.id = f.product_id "
					+ "left join files fl on fl.model_id = p.id and fl.model_name = 'Product'"
					+ "where (CURDATE() between f.start_date and f.end_date) and p.active = 1 "
					+ "order by f.created_at desc ";

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String code = rs.getString("code");
				String name = rs.getString("name");
				String fileName = rs.getString("file_name");
				double price = rs.getDouble("price");
				item = new Product(id, code, name, price, fileName);
				list.add(item);
			}

			stmt.close();
			conn.close();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}
	
	public static List<Product> getLastProducts() {
		List<Product> list = new ArrayList<>();
		Product item = null;

		try {
			Connection conn = DBContext.getConnection();
			String sql ="select p.id as id, p.code as code, p.name as name, p.price as price, fl.name as file_name "
					+ "from products p "
					+ "left join files fl on fl.model_id = p.id and fl.model_name = 'Product'"
					+ "where  p.active = 1 "
					+ "order by p.created_at desc "
					+ "limit 5";

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String code = rs.getString("code");
				String name = rs.getString("name");
				String fileName = rs.getString("file_name");
				double price = rs.getDouble("price");
				item = new Product(id, code, name, price, fileName);
				list.add(item);
			}

			stmt.close();
			conn.close();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}
	
	public static List<Product> getPromotionProducts() {
		List<Product> list = new ArrayList<>();
		Product item = null;

		try {
			Connection conn = DBContext.getConnection();
			String sql ="select p.id as id, p.code as code, p.name as name, p.price as price, fl.name as file_name "
					+ "from products p "
					+ "left join files fl on fl.model_id = p.id and fl.model_name = 'Product'"
					+ "where  p.active = 1 "
					+ "limit 5";

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String code = rs.getString("code");
				String name = rs.getString("name");
				String fileName = rs.getString("file_name");
				double price = rs.getDouble("price");
				item = new Product(id, code, name, price, fileName);
				list.add(item);
			}

			stmt.close();
			conn.close();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}
	
	public static List<HomeProductSP> getHomeProductsSP() {
		List<HomeProductSP> list = new ArrayList<>();
		HomeProductSP item = null;

		try {
			Connection conn = DBContext.getConnection();
			String sql ="select p.id as id, p.code as code, p.name as name, p.price as price, fl.name as file_name, "
					+ "pt.code as product_type_code, pt.name as product_type_name, "
					+ "b.code as brand_code, b.name as brand_name "
					+ "from products p "
					+ "inner join product_types pt on pt.id = p.product_type_id "
					+ "inner join brands b on b.id = p.brand_id "
					+ "left join files fl on fl.model_id = p.id and fl.model_name = 'Product'"
					+ "where  p.active = 1 ";

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String productTypeCode = rs.getString("product_type_code");
				String productTypeName = rs.getString("product_type_name");
				String brandCode = rs.getString("brand_code");
				String brandName = rs.getString("brand_name");
				int id = rs.getInt("id");
				String code = rs.getString("code");
				String name = rs.getString("name");
				String fileName = rs.getString("file_name");
				double price = rs.getDouble("price");
				item = new HomeProductSP(productTypeCode, productTypeName, brandCode, brandName, id, code, name, price, fileName);
				list.add(item);
			}

			stmt.close();
			conn.close();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}
	
	public static List<Product> getRandomProducts() {
		List<Product> list = new ArrayList<>();
		Product item = null;

		try {
			Connection conn = DBContext.getConnection();
			String sql ="select p.id as id, p.code as code, p.name as name, p.price as price, fl.name as file_name "
					+ "from products p "
					+ "left join files fl on fl.model_id = p.id and fl.model_name = 'Product' "
					+ "where  p.active = 1 "
					+ "order by rand() "
					+ "limit 6";

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String code = rs.getString("code");
				String name = rs.getString("name");
				String fileName = rs.getString("file_name");
				double price = rs.getDouble("price");
				item = new Product(id, code, name, price, fileName);
				list.add(item);
			}

			stmt.close();
			conn.close();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public static int create(Product item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "insert into products (code, name, description, price, product_type_id, "
				+ "brand_id, quantity, active, created_at) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, item.getCode());
		stmt.setString(2, item.getName());
		stmt.setString(3, item.getDescription());
		stmt.setDouble(4, item.getPrice());
		stmt.setInt(5, item.getProductTypeId());
		stmt.setInt(6, item.getBrandId());
		stmt.setInt(7, item.getQuantity());
		stmt.setBoolean(8, item.isActive());
		java.util.Date date = new Date();
		stmt.setTimestamp(9, new java.sql.Timestamp(date.getTime()));

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

	public static int update(Product item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "update products set name = ?, description = ?, price = ?, product_type_id = ?, "
				+ "brand_id = ?, quantity = ?, active = ?, updated_at = ? "
				+ "where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, item.getName());
		stmt.setString(2, item.getDescription());
		stmt.setDouble(3, item.getPrice());
		stmt.setInt(4, item.getProductTypeId());
		stmt.setInt(5, item.getBrandId());
		stmt.setInt(6, item.getQuantity());
		stmt.setBoolean(7, item.isActive());
		java.util.Date date = new Date();
		stmt.setTimestamp(8, new java.sql.Timestamp(date.getTime()));
		stmt.setInt(9, item.getId());

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static int delete(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "delete from products where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static int disable(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "update products set active = 0, updated_at = ? where id = ?";

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
		String sql = "select count(*) as count from products where id = ?";

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
		String sql = "select count(*) as count from products where code = ?";

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
