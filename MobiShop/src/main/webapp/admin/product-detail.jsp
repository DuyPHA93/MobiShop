<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<link rel="stylesheet" type="text/css"
	href="assets/css/panel-detail.css">

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
					<li><a href='<c:url value="/admin/product" />'>Sản phẩm</a></li>
					<li class="active">Chi tiết</li>
				</ul>
			</div>

			<div class="title-section">
				<h1>Chi tiết sản phẩm</h1>
			</div>

			<div class="content-section">
				<form action="${pageContext.request.contextPath}/admin/product" method="POST" enctype="multipart/form-data">
					<input type="hidden" name="action" value="store">
					<input type="hidden" name="id" value="${detail.id}">
					
					<div class="panel-body">
						<custom:a-detail-title id="${detail.id}" name="${detail.name}" />

						<div class="row mt-5">
							<div class="col-md-3">
								<div class="panel-upload mb-5">
									<div class="photo">
										<c:choose>
											<c:when test="${detail.fileName != null}">
												<img alt="" src="../assets/images/products/${detail.fileName}" id="result_photo">
											</c:when>
											<c:otherwise>
												<img alt="" src="assets/images/add-image.png" id="result_photo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="upload">
										<input type="file" name="file" id="file_upload">
										<label for="file_upload" id="upload_btn"><i class="fa-solid fa-cloud-arrow-up icon"></i> Tải lên</label>
									</div>
								</div>
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
								
								<h3>Thông tin sản phẩm</h3>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Mã</label>
									<div class="col-md-9">
										<input type="text" name="code" class="d-form-control dw-25" value="${detail.code}" ${detail.id > 0 ? 'readonly' : ''}>
									</div>
								</div>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Tên</label>
									<div class="col-md-9">
										<input type="text" name="name" class="d-form-control dw-75" value="${detail.name}">
									</div>
								</div>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Mô tả</label>
									<div class="col-md-9">
										<textarea rows="5" cols="" class="d-form-control dw-75" name="description">${detail.description}</textarea>
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
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Giá</label>
									<div class="col-md-9">
										<div class="d-input-group dw-25 mb-3">
											<span class="d-input-group-text" id="basic-addon2">đ</span>
  											<input type="text" class="d-form-control" name="price" value="${Integer.toString(detail.price)}" aria-describedby="basic-addon2">
										</div>
									</div>
								</div>
								<h3>Danh mục</h3>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Loại</label>
									<div class="col-md-9">
										<select class="d-form-control dw-25" name="productType" id="product_type_select">
											<option value=""></option>
											<c:forEach var="item" items="${productTypes}">
												<option value="${item.id}" ${(detail.productTypeId == item.id) ? 'selected' : ''} >${item.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Nhãn hiệu</label>
									<div class="col-md-9">
										<select class="d-form-control dw-25" name="brand" id="brand_select">	
											<option value=""></option>
											<c:forEach var="item" items="${brands}">
												<option value="${item.id}" ${(detail.brandId == item.id) ? 'selected' : ''} >${item.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<h3>Số lượng trong kho</h3>
								<div class="row mb-3">
									<label class="col-md-3 d-form-label">Số lượng</label>
									<div class="col-md-9">
										<input type="number" name="quantity" class="d-form-control dw-25" value="${detail.quantity}">
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
<script type="text/javascript" src="assets/js/product-detail.js"></script>