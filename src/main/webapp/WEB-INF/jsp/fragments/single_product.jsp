<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="thumbnail">
	<img src="<c:url value='resources/images/books/${product.imageUrl}' />"
		alt="Cover Image" />
	<div class="caption">
		<h3>${product.title}</h3>
		<p>${product.description}</p>
		<a href="<c:url value='' />" class="btn btn-primary btn-block">More</a>
	</div>
</div>
