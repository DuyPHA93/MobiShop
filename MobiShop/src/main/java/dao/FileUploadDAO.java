package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import context.DBContext;
import model.FileUpload;
import model.ProductType;

public class FileUploadDAO {
	
	public static FileUpload read(int modelId, String modelName) {
		FileUpload item = null;
		try {
			Connection conn = DBContext.getConnection();
			String sql = "select * from files where model_id = ? and model_name = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, modelId);
			stmt.setString(2, modelName);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				String path = rs.getString("path");
				String name = rs.getString("name");
				String extension = rs.getString("extension");
				Date createdAt = rs.getTimestamp("created_at");
				Date updatedAt = rs.getTimestamp("updated_at");

				item = new FileUpload(id, modelId, modelName, path, name, extension, createdAt, updatedAt);
			}

			stmt.close();
			conn.close();

			return item;
		} catch (Exception e) {
			e.printStackTrace();
			return item;
		}
	}
	
	public static int create(FileUpload item) {
		int result = 0;
		try {
			Connection conn = DBContext.getConnection();
			String sql = "insert into files (model_id, model_name, path, name, extension, created_at) values(?, ?, ?, ?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, item.getModelId());
			stmt.setString(2, item.getModelName());
			stmt.setString(3, item.getPath());
			stmt.setString(4, item.getName());
			stmt.setString(5, item.getExtension());
			java.util.Date date = new Date();
			stmt.setTimestamp(6, new java.sql.Timestamp(date.getTime()));

			result = stmt.executeUpdate();

			stmt.close();
			conn.close();

			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static int update(FileUpload item) {
		int result = 0;
		try {
			Connection conn = DBContext.getConnection();
			String sql = "update files "
					+ "set path = ?, name = ?, extension = ?, updated_at = ? "
					+ "where model_id = ? and model_name = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, item.getPath());
			stmt.setString(2, item.getName());
			stmt.setString(3, item.getExtension());
			java.util.Date date = new Date();
			stmt.setTimestamp(4, new java.sql.Timestamp(date.getTime()));
			stmt.setInt(5, item.getModelId());
			stmt.setString(6, item.getModelName());

			result = stmt.executeUpdate();

			stmt.close();
			conn.close();

			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static int delete(int modelId, String modelName) {
		int result = 0;
		try {
			Connection conn = DBContext.getConnection();
			String sql = "delete from files where model_id = ? and model_name = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, modelId);
			stmt.setString(2, modelName);

			result = stmt.executeUpdate();

			stmt.close();
			conn.close();

			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
