<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test='${empty param.name}'>
		<jsp:include page="fragments/page_header.jsp">
			<jsp:param name="name" value="ALL BOOKS" />
		</jsp:include>
	</c:when>
	<c:otherwise>
		<jsp:include page="fragments/page_header.jsp">
			<jsp:param name="name" value="${param.name}" />
		</jsp:include>
	</c:otherwise>
</c:choose>
<div class="row">
	<div class="col-md-12">
		<c:forEach var="product" items="${products}">
			<div class="col-sm-6 col-md-3">
				<c:set var="product" value="${product}" scope="request" />
				<jsp:include page="fragments/single_product.jsp" />
			</div>
		</c:forEach>
	</div>

</div>