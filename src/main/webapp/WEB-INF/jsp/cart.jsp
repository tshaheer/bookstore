<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="fragments/page_header.jsp">
	<jsp:param name="name" value="CART" />
</jsp:include>
<c:choose>
	<c:when test="${emptyMessage != null}">
		<h3>${emptyMessage}</h3>
		<a href="<c:url value='home.do?action=Product' />"
			class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue
			Shopping</a>
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="col-sm-2 well">
				<p class="larger_p">
					<strong>To change the quantity for an item,</strong> enter the new
					quantity and click on the Update button.
				</p>
				<p class="larger_p">
					<strong>To remove an item,</strong> click on the remove button.
				</p>
			</div>
			<div class="col-sm-10">
				<table id="cart" class="table table-hover table-condensed">
					<thead>
						<tr>
							<th style="width: 50%">Product</th>
							<th style="width: 10%">Price</th>
							<th style="width: 15%">Quantity</th>
							<th style="width: 17%" class="text-center">Sub total</th>
							<th style="width: 8%"></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${cart.items}">
							<tr>
								<td data-th="Product">
									<div class="row">
										<div class="col-sm-2 hidden-xs">
											<img
												src="<c:url value='resources/images/books/${item.product.imageUrl}' />"
												alt="Cover Image" class="img-responsive" />
										</div>
										<div class="col-sm-10">
											<h4 class="nomargin">${item.product.title}</h4>
										</div>
									</div>
								</td>
								<td data-th="Price">${item.product.priceCurrencyFormat}</td>
								<td data-th="Quantity">
									<form method="post"
										action="<c:url value='home.do?action=Cart' />">
										<input type="hidden" name="isbn"
											value="<c:out value='${item.product.isbn}' />" /> <input
											type="number" name="qty" class="form-control text-center"
											style="width: 60%; float: left;"
											value="<c:out value='${item.quantity}' />"> &nbsp;
										<button class="btn btn-info btn-sm" name="op" value="update"
											title="Update">
											<i class="fa fa-refresh"></i>
										</button>
									</form>
								</td>
								<td data-th="Subtotal" class="text-center">${item.totalPriceCurrencyFormat}</td>
								<td class="actions" data-th="">
									<form method="post"
										action="<c:url value='home.do?action=Cart' />">
										<input type="hidden" name="isbn"
											value="<c:out value='${item.product.isbn}' />" />
										<button class="btn btn-danger btn-sm" name="op" value="remove"
											title="Remove">
											<i class="fa fa-trash-o"></i>
										</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr class="visible-xs"></tr>
						<tr>
							<td><a href="<c:url value='home.do?action=Product' />"
								class="btn btn-warning"><i class="fa fa-angle-left"></i>
									Continue Shopping</a></td>
							<td colspan="2" class="hidden-xs"></td>
							<td class="hidden-xs text-center"><strong>Total
									${cart.totalPriceCurrencyFormat}</strong></td>
							<td><a href="<c:url value='home.do?action=Checkout'/>"
								class="btn btn-success btn-block"> Checkout <i
									class="fa fa-angle-right"></i>
							</a></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</c:otherwise>
</c:choose>
