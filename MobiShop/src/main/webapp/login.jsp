<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Detail CSS--%>
<link rel="stylesheet" type="text/css" href="assets/css/login.css">

<%-- Import Header --%>
<c:import url="/header.jsp">
	<c:param name="title" value="Home"></c:param>
</c:import>

<div class="wrapper pt-5 pb-5">
	<form action="${pageContext.request.contextPath}/auth" method="POST" id="loginFrm">
		<h3>Đăng nhập</h3>
		<c:if test="${message != null }">
			<p class="error"><strong>Lỗi: </strong> ${message}</p>
		</c:if>
		
		<div class="d-input">
			<label>Tên tài khoản hoặc địa chỉ email * 
				<input type="text" name="username" placeholder="Tên tài khoản hoặc địa chỉ email" value="${username != null ? username: userRemember}"">
			</label>
		</div>
		<div class="d-input">
			<label>Mật khẩu * 
				<input type="password" name="password" placeholder="Mật khẩu">
			</label>
		</div>
		<div>
			<button>Đăng nhập</button>
		</div>
		<div class="row">
			<div class="col-md-6">
				<label>
					<input type="checkbox" name="remember" ${userRemember != null ? 'checked' : ''}>
					Ghi nhớ tài khoản
				</label>
			</div>
			<div class="col-md-6 text-end">
				<a href="#">Quên mật khẩu ?</a>
			</div>
		</div>
	</form>
</div>

<%-- Import Footer --%>
<c:import url="/footer.jsp"></c:import>