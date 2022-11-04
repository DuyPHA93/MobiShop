<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<jsp:useBean id="product" class="model.Product" scope="page" />

<div class="products-side">
	<h3>Sản Phẩm</h3>
	<div class="divider"></div>
	<div class="products">
		<c:forEach var="item" items="${product.getRandomProducts()}">
			<div class="item">
				<a href='<c:url value="product?action=detail&id=${item.id }" />'> <img alt="" src="assets/images/products/${item.fileName}">
					<p class="name">${item.name}</p>
				</a> <span class="price"><custom:currency price="${item.price}" /></span>
			</div>
		</c:forEach>
	</div>
</div>