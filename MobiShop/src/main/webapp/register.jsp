<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- Detail CSS--%>
<link rel="stylesheet" type="text/css" href="assets/css/register.css">

<%-- Import Header --%>
<c:import url="/header.jsp">
	<c:param name="title" value="Home"></c:param>
</c:import>

<div class="wrapper pt-5 pb-5">
	<form action="${pageContext.request.contextPath}/auth" method="POST" id="registerFrm">
		<input type="hidden" name="action" value="register">
		
		<h3>Đăng ký</h3>
		<c:if test="${message != null}">
			<p class="error"><strong>Lỗi: </strong> ${message}</p>
		</c:if>
		
		<div class="row">
			<div class="col-md-12">
				<div class="d-input">
					<label>Tên tài khoản: <input type="text" name="username" placeholder="Tên tài khoản" value="${username}">
					</label>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="d-input">
					<label>Mật khẩu: <input type="password" name="password" placeholder="Mật khẩu">
					</label>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="d-input">
					<label>Nhập lại mật khẩu: <input type="password" name="re-password" placeholder="Nhập lại mật khẩu">
					</label>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="d-input">
					<label>Họ: <input type="text" name="firstName" placeholder="Họ" value="${firstName}">
					</label>
				</div>
			</div>
			<div class="col-md-6">
				<div class="d-input">
					<label>Tên: <input type="text" name="lastName" placeholder="Tên" value="${lastName}">
					</label>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="d-input">
					<label>Email: <input type="text" name="email" placeholder="Email" value="${email}">
					</label>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="d-input">
					<label>Số điện thoại: <input type="text" name="phone" placeholder="Số điện thoại" value="${phone}">
					</label>
				</div>
			</div>
		</div>
		<div>
			<button>Đăng ký</button>
		</div>
		<div class="row">
			<div class="col-md-6">
				<a href='<c:url value="auth"/>'>Đăng nhập</a>
			</div>
			<div class="col-md-6 text-end">
				<a href='<c:url value="#"/>'>Quên mật khẩu ?</a>
			</div>
		</div>
	</form>
</div>

<%-- Import Footer --%>
<c:import url="/footer.jsp"></c:import>