<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="assets/css/side-menu.css">

<div class="side-menu">
	<div class="container-left">
		<ul class="menu">
			<li class="menu-item">
				<a href="${pageContext.request.contextPath}/admin/dashboard">
					<span class="menu-icon"><i class="fa-solid fa-gauge"></i></span>
					<span class="menu-text">Dashboard</span>
				</a>
			</li>
			<li class="menu-item">
				<a href="${pageContext.request.contextPath}/admin/productType">
					<span class="menu-icon"><i class="fa-solid fa-laptop"></i></span>
					<span class="menu-text">Loại sản phẩm</span>
				</a>
			</li>
			<li class="menu-item">
				<a href="${pageContext.request.contextPath}/admin/brand">
					<span class="menu-icon"><i class="fa-solid fa-bandage"></i></span>
					<span class="menu-text">Nhãn hiệu</span>
				</a>
			</li>
			<li class="menu-item">
				<a href="${pageContext.request.contextPath}/admin/product">
					<span class="menu-icon"><i class="fa-solid fa-boxes-stacked"></i></span>
					<span class="menu-text">Sản phẩm</span>
				</a>
			</li>
			<li class="menu-item">
				<a href="${pageContext.request.contextPath}/admin/featuredProduct">
					<span class="menu-icon"><i class="fa-brands fa-product-hunt"></i></span>
					<span class="menu-text">Sản phẩm nổi bật</span>
				</a>
			</li>
			<li class="menu-item">
				<a href="${pageContext.request.contextPath}/admin/user">
					<span class="menu-icon"><i class="fa-solid fa-users"></i></span>
					<span class="menu-text">Người dùng</span>
				</a>
			</li>
			<li class="menu-item">
				<a href="${pageContext.request.contextPath}/admin/order">
					<span class="menu-icon"><i class="fa-solid fa-boxes-packing"></i></span>
					<span class="menu-text">Đơn hàng</span>
				</a>
			</li>
		</ul>
	</div>
	
</div>