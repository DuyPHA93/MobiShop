<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Adminstrator Login</title>

<link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/login.css">
</head>
<body>
	<form action="${pageContext.request.contextPath}/admin/auth" method="POST" id="frm">
		<input type="hidden" name="action" value="login">
		<div class="login-box">
			<c:if test="${message != null}">
				<p id="error"> <strong>Lỗi: </strong>${message}</p>
			</c:if>
			
			<div class="form-group">
				<label>Email</label>
				<div>
					
					<input type="text" placeholder="Email" name="email" value="${email != null ? email: emailRemeber}">
				</div>
			</div>
			<div class="form-group">
				<label>Mật khẩu</label>
				<div>
					<input type="password" placeholder="Mật khẩu" name="password">
				</div>
			</div>
			<div class="form-group">
				<button class="btn btn-primary login-button">Đăng nhập</button>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<input type="checkbox" id="remmember" name="remember" ${emailRemeber != null ? 'checked' : ''} />
					<label for="remmember">Nhớ tài khoản</label>
				</div>
				<div class="col-lg-6 text-end">
					<a href="#">Quên mật khẩu ?</a>
				</div>
			</div>
		</div>
	</form>
</body>
</html>