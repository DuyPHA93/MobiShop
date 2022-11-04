<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

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
					<li><a href='<c:url value="/admin/brand" />'>Nhãn hiệu</a></li>
					<li class="active">Chi tiết</li>
				</ul>
			</div>

			<div class="title-section">
				<h1>Chi tiết nhãn hiệu</h1>
			</div>

			<div class="content-section">
				<form action="${pageContext.request.contextPath}/admin/brand" method="POST">
					<input type="hidden" name="action" value="store">
					<input type="hidden" name="id" value="${detail.id}">
				
					<div class="panel-body">
						<custom:a-detail-title id="${detail.id}" name="${detail.name}" />

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
								
								<h3>Thông tin nhãn hiệu</h3>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Mã</label>
									<div class="col-md-9">
										<input type="text" name="code" class="d-form-control dw-75" value="${detail.code}" ${detail.id > 0 ? 'readonly' : ''}>
									</div>
								</div>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Tên</label>
									<div class="col-md-9">
										<input type="text" name="name" class="d-form-control dw-75" value="${detail.name}">
									</div>
								</div>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Loại sản phẩm</label>
									<div class="col-md-9">
										<select name="productType" class="d-form-control dw-75">
											<option value=""></option>
											<c:forEach var="item" items="${productTypes}">
												<option value="${item.id}" ${(detail.productTypeId == item.id) ? 'selected' : ''} >${item.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Hoạt động</label>
									<div class="col-md-9">
										<label class="check-switch" for="_active"> 
											<input type="checkbox" name="active" id="_active" ${detail.active == true ? 'checked' : ''}> 
											<span class="checkmark"> 
												<span class="pivot"> 
													<span class="checked"><i class="fas fa-check"></i></span> 
													<span class="uncheck"><i class="fas fa-times"></i></span>
												</span>
											</span>
										</label>
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

<script type="text/javascript" src="assets/js/panel-detail.js"></script>