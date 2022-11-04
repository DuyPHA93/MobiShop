<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="id" required="true" type="java.lang.Integer" description="ID"%>
<%@ attribute name="name" required="true" type="java.lang.String" description="Name"%>

<c:choose>
	<c:when test="${id > 0 }">
		<h2>
			Chi tiết: <strong>${name}</strong>
		</h2>
	</c:when>
	<c:otherwise>
		<h2>
			<strong>Tạo mới</strong>
		</h2>
	</c:otherwise>
</c:choose>
