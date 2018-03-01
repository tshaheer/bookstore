<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String strDynIncPage = request.getParameter("paramDynInclPage") + ".jsp";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Book Store</title>

<jsp:include page="/WEB-INF/jsp/fragments/links.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/fragments/script.jsp"></jsp:include>

</head>
<body>
	<%-- <jsp:include page="/WEB-INF/jsp/fragments/nav_bar.jsp"></jsp:include> --%>
	<%@ include file="/WEB-INF/jsp/fragments/nav_bar.jsp"  %>
	<div class="container-fluid">
		<!-- dynamic area -->
		<jsp:include page="<%= strDynIncPage %>" flush="true" />
	</div>
	<!-- footer -->
	<%@ include file="fragments/footer.jsp"  %>
</body>
</html>