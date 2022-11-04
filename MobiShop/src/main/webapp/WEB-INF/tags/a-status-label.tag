<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="active" required="true" type="java.lang.Boolean" description="Status"%>

<c:choose>
	<c:when test="${active == true}">
		<span class="status-icon active">Đang hoạt động</span>
	</c:when>
	<c:otherwise>
		<span class="status-icon disabled">Không hoạt động</span>
	</c:otherwise>
</c:choose>
