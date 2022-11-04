package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import context.DBContext;
import model.Account;

public class AccountDAO {
	public static Account getByEmailAndPassword(String email, String password) {
		Account item = null;
		
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "select users.id as id, users.email as email, users.username as username, "
					+ "users.first_name as first_name, users.last_name as last_name, "
					+ "users.role_id as role_id, roles.name as role_name, files.name as file_name "
					+ "from users "
					+ "left join roles on roles.id = users.role_id "
					+ "left join files on files.model_id = users.id and files.model_name = 'User' "
					+ "where email = ? and password = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				Integer id = rs.getInt("id");
				String username = rs.getString("username");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Integer roleId = rs.getInt("role_id");
				String roleName = rs.getString("role_name");
				String fileName = rs.getString("file_name");
				
				item = new Account(id, email, username, firstName, lastName, roleId, roleName, fileName);
			}
			
			stmt.close();
			conn.close();
			
			return item;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static Account getByUsernameAndPassword(String username, String password) {
		Account item = null;
		
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "select users.id as id, users.email as email, users.username as username, "
					+ "users.first_name as first_name, users.last_name as last_name, "
					+ "users.role_id as role_id, roles.name as role_name, files.name as file_name "
					+ "from users "
					+ "left join roles on roles.id = users.role_id "
					+ "left join files on files.model_id = users.id and files.model_name = 'User' "
					+ "where username = ? and password = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				Integer id = rs.getInt("id");
				String email = rs.getString("email");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Integer roleId = rs.getInt("role_id");
				String roleName = rs.getString("role_name");
				String fileName = rs.getString("file_name");
				
				item = new Account(id, email, username, firstName, lastName, roleId, roleName, fileName);
			}
			
			stmt.close();
			conn.close();
			
			return item;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
}
