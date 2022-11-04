<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="status" required="true" type="java.lang.String" description="Status"%>

<c:choose>
	<c:when test="${status == 'dang_cho_nhan_hang'}">
		<span class="order-status green">Chờ xác nhận</span>
	</c:when>
	<c:when test="${status == 'da_nhan_hang'}">
		<span class="order-status green">Đã nhận hàng</span>
	</c:when>
	<c:when test="${status == 'dang_giao_hang'}">
		<span class="order-status yellow">Đang giao hàng</span>
	</c:when>
	<c:when test="${status == 'da_giao_hang'}">
		<span class="order-status yellow">Đã giao hàng</span>
	</c:when>
	<c:when test="${status == 'da_huy'}">
		<span class="order-status warning">Đã hủy</span>
	</c:when>
	<c:when test="${status == 'hoan_thanh'}">
		<span class="order-status gray">Hoàn thành</span>
	</c:when>
</c:choose>