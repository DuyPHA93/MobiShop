<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ attribute name="date" required="true" type="java.util.Date" description="Date"%>

<fmt:formatDate pattern="HH:mm dd-MM-yyyy" value="${date}" />