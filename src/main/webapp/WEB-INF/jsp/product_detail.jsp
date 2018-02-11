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
				<p class="price">${product.priceCurrencyFormat}</p>
				<form method="post" action="<c:url value='home.do?action=Cart' />"
					class="float-left">
					<input type="hidden" name="isbn"
						value="<c:out value='${product.isbn}' />" />
					<div class="form-group">
						<em>Free shipping</em>
					</div>
					<div class="form-group">
						<input type="number" name="qty"
							class="form-control text-center col-xs-offset-1" min="1"
							value="1" style="width: 50%" />
					</div>
					<div class="form-group">
						<button class="btn btn-success btn-block" name="op" value="add">
							<i class="fa fa-shopping-cart"></i> Add To Cart
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
