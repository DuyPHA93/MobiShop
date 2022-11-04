package model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import dao.ProductDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Product {
	private Integer id;
	private String code;
	private String name;
	private String description;
	private Double price;
	private Integer productTypeId;
	private String productTypeName;
	private Integer brandId;
	private String brandName;
	private Integer quantity;
	private boolean active;
	private String fileName;
	private Date createdAt;
	private Date updatedAt;
	
	public Product() {}

	public Product(Integer id, String code, String name, String description, Double price, Integer productTypeId,
			String productTypeName, Integer brandId, String brandName, Integer quantity, boolean active, Date createdAt,
			Date updatedAt) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.price = price;
		this.productTypeId = productTypeId;
		this.productTypeName = productTypeName;
		this.brandId = brandId;
		this.brandName = brandName;
		this.quantity = quantity;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Product(Integer id, String code, String name, Double price, String fileName) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.price = price;
		this.fileName = fileName;
	}
	
	public Product(HttpServletRequest request, HttpServletResponse response) {
		try {
			String strId = request.getParameter("id");
			this.id = strId == null || strId.equals("")  ? null : Integer.parseInt(strId);
			this.code = request.getParameter("code");
			this.name = request.getParameter("name");
			this.description = request.getParameter("description");
			String strPrice = request.getParameter("price");
			this.price = strPrice == null || strPrice.equals("")  ? 0 : Double.parseDouble(strPrice);
			String strProductTypeId = request.getParameter("productType");
			this.productTypeId = strProductTypeId == null || strProductTypeId.equals("") ? -1 : Integer.parseInt(strProductTypeId);
			String strBrandId = request.getParameter("brand");
			this.brandId = strBrandId == null || strBrandId.equals("") ? -1 : Integer.parseInt(strBrandId);
			String strQuantity = request.getParameter("quantity");
			this.quantity = strQuantity == null || strQuantity.equals("") ? 0 : Integer.parseInt(strQuantity);
			this.active = request.getParameter("active") == null ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
	
	public List<Product> getRandomProducts() {
		return ProductDAO.getRandomProducts();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
