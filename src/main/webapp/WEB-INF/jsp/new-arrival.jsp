<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<section style="text-align: center">
		<a href="#"><i class="fa fa-angle-left"></i></a>
		<h6 style="display: inline-block; color: #76786D; font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;N&nbsp;E&nbsp;W
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A&nbsp;R&nbsp;R&nbsp;I&nbsp;V&nbsp;A&nbsp;L&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h6>
		<a href="#"><i class="fa fa-angle-right"></i></a> <br /> <br />
	</section>
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