<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<link rel="stylesheet" type="text/css" href="assets/css/panel-list.css">
<link rel="stylesheet" type="text/css" href="assets/css/orders-list.css">

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
					<li class="active">Đơn hàng</li>
				</ul>
			</div>

			<div class="title-section">
				<h1>Đơn hàng</h1>
			</div>

			<div class="content-section">
				<div class="panel-body">
					<div>
						<h2>Đơn hàng</h2>
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
											<th>Số</th>
											<th>Địa chỉ giao hàng</th>
											<th>Ngày</th>
											<th style="min-width:105px">Số lượng</th>
											<th>Tổng</th>
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
												<td>
													<a href='<c:url value="/admin/order?action=detail&id=${item.id}" />'>${item.orderNo}</a>
												</td>
												<td>${item.contactAddress}</td>
												<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.orderDate}" /></td>
												<td>${item.totalQuantity} sản phẩm</td>
												<td><custom:currency price="${item.totalPrice}" /></td>
												<td><custom:a-order-status status="${item.status}" /></td>
												<td class="action">
													<div class="btn-group action-group">
														<a class="btn btn-default btn-sm" href='<c:url value="/admin/order?action=detail&id=${item.id}" />'>Xem</a>
														<button class="btn btn-default btn-sm dropdown-toggle"
															type="button" data-bs-toggle="dropdown"
															aria-expanded="false"></button>
													</div>
												</td>
											</tr>
										</c:forEach>
										
										<%-- 
										<tr>
											<td class="choosen"><input
												class="form-check-input choose" type="checkbox" value="">
											</td>
											<td>100425</td>
											<td>115302, Moscowul. Varshavskaya, 15-2-178</td>
											<td>13/08/2022</td>
											<td>3 sản phẩm</td>
											<td>2,000,000d</td>
											<td><span class="order-status yellow">Đã giao
													hàng</span></td>
											<td class="action">
												<div class="btn-group action-group">
													<a class="btn btn-default btn-sm" href="#">Xem</a>
													<button class="btn btn-default btn-sm dropdown-toggle"
														type="button" data-bs-toggle="dropdown"
														aria-expanded="false"></button>
													<ul class="dropdown-menu dropdown-menu-end">
														<li><a class="dropdown-item" href="#"><i
																class="fa-solid fa-xmark icon"></i>Delete</a></li>
														<li><a class="dropdown-item" href="#"><i
																class="fa-solid fa-power-off icon"></i>Disable</a></li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<td class="choosen"><input
												class="form-check-input choose" type="checkbox" value="">
											</td>
											<td>100676</td>
											<td>E67 đường 5, Mỹ Hòa 2, Xuân Thới Đông, Hóc Môn,
												Tp.HCM</td>
											<td>13/08/2022</td>
											<td>1 sản phẩm</td>
											<td>500,000d</td>
											<td><span class="order-status green">Chờ xác nhận</span>
											</td>
											<td class="action">
												<div class="btn-group action-group">
													<a class="btn btn-default btn-sm" href="#">Xem</a>
													<button class="btn btn-default btn-sm dropdown-toggle"
														type="button" data-bs-toggle="dropdown"
														aria-expanded="false"></button>
													<ul class="dropdown-menu dropdown-menu-end">
														<li><a class="dropdown-item" href="#"><i
																class="fa-solid fa-xmark icon"></i>Delete</a></li>
														<li><a class="dropdown-item" href="#"><i
																class="fa-solid fa-power-off icon"></i>Disable</a></li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<td class="choosen"><input
												class="form-check-input choose" type="checkbox" value="">
											</td>
											<td>100456</td>
											<td>66/4B Trung Mỹ Tây 4, Trung Mỹ Tây, Trung Chánh, Hóc
												Môn, TP.HCM</td>
											<td>13/08/2022</td>
											<td>1 sản phẩm</td>
											<td>1,500,000d</td>
											<td><span class="order-status warning">Đã hủy</span></td>
											<td class="action">
												<div class="btn-group action-group">
													<a class="btn btn-default btn-sm" href="#">Xem</a>
													<button class="btn btn-default btn-sm dropdown-toggle"
														type="button" data-bs-toggle="dropdown"
														aria-expanded="false"></button>
													<ul class="dropdown-menu dropdown-menu-end">
														<li><a class="dropdown-item" href="#"><i
																class="fa-solid fa-xmark icon"></i>Delete</a></li>
														<li><a class="dropdown-item" href="#"><i
																class="fa-solid fa-power-off icon"></i>Disable</a></li>
													</ul>
												</div>
											</td>
										</tr>
										--%>
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
