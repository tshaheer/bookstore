<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="thumbnail">
	<img src="<c:url value='resources/images/books/${product.imageUrl}' />"
		alt="Cover Image" />
	<div class="caption">
		<h3>${product.title}</h3>
		<p>${product.description}</p>
		<a
			href="<c:url value='home.do?action=ProductDetail&isbn=${product.isbn}' />"
			class="btn btn-success btn-sm"><i class="fa fa-shopping-cart"></i>
			Add To Cart</a> <a
			href="<c:url value='home.do?action=ProductDetail&isbn=${product.isbn}' />"
			class="btn btn-primary btn-sm pull-right">More Info</a>
	</div>
</div>
