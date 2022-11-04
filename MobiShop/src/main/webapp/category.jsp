<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<c:if test="${categoryList == null}">
	
</c:if>

<jsp:useBean id="category" class="bean.Category" scope="page" />
	<c:set var="categoryList" value="${category.getListDB()}" scope="page" />

<link rel="stylesheet" type="text/css" href="assets/css/category.css">

<div class="category">
	<div class="caption no-select">
		<span class="c-icon"><i class="fa-solid fa-bars"></i></span> 
		<span>Danh mục sản phẩm</span>
	</div>
	<ul class="menu" style="${isHomePage == '1' ? '' : 'display: none;'}">
		<c:forEach var="item" items="${categoryList}">
			<li class="${item.getSubMenu().size() > 0 ? 'has-children' : ''}">
			<a href="product?action=productType&id=${item.id}">
				<span class="menu-icon">
					<img alt="" src="assets/images/product-types/${item.fileName}">
				</span>
				<span class="menu-text">${item.name}</span>
			</a>
			<c:if test="${item.getSubMenu().size() > 0}">
				<ul class="sub-menu">
					<c:forEach var="submenu" items="${item.getSubMenu()}">
						<li><a href="product?action=brand&id=${submenu.id}">${submenu.name}</a></li>
					</c:forEach>
				</ul>
			</c:if>
		</li>
		</c:forEach>
	</ul>
</div>