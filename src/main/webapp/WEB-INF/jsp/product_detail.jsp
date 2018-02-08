<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div>
		<h3>${product.title}
			<small> by ${product.authorsString}</small>
		</h3>
	</div>
	<div class="row push_down">
		<div class="col-sm-4">
			<img class="img-thumbnail"
				src="<c:url value='resources/images/books/${product.imageUrl}' />"
				alt="Cover Image" />
		</div>
		<div class="col-sm-6">
			<div style="font-size: 25px;">Overview</div>
			<p>${product.description}</p>
			<div style="font-size: 25px;">Details</div>
			<table class="table table-striped">
				<tr class="active">
					<td>Author : ${product.authorsString}</td>
					<td>Category : ${product.categoriesString}</td>
				</tr>
				<tr class="active">
					<td>Published Date : ${product.pubDate}</td>
					<td>ISBN : ${product.isbn}</td>
				</tr>
			</table>
		</div>
		<div class="col-sm-2 well well-lg">
			<div>
				<p class="price">$ ${product.price}</p>
				<form method="post" action="<c:url value='/order/addItem'/>"
					class="float-left">
					<input type="hidden" name="productCode"
						value="<c:out value='${product.isbn}' />" />
					<div class="form-group">
						<em>Free shipping</em>
					</div>
					<div class="form-group">
						<input type="number" class="form-control" style="width: 60%"
							min="0" value="0" />
					</div>
					<div class="form-group">
						<button type="button" class="btn btn-success">
							<span class="glyphicon glyphicon-shopping-cart"></span> Add To
							Cart
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
