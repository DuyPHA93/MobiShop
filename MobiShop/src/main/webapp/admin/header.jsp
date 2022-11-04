<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin System</title>

<link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="assets/plugins/fontawesome/css/all.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/main.css">
<link rel="stylesheet" type="text/css" href="assets/css/header.css">
</head>
<body>
	<div class="header">
		<div class="container-left head-part-1">
			<div class="logo">
				<span class="mb-menu-btn"><i class="fa-solid fa-ellipsis-vertical"></i></span>
				<div>
					<a href="<%=request.getContextPath()%>/admin/dashboard">Admin System</a>
				</div>
			</div>
		</div>
		<div class="container-right head-part-2">
			<div class="row">
				<div class="col-4">
					<div id="search-bar">
						<input type="text" name="headSearch" placeholder="Tìm kiếm...">
						<button>
							<i class="fa-solid fa-magnifying-glass"></i>
						</button>
					</div>
				</div>
				<div class="col-8">
					<div class="head-menu text-end">
						<div class="account-info noselect">
							<div class="avatar">
								<img alt="" src="assets/images/avatars/avatar.jpg">
							</div>
							<div class="menu">
								<div>
									<span>${loginInfo.firstName} ${loginInfo.lastName}</span> <i
										class="icon fa-solid fa-caret-down"></i>
								</div>
								<ul class="hide">
									<li><a href="#"> <span class="menu-icon"><i
												class="fa-solid fa-gears"></i></span> <span class="menu-text">Cài đặt</span>
									</a></li>
									<li><a
										href="<%=request.getContextPath()%>/admin/auth?action=logout">
											<span class="menu-icon"><i
												class="fa-solid fa-right-from-bracket"></i></span> <span
											class="menu-text">Đăng xuất</span>
									</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>