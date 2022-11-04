<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.standalone.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/panel-detail.css">

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
					<li><a href='<c:url value="/admin/featuredProduct" />'>Sản phẩm nổi bật</a></li>
					<li class="active">Chi tiết</li>
				</ul>
			</div>

			<div class="title-section">
				<h1>Chi tiết sản phẩm nổi bật</h1>
			</div>

			<div class="content-section">
				<form action="${pageContext.request.contextPath}/admin/featuredProduct" method="POST">
					<input type="hidden" name="action" value="store">
					<input type="hidden" name="id" value="${detail.id}">
				
					<div class="panel-body">
						<custom:a-detail-title id="${detail.id}" name="${detail.productName}" />

						<div class="row mt-5">
							<div class="col-md-3">
								<div class="history-table mb-4">
									<table class="table table-striped table-hover">
										<tbody>
											<tr>
												<td>Trạng thái</td>
												<td><custom:a-status-label active="${detail.active}" /></td>
											</tr>
											<tr>
												<td>Được tạo ra vào</td>
												<td><custom:a-time-update date="${detail.createdAt}" /></td>
											</tr>
											<tr>
												<td>Đã cập nhật vào</td>
												<td><custom:a-time-update date="${detail.updatedAt}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="col-md-9">
								<c:if test="${message != null}">
									<div class="error-alert"><i class="fa-solid fa-triangle-exclamation"></i> ${message}</div>
								</c:if>
								
								<h3>Thông tin sản phẩm nổi bật</h3>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Sản phẩm</label>
									<div class="col-md-9">
										<c:choose>
											<c:when test="${detail.id > 0}">
												<input type="hidden" name="product" value="${detail.productId}">
												<input type="hidden" name="productCode" value="${detail.productCode}">
												<input type="hidden" name="productName" value="${detail.productName}">
												<input type="text" name="productCodeName" class="d-form-control dw-75" value="[${detail.productCode}] ${detail.productName}" readonly >
											</c:when>
											<c:otherwise>
												<select name="product" class="d-form-control dw-75">
													<option value=""></option>
													<c:forEach var="item" items="${products}">
														<option value="${item.id}"  ${detail.productId == item.id ? 'selected' : '' }>[${item.code}] ${item.name}</option>
													</c:forEach>
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Ngày bắt đầu</label>
									<div class="col-md-9">
										<input type="text" name="startDate" class="d-form-control dw-75 ${detail.id > 0 ? '' : 'datepicker'}" value='<fmt:formatDate pattern="dd/MM/yyyy" value="${detail.startDate}" />'  ${detail.id > 0 ? 'readonly' : ''}>
									</div>
								</div>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Ngày kết thúc</label>
									<div class="col-md-9">
										<input type="text" name="endDate" class="d-form-control dw-75 datepicker" value='<fmt:formatDate pattern="dd/MM/yyyy" value="${detail.endDate}" />'>
									</div>
								</div>
								

								<div class="row mt-5">
									<div class="col-md-3 finish-row">
										<button class="btn btn-finish">
											<i class="fa-solid fa-check icon"></i>Hoàn tất
										</button>
									</div>
								</div>
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

<script type="text/javascript" src="assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="assets/js/panel-detail.js"></script>
<script type="text/javascript" src="assets/js/featured-product-detail.js"></script>