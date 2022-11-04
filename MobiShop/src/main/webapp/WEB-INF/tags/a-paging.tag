<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="totalPage" required="true" type="java.lang.Integer" description="Total page" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" description="Current page" %>

<c:set var="currentPage" value="${currentPage == null ? 1 : currentPage }"></c:set>

<c:url value="" var="displayURL">
	<c:param name="perPage" value="${param.perPage}" />
	<c:param name="search" value="${param.search}" />
</c:url>

<div class="table-paging">
	<ul>
		<li class="${(currentPage == null || currentPage == 1) ? 'disabled' : ''}">
			<a href="${displayURL}&page=${currentPage - 1}"><i class="fa-solid fa-arrow-left-long"></i> Trước</a>
		</li>

		<c:forEach var="page" begin="1" end="${totalPage}">
			<li class="${(currentPage == page) || (currentPage == null && page == 1) ? 'active' : ''}">
				<a href="${displayURL}&page=${page}">${page}</a>
			</li>
		</c:forEach>

		<li class="${(currentPage == totalPage) ? 'disabled' : ''}">
			<a href="${displayURL}&page=${currentPage + 1}">Tiếp theo <i class="fa-solid fa-arrow-right-long"></i></a>
		</li>
	</ul>
</div>