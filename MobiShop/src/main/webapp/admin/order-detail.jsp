<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<link rel="stylesheet" type="text/css"
	href="assets/css/panel-detail.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/order-detail.css">

<%-- Import Header --%>
<c:import url="/admin/header.jsp">
	<c:param name="title" value="Detail"></c:param>
</c:import>

<div class="content">
	<%-- Import Side Menu --%>
	<c:import url="/admin/side-menu.jsp"></c:import>

	<div class="container-right">
		<div class="">
			<div class="head-section">
				<ul>
					<li><a href='<c:url value="/admin" />'>Trang chủ</a></li>
					<li><a href='<c:url value="/admin/order" />'>Đơn hàng</a></li>
					<li class="active">Chi tiết</li>
				</ul>
			</div>

			<div class="title-section">
				<h1>Chi tiết đơn hàng</h1>
			</div>

			<div class="content-section" id="order-detail">
				<form action="${pageContext.request.contextPath}/admin/order"
					method="POST" id="frm">
					<input type="hidden" name="action" id="_action" value=""> <input
						type="hidden" name="id" " value="${detail.id}"> <input
						type="hidden" name="orderNo" " value="${detail.orderNo}">

					<div class="row">
						<div class="col-lg-9">
							<div class="panel-body">
								<h2>Đơn hàng #${detail.orderNo}</h2>
								<span style="font-size: 1.15rem"><custom:a-order-status
										status="${detail.status}" /></span>
								<div class="row info mt-4">
									<div class="col-md-7 mb-3">
										<p>${detail.contactAddress}</p>
										<p>P: ${detail.contactPhone}</p>
										<p>${detail.contactEmail}</p>
									</div>
									<div class="col-md-5">
										<div class="text-end">
											<table class="table table-bordered">
												<thead>
													<th>Số đơn hàng</th>
													<th>Ngày đặt hàng</th>
												</thead>
												<tbody>
													<tr>
														<td>${detail.orderNo}</td>
														<td><fmt:formatDate pattern="dd/MM/yyyy"
																value="${detail.orderDate}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								
								<c:if test="${message != null}">
									<div class="error-alert"><i class="fa-solid fa-triangle-exclamation"></i> ${message}</div>
								</c:if>

								<div class="row mt-5">
									<div class="col-12">
										<div class="products-table">
											<h4>Sản phẩm</h4>
											<table class="table mt-3" id="products-table">
												<thead>
													<th style="width: 90px">Ảnh</th>
													<th style="width: 60%">Tên</th>
													<th class="quantity">SL</th>
													<th class="price">Giá</th>
													<th class="total">Tổng</th>
												</thead>
												<tbody>
													<c:forEach var="item" items="${products}">
														<tr>
															<td><a
																href='<c:url value="/admin/product?action=detail&id=${item.productId}" />'><img
																	alt="" src="../assets/images/products/${item.fileName}"></a></td>
															<td>${item.productName}
																<div class="code">${item.productCode}</div>
																<div class="for-mobile">
																	<span>${item.productQuantity} x </span>
																	<custom:currency price="${item.productPrice}" />
																</div>
															</td>
															<td class="quantity">${item.productQuantity}</td>
															<td class="price"><custom:currency
																	price="${item.productPrice}" /></td>
															<td class="total"><custom:currency
																	price="${item.productTotalPrice}" /></td>
														</tr>
													</c:forEach>
												</tbody>
												<tfoot>
													<tr>
														<td colspan="3"><strong>Ghi chú:</strong>
															<p>${detail.note}</p></td>
														<th>Tổng</th>
														<td class="total-amount"><custom:currency
																price="${detail.totalPrice}" /></td>
													</tr>
												</tfoot>
											</table>
										</div>
									</div>
								</div>

								<div class="row mt-5">
									<div class="col-12">

										<c:if test="${detail.status == 'da_nhan_hang' }">
											<h4>Vận chuyển</h4>
											
											<div class="mt-3" id="input-delivery">
												<div class="row mb-3">
													<label class="col-md-3 d-form-label">Kho lấy hàng</label>
													<div class="col-md-9">
														<select class="d-form-control dw-25" name="warehousePickup">
															<option value="Kho chính">Kho chính</option>
															<option value="Kho 1">Kho 1</option>
															<option value="Kho 2">Kho 2</option>
															<option value="Kho 3">Kho 3</option>
														</select>
													</div>
												</div>
												<div class="row mb-3">
													<label class="col-md-3 d-form-label">Mã vận chuyển</label>
													<div class="col-md-9">
														<input type="text" name="shippingCode"
															class="d-form-control dw-25">
													</div>
												</div>
												<div class="row mb-3">
													<label class="col-md-3 d-form-label">Nhà vận chuyển</label>
													<div class="col-md-9">
														<select class="d-form-control dw-25" name="transporter">
															<option value="Nhà vận chuyển 1">Nhà vân chuyển
																1</option>
															<option value="Nhà vận chuyển 2">Nhà vân chuyển
																2</option>
															<option value="Nhà vận chuyển 3">Nhà vân chuyển
																3</option>
														</select>
													</div>
												</div>
												<div class="row mb-3">
													<label class="col-md-3 d-form-label">Tổng khối
														lượng</label>
													<div class="col-md-9">
														<div class="d-input-group dw-25 mb-3">
															<input type="number" step=".01" class="d-form-control" name="totalWeight"
																aria-describedby="basic-addon2"> <span
																class="d-input-group-text" id="basic-addon2">kg</span>
														</div>
													</div>
												</div>
											</div>
										</c:if>

										<c:if test="${detail.status != 'dang_cho_nhan_hang' && detail.status != 'da_nhan_hang' && detail.status != 'da_huy' }">
											<h4>Vận chuyển</h4>
											
											<div class="mt-4" id="output-delivery">
												<div class="row">
													<div class="col-md-6 col-left">
														<div class="row">
															<div class="col-6">Kho lấy hàng</div>
															<div class="col-6 text-end">${detail.warehousePickup}</div>
														</div>
														<div class="row">
															<div class="col-6">Mã vận chuyển</div>
															<div class="col-6 text-end">${detail.shippingCode}</div>
														</div>
														<div class="row">
															<div class="col-6">Nhà vận chuyển</div>
															<div class="col-6 text-end">${detail.transporter}</div>
														</div>
													</div>
													<div class="col-md-6 col-right">
														<div class="row">
															<div class="col-6">Trạng thái vận chuyển</div>
															<div class="col-6 text-end">
																<custom:a-order-status status="${detail.shippingStatus}" />
															</div>
														</div>
														<div class="row">
															<div class="col-6">Tổng khối lượng</div>
															<div class="col-6 text-end">${detail.totalWeight} kg</div>
														</div>
													</div>
												</div>
											</div>
										</c:if>


									</div>
								</div>

								<c:if test="${detail.status == 'dang_cho_nhan_hang' || detail.status == 'da_huy' }">
									<div class="row mt-4">
										<div class="col-12">
											<h4>Hủy đơn hàng</h4>

											<div class="mt-4">
												<c:choose>
													<c:when test="${detail.status == 'da_huy'}">
														<div style="color: #f85d2c"><strong>Lý do hủy: </strong>${detail.reasonForCancelOrder}</div>
													</c:when>
													<c:otherwise>
														<div>Xin vui lòng nhập lý do trả về (tối đa 1000 kí
													tự).</div>
												<textarea rows="5" cols="" class="d-form-control dw-100" name="reasonCancelOrder"></textarea>
												<button class="d-btn d-default-btn mt-3" id="cancel_order_btn">
													<i class="fa-solid fa-arrow-right-arrow-left icon"></i> Hủy
												</button>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
								</c:if>

								<div class="row mt-5">
									<div class="col-12">
										<div class="text-center">
											<c:choose>
												<c:when test="${detail.status == 'dang_cho_nhan_hang' }">
													<button type="button" class="d-btn d-blue-btn"
														id="receive_order_button">
														<i class="fa-solid fa-check icon"></i> Nhận đơn hàng
													</button>
												</c:when>
												<c:when test="${detail.status == 'da_nhan_hang' }">
													<button type="button" class="d-btn d-blue-btn"
														id="delivery_order_button">
														<i class="fa-solid fa-truck icon"></i> Giao
													</button>
												</c:when>
												<c:when test="${detail.status == 'dang_giao_hang' }">
													<button class="d-btn d-blue-btn" id="confirm_delivered_button">
												<i class="fa-solid fa-check icon"></i> Xác nhận đã giao hàng
											</button>
												</c:when>
												<c:when test="${detail.status == 'da_giao_hang' }">
													<button class="d-btn d-blue-btn" id="confirm_complete_button">
												<i class="fa-solid fa-check icon"></i> Xác nhận đơn hàng
											</button>
												</c:when>
											</c:choose>

											<a href='<c:url value="/admin/order" />'
												class="d-btn d-default-btn" id="back_button"> <i
												class="fa-solid fa-arrow-left-long icon"></i> Trở lại
											</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-3">
							<div class="panel-body panel-right">
								<div class="caption">Khách hàng</div>

								<a
									href='<c:url value="/admin/user?action=detail&id=${detail.personOrderId}" />'
									class="customer-name">${personOrder.firstName} ${personOrder.lastName}</a>
								<div class="text-muted">Lần đầu đặt hàng</div>
							</div>
							<div class="panel-body panel-right">
								<div class="caption">Người liên hệ</div>

								<div>${detail.contactFirstName} ${detail.contactLastName}</div>
								<div>
									<a href="#">${detail.contactEmail}</a>
								</div>
								<div class="text-muted">${detail.contactPhone}</div>
							</div>
							<div class="panel-body panel-right">
								<div class="caption">Địa chỉ giao hàng</div>
								<div>${detail.contactFirstName} ${detail.contactLastName}</div>
								<div>${detail.contactAddress}</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<%-- Import Footer --%>
<c:import url="/admin/footer.jsp"></c:import>

<script type="text/javascript" src="assets/js/panel-detail.js"></script>
<script type="text/javascript" src="assets/js/order-detail.js"></script>