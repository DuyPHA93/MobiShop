<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%-- Detail CSS--%>
<link rel="stylesheet" type="text/css" href="assets/css/checkout.css">

<%-- Import Header --%>
<c:import url="/header.jsp">
	<c:param name="title" value="Home"></c:param>
</c:import>

<div class="wrapper mt-4 pb-5" id="checkout-form">
	<form action="${pageContext.request.contextPath}/checkout" method="POST">
		<c:if test="${message != null }">
			<p class="error">${message}</p>
		</c:if>
	
		<div class="row">
			<div class="col-md-7 pb-5">
				<h3>Thông tin thanh toán</h3>
				<div class="row">
					<div class="col-lg-6">
						<label>Họ * <input type="text" name="firstName" value="${firstName}">
						</label>
					</div>
					<div class="col-lg-6">
						<label>Tên * <input type="text" name="lastName" value="${lastName}">
						</label>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<label>Địa chỉ * <input type="text" name="address" value="${address}">
						</label>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<label>Điện thoại * <input type="text" name="phone" value="${phone}">
						</label>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<label>Email * <input type="text" name="email" value="${email}">
						</label>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<label>Ghi chú đơn hàng <textarea rows="4" cols="" name="note">${note}</textarea>
						</label>
					</div>
				</div>
			</div>
			<div class="col-md-5">
				<div id="order-report">
					<h3>Đơn hàng của bạn</h3>
					<table class="table">
						<thead>
							<tr>
								<th>Sản phẩm</th>
								<th>Tổng</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${cart.list}">
								<tr>
									<td>${item.name}  <strong>× ${item.quantity}</strong></td>
									<td><custom:currency price="${item.price * item.quantity}" /></td>
								</tr>
							</c:forEach>
							<%-- 
							<tr>
								<td>Camera Hành trình YI Dash Camera - Dark grey 2K  <strong>× 1</strong></td>
								<td>1,110,000 ₫</td>
							</tr>
							<tr>
								<td>Camera Xiaomi Yi - Cloud Dome 1080P  <strong>× 1</strong></td>
								<td>1,250,000 ₫</td>
							</tr>
							<tr>
								<td>Mouse Razer Mamba Wireless  <strong>× 2</strong></td>
								<td>300,000 ₫</td>
							</tr>
							--%>
						</tbody>
						<tfoot>
							<tr>
								<th>Tổng phụ</th>
								<td><custom:currency price="${cart.totalPrice}" /></td>
							</tr>
							<tr>
								<%-- 
								<th>Giao hàng</th>
								<td style="color: #666; font-size: .9em">Giao hàng miễn phí</td>
								--%>
								<td colspan="2" style="padding:0">
									<table>
										<tbody>
											<tr>
												<th>Giao hàng</th>
												<td style="color: #666; font-size: .9em; text-align: right;">Giao hàng miễn phí</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
							<tr>
								<th>Tổng</th>
								<td><custom:currency price="${cart.totalPrice}" /></td>
							</tr>
						</tfoot>
					</table>
					<div>
						<button id="order-btn">Đặt hàng</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

<%-- Import Footer --%>
<c:import url="/footer.jsp"></c:import>