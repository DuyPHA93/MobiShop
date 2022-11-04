<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ attribute name="price" required="true" type="java.lang.Double" description="Price"%>

<fmt:setLocale value = "en_VN"/>
<span class="amount"><fmt:formatNumber type="currency" pattern = "###,### ₫" value = "${price}" /></span>

