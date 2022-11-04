package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import dao.OrderDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Order {
	private Integer id;
	private String orderNo;
	private Date orderDate;
	private Integer personOrderId;
	private String fileName;
	private String contactFirstName;
	private String contactLastName;
	private String contactEmail;
	private String contactPhone;
	private String contactAddress;
	private String note;
	private String status;
	private String reasonForCancelOrder;
	private Integer totalQuantity;
	private Double totalPrice;
	private String warehousePickup;
	private String shippingCode;
	private String transporter;
	private String shippingStatus;
	private Double totalWeight;
	private Date deliveryDate;
	private Date deliveredDate;
	private Date receiveOrderDate;
	private Date confirmCompleteOrderDate;
	private Date createdAt;
	private Date updatedAt;

	public Order() {
	}

	public Order(Integer id, String orderNo, Date orderDate, Integer personOrderId, String contactFirstName,
			String contactLastName, String contactEmail, String contactPhone, String contactAddress, String note,
			String status, String reasonForCancelOrder, Integer totalQuantity, Double totalPrice,
			String warehousePickup, String shippingCode, String transporter, String shippingStatus, Double totalWeight,
			Date deliveryDate, Date deliveredDate, Date receiveOrderDate, Date confirmCompleteOrderDate, Date createdAt,
			Date updatedAt) {
		this.id = id;
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.personOrderId = personOrderId;
		this.contactFirstName = contactFirstName;
		this.contactLastName = contactLastName;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.contactAddress = contactAddress;
		this.note = note;
		this.status = status;
		this.reasonForCancelOrder = reasonForCancelOrder;
		this.totalQuantity = totalQuantity;
		this.totalPrice = totalPrice;
		this.warehousePickup = warehousePickup;
		this.shippingCode = shippingCode;
		this.transporter = transporter;
		this.shippingStatus = shippingStatus;
		this.totalWeight = totalWeight;
		this.deliveryDate = deliveryDate;
		this.deliveredDate = deliveredDate;
		this.receiveOrderDate = receiveOrderDate;
		this.confirmCompleteOrderDate = confirmCompleteOrderDate;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Order(Integer id, String orderNo, Date orderDate, String contactAddress, String status,
			Integer totalQuantity, Double totalPrice, Date createdAt, Date updatedAt) {
		this.id = id;
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.contactAddress = contactAddress;
		this.status = status;
		this.totalQuantity = totalQuantity;
		this.totalPrice = totalPrice;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Order(HttpServletRequest request, HttpServletResponse response) {
		this.contactFirstName = request.getParameter("firstName");
		this.contactLastName = request.getParameter("lastName");
		this.contactAddress = request.getParameter("address");
		this.contactPhone = request.getParameter("phone");
		this.contactEmail = request.getParameter("email");
		this.note = request.getParameter("note");
	}
	
	public static String generateOrderNo() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			
			String prefix = dateFormat.format(date);
			Integer maxTail = OrderDAO.getMaxTailOrderNo(prefix);
			
			if (maxTail == -1) {
				return prefix + "01";
			} else if (maxTail >= 0) {
				return prefix + String.format("%02d", maxTail + 1);
			}
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getPersonOrderId() {
		return personOrderId;
	}

	public void setPersonOrderId(Integer personOrderId) {
		this.personOrderId = personOrderId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReasonForCancelOrder() {
		return reasonForCancelOrder;
	}

	public void setReasonForCancelOrder(String reasonForCancelOrder) {
		this.reasonForCancelOrder = reasonForCancelOrder;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getWarehousePickup() {
		return warehousePickup;
	}

	public void setWarehousePickup(String warehousePickup) {
		this.warehousePickup = warehousePickup;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public Date getReceiveOrderDate() {
		return receiveOrderDate;
	}

	public void setReceiveOrderDate(Date receiveOrderDate) {
		this.receiveOrderDate = receiveOrderDate;
	}

	public Date getConfirmCompleteOrderDate() {
		return confirmCompleteOrderDate;
	}

	public void setConfirmCompleteOrderDate(Date confirmCompleteOrderDate) {
		this.confirmCompleteOrderDate = confirmCompleteOrderDate;
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
