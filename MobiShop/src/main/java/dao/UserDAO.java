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
import model.PagingQuery;
import model.ProductType;
import model.User;

public class UserDAO {

	public static User findById(int id) throws Exception {
		User item = null;

		Connection conn = DBContext.getConnection();
		String sql = "select * from users where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			String email = rs.getString("email");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String phone = rs.getString("phone");
			Integer roleId = rs.getInt("role_id");
			boolean active = Integer.parseInt(rs.getString("active")) == 1 ? true : false;
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new User(id, email, username, password, null, firstName, lastName, phone, roleId, null, active, createdAt, updatedAt);
		}

		stmt.close();
		conn.close();

		return item;
	}
	
	public static int getTotalRecords(PagingQuery paging) {
		try {
			Connection conn = DBContext.getConnection();
			String sql = "select count(*) as count "
					+ "from users "
					+ "left join roles on users.role_id = roles.id "
					+ "left join files on files.model_id = users.id and files.model_name ='User' "
					+ "where users.id like ? or users.first_name like ? or users.last_name like ? or users.email like ? "
					+ "or users.phone like ? or roles.name like ? ";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, "%" + paging.getQuery() + "%");
			stmt.setString(2, "%" + paging.getQuery() + "%");
			stmt.setString(3, "%" + paging.getQuery() + "%");
			stmt.setString(4, "%" + paging.getQuery() + "%");
			stmt.setString(5, "%" + paging.getQuery() + "%");
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

	public static List<User> search(PagingQuery paging) throws Exception {
		List<User> list = new ArrayList<>();
		User item = null;
		
		int start = (paging.getCurrentPage() - 1) * paging.getPerPage();

		Connection conn = DBContext.getConnection();
		String sql = "select users.id as id, users.first_name as first_name, users.last_name as last_name, users.username as username, users.password as password, "
				+ "users.email as email, users.phone as phone, users.role_id as role_id, roles.name as role_name, "
				+ "files.name as file_name, users.active as active, users.created_at as created_at, users.updated_at as updated_at "
				+ "from users "
				+ "left join roles on users.role_id = roles.id "
				+ "left join files on files.model_id = users.id and files.model_name ='User' "
				+ "where users.id like ? or users.first_name like ? or users.last_name like ? or users.email like ? "
				+ "or users.phone like ? or roles.name like ? "
				+ "order by users.id "
				+ "limit ?, ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, "%" + paging.getQuery() + "%");
		stmt.setString(2, "%" + paging.getQuery() + "%");
		stmt.setString(3, "%" + paging.getQuery() + "%");
		stmt.setString(4, "%" + paging.getQuery() + "%");
		stmt.setString(5, "%" + paging.getQuery() + "%");
		stmt.setString(6, "%" + paging.getQuery() + "%");
		stmt.setInt(7, start);
		stmt.setInt(8, paging.getPerPage());

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Integer id = rs.getInt("id");
			String email = rs.getString("email");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String phone = rs.getString("phone");
			Integer roleId = rs.getInt("role_id");
			String roleName = rs.getString("role_name");
			boolean active = Integer.parseInt(rs.getString("active")) == 1 ? true : false;
			Date createdAt = rs.getTimestamp("created_at");
			Date updatedAt = rs.getTimestamp("updated_at");

			item = new User(id, email, username, password, null, firstName, lastName, phone, roleId, roleName, active, createdAt, updatedAt);
			item.setFileName(rs.getString("file_name"));
			list.add(item);
		}

		stmt.close();
		conn.close();

		return list;
	}

	public static int create(User item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "insert into users (username, email, password, first_name, last_name, phone, role_id, active, created_at) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, item.getUsername());
		stmt.setString(2, item.getEmail());
		stmt.setString(3, item.getPassword());
		stmt.setString(4, item.getFirstName());
		stmt.setString(5, item.getLastName());
		stmt.setString(6, item.getPhone());
		stmt.setInt(7, item.getRoleId());
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

	public static int update(User item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "update users "
				+ "set password = ?, first_name = ?, last_name = ?, phone = ?, role_id = ?, active = ?, updated_at = ? "
				+ "where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, item.getPassword());
		stmt.setString(2, item.getFirstName());
		stmt.setString(3, item.getLastName());
		stmt.setString(4, item.getPhone());
		stmt.setInt(5, item.getRoleId());
		stmt.setBoolean(6, item.isActive());
		java.util.Date date = new Date();
		stmt.setTimestamp(7, new java.sql.Timestamp(date.getTime()));
		stmt.setInt(8, item.getId());

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static int updateWithoutPassword(User item) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "update users "
				+ "set first_name = ?, last_name = ?, phone = ?, role_id = ?, active = ?, updated_at = ? "
				+ "where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, item.getFirstName());
		stmt.setString(2, item.getLastName());
		stmt.setString(3, item.getPhone());
		stmt.setInt(4, item.getRoleId());
		stmt.setBoolean(5, item.isActive());
		java.util.Date date = new Date();
		stmt.setTimestamp(6, new java.sql.Timestamp(date.getTime()));
		stmt.setInt(7, item.getId());

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static int delete(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "delete from users where id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, id);

		int result = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return result;
	}
	
	public static int disable(int id) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "update users set active = 0, updated_at = ? where id = ?";

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
		String sql = "select count(*) as count from users where id = ?";

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

	public static boolean checkExistsUsername(String username) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "select count(*) as count from users where username = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, username);

		ResultSet rs = stmt.executeQuery();

		int count = 0;

		if (rs.next()) {
			count = rs.getInt("count");
		}

		stmt.close();
		conn.close();

		return count == 0 ? false : true;
	}
	
	public static boolean checkExistsEmail(String email) throws Exception {
		Connection conn = DBContext.getConnection();
		String sql = "select count(*) as count from users where email = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, email);

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
