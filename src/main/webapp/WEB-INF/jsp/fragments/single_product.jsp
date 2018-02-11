<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="thumbnail">
	<img src="<c:url value='resources/images/books/${product.imageUrl}' />"
		alt="Cover Image" />
	<div class="caption">
		<form method="post" action="<c:url value='home.do?action=Cart' />">
			<h3>${product.title}</h3>
			<p>${product.description}</p>
			<input type="hidden" name="isbn"
				value="<c:out value='${product.isbn}' />" /> <input type="hidden"
				name="qty" value="1" />
			<button class="btn btn-success btn-sm" name="op" value="add">
				<i class="fa fa-shopping-cart"></i> Add To Cart
			</button>
			<a
			href="<c:url value='home.do?action=ProductDetail&isbn=${product.isbn}' />"
			class="btn btn-primary btn-sm pull-right">More Info</a>
		</form>
	</div>
</div>
