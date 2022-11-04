<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%-- Detail CSS--%>
<link rel="stylesheet" type="text/css" href="assets/css/order-detail.css">

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
			<div class="account-content" id="order_detail_table">
				<div class="row">
					<div class="col-12">
						<h3>Chi tiết đơn hàng #${detail.orderNo}</h3>
						<custom:order-status status="${detail.status}" />
					</div>
				</div>
				
				<c:if test="${detail.reasonForCancelOrder != null}">
					<div style="color:#f85d2c"><strong>Lý do hủy: </strong>${detail.reasonForCancelOrder}</div>
				</c:if>
				
				<div class="row mt-3">
					<div class="col-md-6 mb-3">
						<div>${detail.contactAddress}</div>
						<div>P: ${detail.contactPhone}</div>
					</div>
					<div class="col-md-6">
						<div id="right-table">
							<table class="table table-bordered">
								<thead>
									<th>Mã đơn hàng</th>
									<th>Ngày đặt hàng</th>
								</thead>
								<tbody>
									<tr>
										<td>${detail.orderNo}</td>
										<td><fmt:formatDate pattern = "HH:mm aa dd/MM/yyy" value = "${detail.orderDate}" /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="row mt-4">
					<div class="col-md-4">
						<div>
							<div><strong>Địa chỉ người nhận</strong></div>
							<div>${detail.contactFirstName} ${detail.contactLastName}</div>
							<div>${detail.contactAddress}</div>
							<div>${detail.contactPhone}</div>
						</div>
					</div>
					<div class="col-md-4">
						<div>
							<div><strong>Hình thức giao hàng</strong></div>
							<fmt:parseDate type="date" value ="${detail.orderDate}" var="var" pattern="yyyy-MM-dd HH:mm:ss" />
							<c:set var="now" value="${var}" />
							<c:set target="${now}" property="time" value="${now.time + 86400000 * 2}" />
							<div>Vận chuyển tiết kiệm (dự kiến giao hàng vào Thứ bảy, <fmt:formatDate value="${now}" pattern="dd/MM/yyy" />)</div>
							<div>Phí vận chuyển: 0d</div>
						</div>
					</div>
					<div class="col-md-4">
						<div>
							<div><strong>Hình thức thanh toán</strong></div>
							<div>Thanh toán tiền mặt khi nhận hàng</div>
						</div>
					</div>
				</div>
				<div class="row mt-5">
					<div class="col-12">
						<div>
							<table class="table" id="product_table">
								<thead>
									<th colspan="2">Sản phẩm</th>
									<th class="text-end">Giá</th>
									<th class="text-center" style="min-width:55px">SL</th>
									<th class="text-end" style="min-width:120px">Giảm giá</th>
									<th class="text-end">Tổng</th>
								</thead>
								<tbody>
									<c:forEach var="item" items="${products}">
										<tr>
										<td class="thumbnail">
											<a href='<c:url value="product?action=detail&id=${item.productId }" />'>
												<img alt="" src="assets/images/products/${item.fileName}">
											</a>
										</td>
										<td><a href='<c:url value="product?action=detail&id=${item.productId }" />'>${item.productName}</a></td>
										<td class="text-end"><custom:currency price="${item.productPrice}" /></td>
										<td class="text-center">${item.productQuantity}</td>
										<td class="text-end">0 ₫</td>
										<td class="text-end"><custom:currency price="${item.productTotalPrice}" /></td>
									</tr>
									</c:forEach>
									
									<%--
									<tr>
										<td class="thumbnail">
											<a href="#"><img alt="" src="assets/images/products/product-01.jpg"></a>
										</td>
										<td><a href="#">Camera Hành trình YI Dash Camera - Dark grey 2K</a></td>
										<td class="text-end">1,110,000 ₫</td>
										<td class="text-center">1</td>
										<td class="text-end">0 ₫</td>
										<td class="text-end">1,110,000 ₫</td>
									</tr>
									<tr>
										<td class="thumbnail">
											<a href="#"><img alt="" src="assets/images/products/product-02.jpg"></a>
										</td>
										<td><a href="#">Mouse Razer Mamba WirelessK</a></td>
										<td class="text-end">1,110,000 ₫</td>
										<td class="text-center">1</td>
										<td class="text-end">0 ₫</td>
										<td class="text-end">1,110,000 ₫</td>
									</tr>
									<tr>
										<td class="thumbnail">
											<a href="#"><img alt="" src="assets/images/products/product-06.jpg"></a>
										</td>
										<td><a href="#">Camera Xiaomi Yi - Cloud Dome 1080P</a></td>
										<td class="text-end">1,110,000 ₫</td>
										<td class="text-center">1</td>
										<td class="text-end">0 ₫</td>
										<td class="text-end">1,110,000 ₫</td>
									</tr>
									 --%>
								</tbody>
								<tfoot>
									<tr>
										<th colspan="4" rowspan="3">
											Ghi chú: <br>
											<p class="fw-normal">${detail.note}</p>
										</th>
										<th class="no-bd text-end">Tổng phụ</th>
										<th class="no-bd text-end"><custom:currency price="${detail.totalPrice}" /></th>
									</tr>
									<tr>
										<th class="no-bd text-end">Giảm giá</th>
										<th class="no-bd text-end">0 ₫</th>
									</tr>
									<tr>
										<th class="no-bd text-end">Phí vận chuyển</th>
										<th class="no-bd text-end">0 ₫</th>
									</tr>
									<tr>
										<th colspan="4"></th>
										<th class="text-end">Tổng cộng</th>
										<th class="text-end"><custom:currency price="${detail.totalPrice}" /></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%-- Import Footer --%>
<c:import url="/footer.jsp"></c:import>