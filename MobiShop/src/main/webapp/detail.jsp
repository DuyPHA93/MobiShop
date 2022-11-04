<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%-- Detail CSS--%>
<link rel="stylesheet" type="text/css" href="assets/css/detail.css">

<%-- Import Header --%>
<c:import url="/header.jsp">
	<c:param name="title" value="Home"></c:param>
</c:import>

<div class="wrapper pb-5">
	<div class="row mt-4 mb-4">
		<div class="col-md-12">
			<div class="page-title">
				<span><a href='<c:url value="/home" />'>Trang chủ</a></span> <span><a
					href='<c:url value="product?action=productType&id=${detail.productTypeId}" />'>${detail.productTypeName}</a></span>
				<c:if test="${detail.brandName != null}">
					<span><a
						href='<c:url value="product?action=brand&id=${detail.brandId}" />'>${detail.brandName}</a></span>
				</c:if>
			</div>
		</div>
	</div>

	<c:if test="${successMsg != null}">
		<div id="success-msg">
			<span><i class="fa-solid fa-check"></i></span> ${successMsg}
		</div>
	</c:if>
	

	<div class="row mt-4">
		<div class="col-md-3 d-none d-md-block">
			<%-- Import Header --%>
			<c:import url="/products-side.jsp"></c:import>
		</div>
		<div class="col-md-9">
			<form action="${pageContext.request.contextPath}/cart" method="POST">
				<input type="hidden" name="action" value="addToCart">
				<input type="hidden" name="productId" value="${detail.id}">
			
				<div class="row">
					<div class="col-md-6">
						<div class="photo-detail">
							<img alt="" src="assets/images/products/${detail.fileName}">
						</div>
					</div>
					<div class="col-md-6">
						<div class="info">
							<h1>${detail.name}</h1>
							<div class="divider"></div>
							<div class="price">
								<custom:currency price="${detail.price}" />
							</div>
							<div class="description">
								${detail.description}
								<%-- 
							<p>✔ TÌNH TRẠNG: MỚI 100% QUỐC TẾ</p>
							<p>✔ BẢO HÀNH: 12 THÁNG APPLE</p>
							<p>✔ TRỌN BỘ: NGUYÊN SEAL CHƯA ACTIVE</p>
							--%>
							</div>

							<div>
								<input type="number" class="quantity" name="quantity" value="${old_quantity == null ? '1' : old_quantity}">
								<button class="add-to-cart-btn">THÊM VÀO GIỎ</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<%-- Import Footer --%>
<c:import url="/footer.jsp"></c:import>

<%  
request.getSession().removeAttribute("successMsg");  
request.getSession().removeAttribute("old_quantity");  
%>