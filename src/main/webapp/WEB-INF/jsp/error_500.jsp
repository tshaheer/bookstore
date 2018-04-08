<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="500 Internal Server Error">
<title>500 Internal Server Error</title>
<!-- Bootstrap core CSS -->
<jsp:include page="/WEB-INF/jsp/fragments/links.jsp"></jsp:include>
<style type="text/css">
body {
	background-color: #39bbdb;
	color: #fff;
	padding-bottom: 0;
	min-height: 100%;
	font-family: 'Open Sans', 'Segoe UI';
	font-size: 13px;
}

.error-header {
	background: #fff;
	height: 180px;
}

.error-container {
	background: rgba(0, 0, 0, .05);
	text-align: center;
}

.error-container h1 {
	color: #39bbdb;
}

.error-container h1 {
	font-size: 110px;
	font-weight: 300;
	margin: -103px 0 0 0;
	text-align: center;
}

.error-divider {
	margin-top: -27px;
	padding: 30px 0;
}

.error-container h2 {
	font-size: 58px;
	font-weight: 300;
	margin: 0;
	text-transform: uppercase;
}

.error-container p.description {
	color: #e5e5e5;
	font-size: 40px;
	margin: 0;
}

.return-btn, .return-btn:hover {
	border: 1px solid rgba(255, 255, 255, .3);
	padding: 10px 15px;
	margin-top: 100px;
	margin-bottom: 30px;
	display: inline-block;
	-webkit-border-radius: 5px;
	-webkit-background-clip: padding-box;
	-moz-border-radius: 5px;
	-moz-background-clip: padding;
	border-radius: 5px;
	background-clip: padding-box;
	color: #fff;
	font-size: 16px;
	font-weight: 300;
}

a:hover {
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="error-header"></div>
	<div class="container ">
		<section class="error-container text-center">
			<h1>500</h1>
			<div class="error-divider">
				<h2>ooops!!</h2>
				<p class="description">SOMETHING WENT WRONG.</p>
			</div>
			<a href="<c:url value='home.do?action=NewArrival' />"><i class="fa fa-home"></i>Home</a>
		</section>
	</div>
</body>
</html>