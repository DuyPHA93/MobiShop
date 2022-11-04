<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%-- Flickity CSS--%>
<link rel="stylesheet" type="text/css"
	href="assets/plugins/flickity/css/flickity.css">
<%-- Home CSS--%>
<link rel="stylesheet" type="text/css" href="assets/css/home.css">

<%-- Import Header --%>
<c:import url="/header.jsp">
	<c:param name="title" value="Home"></c:param>
</c:import>

<div class="wrapper">
	<div class="row gx-0 mt-4 mb-5">
		<div class="col-md-12 col-lg-9">
			<div class="products-tab products-slide">
				<div class="tabs">
					<a class="tab active" href="javascript:;" data-tabid="tab_1">Sản
						phẩm bán chạy</a> <a class="tab" href="javascript:;"
						data-tabid="tab_2">Sản phẩm mới nhất</a> <a class="tab"
						href="javascript:;" data-tabid="tab_3">Sản phẩm khuyến mãi</a>
				</div>
				<div class="tab-content">
					<div
						class="content products-list show product-carousel flickity-outside-button large-columns-5 meidum-columns-4 small-columns-2"
						id="tab_1">
						<c:forEach var="item" items="${featuredProducts}">
							<div class="col">
								<div class="product-box">
									<div class="photo">
										<a href='<c:url value="product?action=detail&id=${item.id }" />'>
											<img alt="" src="assets/images/products/${item.fileName}">
										</a>
									</div>
									<div class="box-text text-center">
										<p class="name">
											<a href='<c:url value="product?action=detail&id=${item.id }" />'>${item.name}</a>
										</p>
										<p class="price">
											<custom:currency price="${item.price}" />
										</p>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>

					<div
						class="content products-list large-columns-5 meidum-columns-4 small-columns-2"
						id="tab_2">
						<c:forEach var="item" items="${lastProducts}">
							<div class="col">
								<div class="product-box">
									<div class="photo">
										<a href='<c:url value="product?action=detail&id=${item.id }" />'>
											<img alt="" src="assets/images/products/${item.fileName}">
										</a>
									</div>
									<div class="box-text text-center">
										<p class="name">
											<a href='<c:url value="product?action=detail&id=${item.id }" />'>${item.name}</a>
										</p>
										<p class="price">
											<custom:currency price="${item.price}" />
										</p>
									</div>
								</div>
							</div>
						</c:forEach>

					</div>

					<div
						class="content products-list large-columns-5 meidum-columns-4 small-columns-2"
						id="tab_3">
						<c:forEach var="item" items="${promotionProducts}">
							<div class="col">
								<div class="product-box">
									<div class="photo">
										<a href='<c:url value="product?action=detail&id=${item.id }" />'>
											<img alt="" src="assets/images/products/${item.fileName}">
										</a>
									</div>
									<div class="box-text text-center">
										<p class="name">
											<a href='<c:url value="product?action=detail&id=${item.id }" />'>${item.name}</a>
										</p>
										<p class="price">
											<custom:currency price="${item.price}" />
										</p>
									</div>
								</div>
							</div>
						</c:forEach>

					</div>
				</div>
			</div>
		</div>
		<div class="col-md-3 d-none d-lg-block">
			<div class="side-right">
				<div class="featured-news">
					<div class="title">Tin tức nổi bật</div>
					<div class="news">
						<div class="post">
							<a href="#">
								<h5 class="post-title">Trọn bộ hình nền Huawei Mate 10 đẹp
									“miễn chê” cho mọi smartphone</h5>
								<div class="divider"></div>
								<p>Bộ hình nền siêu đẹp từ Huawei Mate 10 đến với nhiều chủ
									đề trừu [...]</p>
							</a>
						</div>
						<div class="post">
							<a href="#">
								<h5 class="post-title">Rò rỉ thông tin Nokia 6 (2018): Màn
									hình tràn viền, tỉ lệ 18:9</h5>
								<div class="divider"></div>
								<p>Thông tin rò rỉ từ Trung Quốc cho biết dường như HMD
									Global đang có [...]</p>
							</a>
						</div>
						<div class="post">
							<a href="#">
								<h5 class="post-title">Meizu Note 8 bất ngờ xuất hiện trong
									diện mạo đẹp hoàn hảo</h5>
								<div class="divider"></div>
								<p>Hình ảnh thiết kế Meizu Note 8 lấy ý tưởng từ tương lai
									với cụm [...]</p>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<c:set var="count" value="1"></c:set>
	<c:forEach var="homeProduct" items="${homeProducts}">
		<div class="row gx-0 mt-4 mb-5">
			<div class="col-md-12 col-lg-9">
				<div class="products-tab">
					<div class="tabs">
						<%-- 
					<a class="tab active" href="javascript:;" data-tabid="tab_4">Sản phẩm bán chạy</a>
					<a class="tab" href="javascript:;" data-tabid="tab_5">Sản phẩm mới nhất</a>
					<a class="tab" href="javascript:;" data-tabid="tab_6">Sản phẩm khuyến mãi</a>
					--%>
						<c:forEach var="item" items="${homeProduct.tabs}"
							varStatus="status">
							<a class="tab ${status.first ? 'active' : ''}"
								href="javascript:;" data-tabid="${item.key}">${item.value}</a>
						</c:forEach>
					</div>
					<div class="tab-content">
						<c:forEach var="content" items="${homeProduct.tabContent}" varStatus="status">
							<div class="content products-list ${status.first ? 'show' : ''} large-columns-5 meidum-columns-4 small-columns-2" id="${content.key}">
								<c:forEach var="item" items="${content.value}">
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
						</c:forEach>

					</div>
				</div>
			</div>
			<div class="col-md-3 d-none d-lg-block">
				<div class="side-right">
					<div class="ad">
						<img alt="" src="assets/images/ad_${count}.jpg">
					</div>
				</div>
			</div>
		</div>
		
		<c:if test="${count < 2}">
		<c:set var="count" value="${count + 1}"></c:set>
		</c:if>
		
	</c:forEach>
</div>

<%-- Import Footer --%>
<c:import url="/footer.jsp"></c:import>

<script src="assets/plugins/flickity/js/flickity.pkgd.min.js"></script>
<script src="assets/js/home.js"></script>