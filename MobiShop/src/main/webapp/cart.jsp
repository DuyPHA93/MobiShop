<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%-- Detail CSS--%>
<link rel="stylesheet" type="text/css" href="assets/css/cart.css">

<%-- Import Header --%>
<c:import url="/header.jsp">
	<c:param name="title" value="Home"></c:param>
</c:import>

<div class="wrapper mt-4 pb-5 cart-container">
	<c:choose>
		<c:when test="${cart == null || cart.list.size() == 0 }">
			<div class="row mt-5">
				<div class="col-12 text-center">
					<p class="mb-4">Chưa có sản phẩm nào trong giỏ hàng.</p>
					<a href='<c:url value="/product" />' class="btn-red">Quay
						trở lại cửa hàng</a>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="row">
				<div class="col-lg-7 mb-4">
					<form action="${pageContext.request.contextPath}/cart" method="POST" id="frmCart">
					<input type="hidden" name="action" value="updateToCart">
					
					<div>
						<table class="table" id="cart-table">
							<thead>
								<tr>
									<th colspan="3">Sản phẩm</th>
									<th>Giá</th>
									<th>Số lượng</th>
									<th>Tổng</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${cart.list}">
									<tr>
										<td><span class="remove-btn" onclick="deleteItem(this)"><i class="fa-solid fa-xmark"></i></span></td>
										<td class="thumbnail">
											<a href='<c:url value="product?action=detail&id=${item.id}" />'> 
												<img alt="" src="assets/images/products/${item.fileName}">
											</a>
										</td>
										<td>
											<a href='<c:url value="product?action=detail&id=${item.id}" />'>${item.name}</a>
											<div class="for-mobile">
												<span class="m-quantity">${item.quantity} x </span> <span class="m-amount"><custom:currency price="${item.price}" /></span>
											</div>
										</td>
										<td><custom:currency price="${item.price}" /></td>
										<td>
											<input type="hidden" name="productId_${item.id}" id="productId_${item.id}" value="${item.quantity}">
											<input type="number" class="quantity" name="quantity" value="${item.quantity}" oninput="onInputQty(this,productId_${item.id})">
										</td>
										<td class="text-end"><custom:currency price="${item.price * item.quantity}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div>
						<a href='<c:url value="product" />' class="btn-red-border">
							<span><i class="fa-solid fa-arrow-left-long"></i></span> Tiếp tục
							xem sản phẩm
						</a>
						<button class="btn-red disabled" id="update-cart-btn">Cập nhật giỏ hàng</button>
					</div>
					</form>
				</div>
				<div class="col-lg-5">
					<div class="report-side">
						<table class="table" id="report-table">
							<thead>
								<tr>
									<th>Tổng số lượng</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<table class="table">
										<tbody>
											<tr>
												<th>Tổng phụ</th>
												<td class="text-end"><custom:currency price="${cart.totalPrice}" /></td>
											</tr>
											<tr>
												<th>Giao hàng</th>
												<td class="text-end">
													<p style="color: #666">Giao hàng miễn phí</p>
													<p style="color: #666">Đây chỉ là ước tính. Giá sẽ cập
														nhật trong quá trình thanh toán.</p>
													<p>Tính phí giao hàng</p>
												</td>
											</tr>
											<tr>
												<th>Tổng</th>
												<td class="text-end"><custom:currency price="${cart.totalPrice}" /></td>
											</tr>
										</tbody>
									</table>
								</tr>
							</tbody>
						</table>

						<div>
							<button id="checkout-btn">Tiến hành thanh toán</button>
						</div>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<%-- Import Footer --%>
<c:import url="/footer.jsp"></c:import>

<script>var ctx = "${pageContext.request.contextPath}"</script>
<script src="assets/js/cart.js"></script>