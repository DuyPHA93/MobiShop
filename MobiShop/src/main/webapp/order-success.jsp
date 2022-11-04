<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%-- Detail CSS--%>
<link rel="stylesheet" type="text/css"
	href="assets/css/order-success.css">

<%-- Import Header --%>
<c:import url="/header.jsp">
	<c:param name="title" value="Home"></c:param>
</c:import>

<div class="wrapper pt-5 pb-5">
	<div class="order-success-container">
		<div class="head-section mt-5">
			<img alt="" src="assets/images/success.png">
			<h3>Đặt hàng thành công</h3>
			<div style="color: #666">
				Mã đơn hàng: <span>${orderNo}</span>
			</div>
		</div>
		<div class="mt-4">
			<table class="table" id="products-table">
				<thead>
					<tr>
						<th>Thời gian</th>
						<th>Tên sản phẩm</th>
						<th>Số lượng</th>
						<th>Tổng</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${cart.list}">
						<tr>
							<td>
								<strong><fmt:formatDate pattern = "HH:mm aa" value = "${orderDate}" /></strong>
								<div><fmt:formatDate pattern = "dd/MM/yyyy" value = "${orderDate}" /></div>
							</td>
							<td><a href='<c:url value="product?action=detail&id=${item.id }" />'>${item.name}</a></td>
							<td>x${item.quantity}</td>
							<td><custom:currency price="${item.price}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="mt-3 mb-5 text-center">
			<div style="font-size: 1.2em">Chúng tôi sẽ liên hệ với bạn sau
				khi nhận được đơn đặt hàng này</div>
			<div>Mọi thắc mắc xin vui lòng liên hệ hotline: 0917 616 633</div>
		</div>

		<div class="text-center">
			<a href='<c:url value="/home" />' id="home-back-btn"> <span><i
					class="fa-solid fa-house"></i></span> Trở về trang chủ
			</a> <a href='<c:url value="/product" />' id="continue-buy-btn">Mua
				thêm sản phẩm</a>
		</div>
	</div>
</div>

<%-- Import Footer --%>
<c:import url="/footer.jsp"></c:import>