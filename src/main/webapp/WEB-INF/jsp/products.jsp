<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header">
	<h2 class="centre-align">
		<c:out value="${param.name}" />
	</h2>
</div>
<div class="row">
	<div class="col-md-12">
		<c:forEach var="product" items="${products}">
			<div class="col-sm-6 col-md-3">
				<c:set var="product" value="${product}" scope="request"/>
				<jsp:include page="fragments/single_product.jsp" />
			</div>
		</c:forEach>
	</div>

</div>