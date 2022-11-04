package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductType {
	private int id;
	private String code;
	private String name;
	private String fileName;
	private boolean active;
	private Date createdAt;
	private Date updatedAt;

	public ProductType() {

	}

	public ProductType(int id, String code, String name, boolean active, Date createdAt, Date updatedAt) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public ProductType(HttpServletRequest request, HttpServletResponse response) {
		try {
			String strId = request.getParameter("id");
			this.id = strId == null || strId.equals("")  ? -1 : Integer.parseInt(strId);
			this.code = request.getParameter("code");
			this.name = request.getParameter("name");
			this.active = request.getParameter("active") == null ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
