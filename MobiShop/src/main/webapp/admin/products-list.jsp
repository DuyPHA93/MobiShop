<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<link rel="stylesheet" type="text/css" href="assets/css/panel-list.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/products-list.css">

<%-- Import Header --%>
<c:import url="/admin/header.jsp">
	<c:param name="title" value="Admin System"></c:param>
</c:import>

<div class="content">
	<%-- Import Side Menu --%>
	<c:import url="/admin/side-menu.jsp"></c:import>

	<div class="container-right">
		<div class="">
			<div class="head-section">
				<ul>
					<li><a href='<c:url value="/admin" />'>Trang chủ</a></li>
					<li class="active">Sản phẩm</li>
				</ul>
			</div>

			<div class="title-section">
				<h1>Sản phẩm</h1>

				<div class="menu-link">
					<span class="item"> <a
						href='<c:url value="/admin/product?action=detail" />'><i
							class="fa-solid fa-plus icon"></i>Thêm sản phẩm</a>
					</span>
				</div>
			</div>

			<div class="content-section">
				<div class="panel-body">
					<div>
						<h2>Sản phẩm</h2>
					</div>
					<form action="" method="GET" id="frm">
						<div id="table_wraper">
							<div class="row align-items-center">
								<div class="col-sm-6">
									<div class="table-length">
										Hiển thị <select name="perPage" id="select_perPage">
											<option value="10" ${param.perPage == 10 ? 'selected' : ''}>10</option>
											<option value="25" ${param.perPage == 25 ? 'selected' : ''}>25</option>
											<option value="50" ${param.perPage == 50 ? 'selected' : ''}>50</option>
											<option value="100" ${param.perPage == 100 ? 'selected' : ''}>100</option>
										</select> trong ${paging.data.size()} kết quả
									</div>
								</div>
								<div class="col-sm-6 text-end">
									<div class="table-search">
										<div>
											<input type="text" name="search"
												placeholder="Gõ từ khóa tìm kiếm..." value="${param.search}">
											<button>
												<i class="fa-solid fa-magnifying-glass"></i>
											</button>
										</div>
									</div>
								</div>
							</div>
							<div class="table-responsive mt-3">
								<table class="table">
									<thead>
										<tr>
											<th class="choosen"><input class="form-check-input"
												type="checkbox" value="" id="choose_all"></th>
											<th>Id</th>
											<th>Ảnh</th>
											<th class="name">Tên</th>
											<th style="min-width:110px">Danh mục</th>
											<th>Giá</th>
											<th style="min-width:70px">SL</th>
											<th style="min-width:120px">Trạng thái</th>
											<th class="action"></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="item" items="${paging.data}">
											<tr>
												<td class="choosen"><input
													class="form-check-input choose" type="checkbox" value="">
												</td>
												<td><fmt:formatNumber type="number" pattern="0000" value="${item.id}" /></td>
												<td><a href='<c:url value="/admin/product?action=detail&id=${item.id}" />'><img alt=""
														src="../assets/images/products/${item.fileName}"></a></td>
												<td class="name">${item.name}</td>
												<td>${item.productTypeName}</td>
												<td><custom:currency price="${item.price}" /></td>
												<td>${item.quantity}</td>
												<td class="text-center">
													<c:choose>
														<c:when test="${item.active}">
															<i class="fa fa-check check-status"></i>
														</c:when>
														<c:otherwise>
															<i class="fa fa-times disable-status"></i></td>
														</c:otherwise>
													</c:choose>
												</td>
												<td class="action">
													<div class="btn-group action-group">
														<a class="btn btn-default btn-sm" href='<c:url value="/admin/product?action=detail&id=${item.id}" />'>Xem</a>
														<button class="btn btn-default btn-sm dropdown-toggle"
															type="button" data-bs-toggle="dropdown"
															aria-expanded="false"></button>
														<ul class="dropdown-menu dropdown-menu-end">
															<li><a class="dropdown-item delete" href="javascript:;" data-id="${item.id}"><i
																	class="fa-solid fa-xmark icon"></i>Xóa</a></li>
															<li><a class="dropdown-item disable" href="javascript:;" data-id="${item.id}"><i
																	class="fa-solid fa-power-off icon"></i>Vô hiệu hóa</a></li>
														</ul>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="table-bulk-action">
										<div class="dropdown">
											<button class="btn btn-orange btn-md dropdown-toggle"
												type="button" id="dropdownMenuButton1"
												data-bs-toggle="dropdown" aria-expanded="false">
												Bulk Action</button>
											<ul class="dropdown-menu"
												aria-labelledby="dropdownMenuButton1">
												<li><a class="dropdown-item" href="#"><i
														class="fa-solid fa-xmark icon"></i>Delete</a></li>
												<li><a class="dropdown-item" href="#"><i
														class="fa-solid fa-power-off icon"></i>Unpublished</a></li>
											</ul>
										</div>
									</div>
								</div>
								<div class="col-sm-6 text-end">
									<custom:a-paging totalPage="${paging.totalPage}" currentPage="${param.page}" />
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<%-- Import Footer --%>
<c:import url="/admin/footer.jsp"></c:import>

<script type="text/javascript" src="assets/js/panel-list.js"></script>
