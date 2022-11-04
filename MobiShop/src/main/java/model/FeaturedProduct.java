package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FeaturedProduct {
	private Integer id;
	private Integer productId;
	private String productCode;
	private String productName;
	private Date startDate;
	private Date endDate;
	private String fileName;
	private boolean active;
	private Date createdAt;
	private Date updatedAt;
	
	public FeaturedProduct(Integer id, Integer productId, String productCode, String productName, Date startDate,
			Date endDate, boolean active, Date createdAt, Date updatedAt) {
		this.id = id;
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public FeaturedProduct(HttpServletRequest request, HttpServletResponse response) {
		String strId = request.getParameter("id");
		this.id = strId == null || strId.equals("")  ? null : Integer.parseInt(strId);
		String strProductId = request.getParameter("product");
		this.productId = strProductId == null || strProductId.equals("")  ? -1 : Integer.parseInt(strProductId);
		this.productCode = request.getParameter("productCode");
		this.productName = request.getParameter("productName");
		
		try {
			this.startDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("startDate"));
			this.endDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("endDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean getActive() {
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
