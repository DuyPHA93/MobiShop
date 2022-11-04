package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import context.DBContext;
import model.CategorySP;

public class CategoryDAO {
	public static List<CategorySP> getAll() {
		List<CategorySP> list = new ArrayList<>();
		
		try {
			Connection conn = DBContext.getConnection();
			
			String sql = "select f.name as file_name, p.id as product_type_id, p.name as product_type_name, "
					+ "b.id as brand_id, b.name as brand_name "
					+ "from product_types p "
					+ "left join brands b on p.id = b.product_type_id and b.active = 1 "
					+ "left join files f on f.model_id = p.id and f.model_name = 'ProductType' "
					+ "where p.active = 1 "
					+ "order by p.id, b.id";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String fileName = rs.getString("file_name");
				Integer productTypeId = rs.getInt("product_type_id");
				String productTypeName = rs.getString("product_type_name");
				Integer brandId = rs.getInt("brand_id");
				String brandName = rs.getString("brand_name");
				
				CategorySP item = new CategorySP(fileName, productTypeId, productTypeName, brandId, brandName);
				list.add(item);
			}
			
			stmt.close();
			conn.close();
			
			return list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
