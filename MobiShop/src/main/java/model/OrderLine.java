package model;

import java.util.Date;

public class OrderLine {
	private String orderNo;
	private Integer productId;
	private String productCode;
	private String productName;
	private Integer productQuantity;
	private Double productPrice;
	private Double productTotalPrice;
	private String fileName;
	private String status;
	private Date createdAt;
	private Date updatedAt;

	public OrderLine(Integer productId, String orderNo, String productCode, String productName, Integer productQuantity,
			Double productPrice, Double productTotalPrice, String fileName, String status,
			Date createdAt, Date updatedAt) {
		this.orderNo = orderNo;
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.productQuantity = productQuantity;
		this.productPrice = productPrice;
		this.productTotalPrice = productTotalPrice;
		this.fileName = fileName;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public OrderLine(String orderNo, String status, Product product) {
		this.orderNo = orderNo;
		this.productId = product.getId();
		this.productCode = product.getCode();
		this.productName = product.getName();
		this.productQuantity = product.getQuantity();
		this.productPrice = product.getPrice();
		this.productTotalPrice = product.getPrice() * product.getQuantity();
		this.fileName = product.getFileName();
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Double getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(Double productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
