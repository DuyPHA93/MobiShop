<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%-- Detail CSS--%>
<link rel="stylesheet" type="text/css" href="assets/css/products.css">

<%-- Import Header --%>
<c:import url="/header.jsp">
	<c:param name="title" value="Home"></c:param>
</c:import>

<div class="wrapper pb-5">
	<div class="row mt-4">
		<div class="col-md-12 col-lg-6">
			<div class="page-title">
				<span><a href="home">Trang chủ</a></span> 
				<c:choose>
					<c:when test="${param.action == null || param.action == 'all' }">
						<span class="active">Sản phẩm</span> 
					</c:when>
					<c:when test="${param.action == 'search'}">
						<span><a href="product">Sản phẩm</a></span> 
						<span class="active">Kết quả tìm kiếm cho "${param.query}"</span>
					</c:when>
					<c:when test="${param.action == 'productType'}">
						<span class="active">${productTypeName}</span>
					</c:when>
					<c:when test="${param.action == 'brand'}">
						<span><a href="product?action=productType&id=${productTypeId}">${productTypeName}</a></span> 
						<span class="active">${brandName}</span>
					</c:when>
				</c:choose>
			</div>
		</div>
		<div class="col-md-12 col-lg-6 text-end">
			<c:if test="${paging.data.size() > 0}">
				<div class="sort-choosen">
					<c:choose>
						<c:when test="${paging.totalPage > 1}">
							<c:set var="from" value="${((paging.currentPage - 1) * paging.perPage) + 1}" />
							<c:set var="to" value="${((paging.currentPage - 1) * paging.perPage) + paging.data.size()}" />
					
							<span>Hiển thị ${from}–${to	} trong ${paging.totalRecords} kết quả</span> 
						</c:when>
						<c:otherwise>
							<span>Hiển thị một kết quả duy nhất</span> 
						</c:otherwise>
					</c:choose>
				
					<select name="sort">
						<option value="">Thứ tự mặc định</option>
						<option value="1">Thứ tự theo mức độ phổ biến</option>
						<option value="2">Mới nhất</option>
						<option value="3">Thứ tự theo giá: thấp đến cao</option>
						<option value="4">Thứ tự theo giá: cao xuống thấp</option>
					</select>
				</div>
			</c:if>
		</div>
	</div>
	
	<jsp:useBean id="category" class="bean.Category" scope="page" />
	<c:set var="categoryList" value="${category.getListDB()}" scope="page" />

	<div class="row mt-4">
		<div class="col-md-3 d-none d-lg-block">
			<div class="product-category-side">
				<h3>Danh mục sản phẩm</h3>
				<div class="divider"></div>
				<ul class="menu">
					<c:forEach var="item" items="${categoryList}">
						<li>
							<a href='<c:url value="product?action=productType&id=${item.id }" />'>
								<span class="menu-icon"><img alt="" src="assets/images/product-types/${item.fileName}"></span>
								<span class="menu-text">${item.name}</span>
							</a>
							
							<c:if test="${item.getSubMenu().size() > 0}">
								<span class="menu-badge no-select">
									<i class="fa-solid fa-angle-down"></i>
								</span>
								<ul class="submenu">
									<c:forEach var="submenu" items="${item.getSubMenu()}">
										<li><a href='<c:url value="product?action=brand&id=${submenu.id}" />'>${submenu.name}</a></li>
									</c:forEach>
								</ul>
							</c:if>
						</li>
					</c:forEach>
				</ul>
			</div>
		
			<%-- Import Header --%>
			<c:import url="/products-side.jsp"></c:import>
		</div>
		<div class="col-md-12 col-lg-9">
			<c:choose>
				<c:when test="${paging.data.size() > 0}">
					<div class="products-list blur-hover mb-5 large-columns-4 meidum-columns-3 small-columns-2">
						<c:forEach var="item" items="${paging.data}">
							<div class="col">
								<div class="product-box">
									<div class="photo">
										<a href='<c:url value="product?action=detail&id=${item.id }" />'> <img alt=""
											src="assets/images/products/${item.fileName}">
										</a>
									</div>
									<div class="box-text text-center">
										<p class="name">
											<a href='<c:url value="product?action=detail&id=${item.id }" />'>${item.name}</a>
										</p>
										<p class="price"><custom:currency price="${item.price}" /></p>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:when>
				<c:otherwise>
					<p>Không tìm thấy sản phẩm nào khớp với lựa chọn của bạn.</p>
				</c:otherwise>
			</c:choose>

			<div>
				<c:if test="${paging.totalPage > 1}">
					<ul class="paging">
					<c:url value="" var="displayURL">
						<c:choose>
							<c:when test="${param.action == null || param.action == 'all'}">
								<c:param name="action" value="all" />
							</c:when>
							<c:when test="${param.action == 'producType' || param.action == 'brand'}">
								<c:param name="action" value="${param.action}" />
								<c:param name="id" value="${param.id}" />
							</c:when>
							<c:otherwise>
								<c:param name="action" value="${param.action}" />
								<c:param name="query" value="${param.query}" />
							</c:otherwise>
						</c:choose>
						
					</c:url>
					
					<li class="page prev ${param.page == null || param.page == 1 ? 'disable' : ''}">
						<a href='<c:url value="${displayURL}&page=${paging.currentPage - 1}" />'><i class="fa-solid fa-angle-left arrow"></i></a>
					</li>
					
					<c:forEach var="page" begin="1" end="${paging.totalPage}">
						<li class="page ${paging.currentPage == page ? 'active' : '' }"><a href='<c:url value="${displayURL}&page=${page}" />'>${page}</a></li>
					</c:forEach>
					
					<li class="page next ${param.page == paging.totalPage ? 'disable' : ''}">
						<a href='<c:url value="${displayURL}&page=${paging.currentPage + 1}" />'><i class="fa-solid fa-angle-right arrow"></i></a>
					</li>
					
					</ul>
				</c:if>
			</div>
		</div>
	</div>
</div>

<%-- Import Footer --%>
<c:import url="/footer.jsp"></c:import>

<script src="assets/js/products.js"></script>