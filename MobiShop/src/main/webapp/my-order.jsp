<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%-- Detail CSS--%>
<link rel="stylesheet" type="text/css" href="assets/css/my-order.css">

<%-- Import Header --%>
<c:import url="/header.jsp">
	<c:param name="title" value="My Order"></c:param>
</c:import>

<div class="wrapper pb-5">
	<div class="row mt-4 mb-4">
		<div class="col-md-12">
			<div class="page-title">
				<span><a href='<c:url value="home" />'>Trang chủ</a></span> <span><a href='<c:url value="order" />'>Đơn
						hàng của tôi</a></span>
			</div>
		</div>
	</div>

	<div class="row mt-4">
		<div class="col-md-3 d-none d-md-block">
			<div>
				<ul class="menu-side">
					<li><a href='<c:url value="#" />'> <span class="menu-icon"><i
								class="fa-solid fa-user"></i></span> <span class="menu-text">Tài
								khoản</span>
					</a></li>
					<li><a href='<c:url value="order" />'> <span class="menu-icon"><i
								class="fa-solid fa-boxes-packing"></i></span> <span class="menu-text">Đơn
								hàng của tôi</span>
					</a></li>
					<li><a href='<c:url value="#" />'> <span class="menu-icon"><i
								class="fa-solid fa-gear"></i></span> <span class="menu-text">Cài
								đặt</span>
					</a></li>
				</ul>
			</div>
		</div>
		<div class="col-md-9">
			<div class="account-content table-responsive" id="my_order_table">
				<div class="row mb-4 align-items-center">
					<div class="col-md-5"><h3>Đơn hàng của tôi</h3></div>
					<div class="col-md-7">
						<div class="sort-choosen">
							<span>Hiển thị một kết quả duy nhất</span>
							<select class="sort">
								<option value="">Tất cả đơn hàng</option>
								<option value="">5 đơn hàng gần đây</option>
								<option value="">15 ngày gần đây</option>
								<option value="">30 ngày gần đây</option>
								<option value="">6 tháng gần đây</option>
								<option value="">Đặt hàng đặt trong năm 2</option>
							</select>
						</div>
					</div>
				</div>
				<table class="table">
					<thead>
						<th>Sản phẩm</th>
						<th>Số lượng</th>
						<th>Tổng tiền</th>
						<th>Trạng thái</th>
						<th></th>
					</thead>
					<tbody>
						<c:forEach var="item" items="${orders}">
							<tr style="background-color: #f0f0f0">
							<td colspan="5" class="text-muted">
								<span class="order-date"><fmt:formatDate pattern="dd-MM-yyy HH:mm:ss" value="${item.orderDate}" /></span>
								<span class="order-no">Mã số đơn: ${item.orderNo}</span>
							</td>
						</tr>
						<tr>
							<td>
								<div>
									<a href='<c:url value="/order?action=detail&id=${item.id}" />'>
										<img alt="" src="assets/images/products/${item.fileName}" style="width: 60px;">
									</a>
								</div>
							</td>
							<td>${item.totalQuantity} sản phẩm</td>
							<td><custom:currency price="${item.totalPrice}" /></td>
							<td><custom:order-status status="${item.status}" /></td>
							<td><a href='<c:url value="/order?action=detail&id=${item.id}" />'>Xem chi tiết</a></td>
						</tr>
						</c:forEach>
						
						<%-- 
						<tr style="background-color: #f0f0f0">
							<td colspan="5" class="text-muted">
								<span class="order-date">14-08-2022 18:45:21</span>
								<span class="order-no">Mã số đơn: LH54857345</span>
							</td>
						</tr>
						<tr>
							<td>
								<div>
									<a href=""><img alt="" src="assets/images/products/product-01.jpg" style="width: 60px;"></a>
								</div>
							</td>
							<td>1 sản phẩm</td>
							<td>1,000,000d</td>
							<td><span class="order-status yellow">Đã giao hàng</span></td>
							<td><a href="#">Xem chi tiết</a></td>
						</tr>
						
						
						<tr style="background-color: #f0f0f0">
							<td colspan="5" class="text-muted">
								<span class="order-date">14-08-2022 18:45:21</span>
								<span class="order-no">Mã số đơn: LH54833345</span>
							</td>
						</tr>
						<tr>
							<td>
								<div>
									<a href=""><img alt="" src="assets/images/products/product-02.jpg" style="width: 60px;"></a>
								</div>
							</td>
							<td>1 sản phẩm</td>
							<td>1,000,000d</td>
							<td><span class="order-status yellow">Đã giao hàng</span></td>
							<td><a href="#">Xem chi tiết</a></td>
						</tr>
						--%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<%-- Import Footer --%>
<c:import url="/footer.jsp"></c:import>