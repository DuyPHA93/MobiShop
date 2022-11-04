<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${param.title}</title>

<link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="assets/plugins/fontawesome/css/all.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/main.css">
<link rel="stylesheet" type="text/css" href="assets/css/header.css">

<link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Noto+Sans&family=Roboto&display=swap" rel="stylesheet">
</head>
<body>
<div class="header">
	<div class="wrapper">
		<div class="top-head d-flex align-items-center">
		<div class="for-mobile d-lg-none">
			<span id="nav-button"><i class="fa-solid fa-bars"></i></span>
			<span id="search-button"><i class="fa-solid fa-magnifying-glass"></i></span>
		</div>
		<div id="logo">
			<a href='<c:url value="/" />'><img alt="Logo" src="assets/images/logo.png"></a>
		</div>
		<div class="d-flex w-100">
			<div class="flex-grow-1 d-none d-lg-block">
				<form action="${pageContext.request.contextPath}/product" method="GET">
					<input type="hidden" name="action" value="search">
				
					<div id="search-bar">
						<input type="text" name="query" placeholder="Gõ từ khóa tìm kiếm..." value="${param.query}" />
						<button>
							<i class="fa-solid fa-magnifying-glass"></i>
						</button>
					</div>
				</form>	
			</div>
			<div class="flex-grow-1 d-none d-lg-block">
				<div class="d-flex m-1">
					<c:choose>
						<c:when test="${accountInfo == null }">
							<div class="box-link">
								<a href='<c:url value="auth"/>'>Đăng nhập</a>
							</div>
							<div class="box-link">
								<a href='<c:url value="auth?action=register"/>'>Đăng ký</a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="box-link">
								<a href='<c:url value="order"/>'>Xin chào, ${accountInfo.firstName} ${accountInfo.lastName}</a>
							</div>
							<div class="box-link">
								<a href='<c:url value="auth?action=logout"/>'>Đăng xuất</a>
							</div>
						</c:otherwise>
					</c:choose>
					
					<div class="box-link">
						<a class="cart-link" href='<c:url value="cart"/>'>
							Giỏ hàng / 
							<custom:currency price="${cart == null ? 0 : cart.totalPrice}" />
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="navigation-bar d-none d-lg-block">
		<div class="wrapper d-flex  align-items-center">
			<%-- Import Category --%>
			<c:import url="/category.jsp"></c:import>
			
			<ul class="navigation flex-grow-1">
				<li><a href='<c:url value="/home"/>'>Trang chủ</a></li>
				<li><a href='<c:url value="#"/>'>Giới thiệu</a></li>
				<li><a href='<c:url value="/product"/>'>Sản phẩm</a></li>
				<li><a href='<c:url value="#"/>'>Tin tức</a></li>
				<li><a href='<c:url value="#"/>'>Liên hệ</a></li>
			</ul>
		</div>
	</div>
	<c:if test="${isHomePage == '1'}">
		<div class="bottom-head">
			<div class="wrapper">
				<div id="banner">
					<img alt="Banner" src="assets/images/banner.jpg">
				</div>
				<div class="left-ad">
				
				</div>
			</div>
		</div>
	</c:if>
</div>