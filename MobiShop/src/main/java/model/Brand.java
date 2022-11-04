package model;

import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Brand {
	private Integer id;
	private String code;
	private String name;
	private boolean active;
	private Integer productTypeId;
	private String productTypeName;
	private Date createdAt;
	private Date updatedAt;
	
	public Brand(Integer id, String code, String name, boolean active, Integer productTypeId, String productTypeName,
			Date createdAt, Date updatedAt) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.active = active;
		this.productTypeId = productTypeId;
		this.productTypeName = productTypeName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Brand(HttpServletRequest request, HttpServletResponse response) {
		String strId = request.getParameter("id");
		this.id = strId == null || strId.equals("")  ? null : Integer.parseInt(strId);
		this.code = request.getParameter("code");
		this.name = request.getParameter("name");
		this.active = request.getParameter("active") == null ? false : true;
		String strProductTypeId = request.getParameter("productTypeId");
		this.productTypeId = strProductTypeId == null || strProductTypeId.equals("")  ? null : Integer.parseInt(strProductTypeId);
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	
	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
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
