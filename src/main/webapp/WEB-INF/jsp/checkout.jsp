<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="col-sm-12">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<b>Order Summary</b>
				<div class="btn-group pull-right">
					<a href="<c:url value='home.do?action=Cart' />">Change</a>
				</div>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-12">
						<table class="table table-hover table-condensed table-bordered">
							<thead>
								<tr>
									<th style="width: 50%">Item description</th>
									<th style="width: 15%">Price</th>
									<th style="width: 15%">Quantity</th>
									<th style="width: 20%" class="text-center">Sub total</th>
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
										<td data-th="Quantity">${item.quantity}</td>
										<td data-th="Subtotal" class="text-center">${item.totalPriceCurrencyFormat}</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr class="visible-xs"></tr>
								<tr>
									<td></td>
									<td colspan="2" class="hidden-xs"></td>
									<td class="hidden-xs text-center"><strong>Total
											${cart.totalPriceCurrencyFormat}</strong></td>
								</tr>
							</tfoot>
						</table>
						<span style="color: red"><c:out
								value="${validationErrors.emptyCart}" /></span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<form method="post" action="<c:url value='home.do?action=Checkout' />">
	<div class="row">
		<div class="col-sm-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<b>Shipping Address</b>
					<div class="btn-group pull-right">
						<a href="<c:url value='home.do?action=Address&op=new' />">Add
							new address</a>
					</div>
				</div>
				<div class="panel-body">
					<div id="divShippingAddress"
						class="well well-sm col-xs-6 col-sm-6 col-md-6"></div>
					<div class="col-xs-6 col-sm-6 col-md-6">
						<div class="form-group">
							<b>Change Shipping Address</b>
						</div>
						<div class="form-group">
							<select id="sbAddress" class="form-control" name="sbAddress">
								<option value="-1">---SELECT---</option>
								<c:forEach items="${addresses}" var="address">
									<option value="${address.id}">${address.firstName}</option>
								</c:forEach>
							</select> <span style="color: red"><c:out
									value="${validationErrors.errAddress}" /></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<b>Payment Details</b>
					<div class="btn-group pull-right">
						<c:choose>
							<c:when test="${empty creditCard}">
								<a href="<c:url value='home.do?action=CreditCard&op=new' />">Add
									new card</a>
							</c:when>
							<c:otherwise>
								<a
									href="<c:url value='home.do?action=CreditCard&op=edit&id=${creditCard.id}' />">Edit
									saved card</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="panel-body">
					<input type="hidden" name="cardId"
						value="<c:out value='${creditCard.id}' />" />
					<div class="round">
						<input type="checkbox" id="cbToggle" name="cbToggle"
							${toggleChecked == true ? 'checked' : ''} /> <label
							for="cbToggle"></label> <span style="color: green;"><b>&nbsp;&nbsp;&nbsp;Use
								saved card</b></span>
					</div>
					<br /> <span style="color: red"><c:out
							value="${validationErrors.errCreditCard}" /></span>
					<div id="divSavedCard" class="well well-sm" align="center">
						<div class="form-group">
							<h4>${creditCard.cardOwner}</h4>
						</div>
						<div class="form-group">
							<h5>${creditCard.cardType}</h5>
							<h5>${creditCard.obscuredNumber}</h5>
						</div>
					</div>
					<div id="divCardDetails">
						<div class="form-group" style="text-align: center;">
							<strong>ENTER CARD DETAILS</strong>
						</div>
						<div class="form-group">
							<input type="text" class="form-control" name="card-owner"
								id="card-owner" placeholder="Card Holder's Name"
								value="${modalCreditCard.cardOwner}"> <span
								style="color: red"><c:out
									value="${validationErrors.cOwner}" /></span>
						</div>
						<div class="form-group">
							<select class="form-control" name="card-type">
								<option value="-1">Select Card Type</option>
								<option value="visa"
									${modalCreditCard.cardType == 'visa' ? 'selected="selected"' : ''}>Visa</option>
								<option value="masterCard"
									${modalCreditCard.cardType == 'masterCard' ? 'selected="selected"' : ''}>MasterCard</option>
								<option value="americanExpress"
									${modalCreditCard.cardType == 'americanExpress' ? 'selected="selected"' : ''}>American
									Express</option>
								<option value="discover"
									${modalCreditCard.cardType == 'discover' ? 'selected="selected"' : ''}>Discover</option>
							</select> <span style="color: red"><c:out
									value="${validationErrors.cType}" /></span>
						</div>
						<div class="form-group">
							<input type="text" class="form-control" name="card-number"
								placeholder="Debit/Credit Card Number"
								value="${modalCreditCard.cardNumber}"> <span
								style="color: red"><c:out
									value="${validationErrors.cNumber}" /></span>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-xs-6">
									<select class="form-control col-sm-6" name="expiry-month">
										<option value="-1">Month</option>
										<option value="01"
											${modalCreditCard.expMonth == 01 ? 'selected="selected"' : ''}>Jan</option>
										<option value="02"
											${modalCreditCard.expMonth == 02 ? 'selected="selected"' : ''}>Feb</option>
										<option value="03"
											${modalCreditCard.expMonth == 03 ? 'selected="selected"' : ''}>Mar</option>
										<option value="04"
											${modalCreditCard.expMonth == 04 ? 'selected="selected"' : ''}>Apr</option>
										<option value="05"
											${modalCreditCard.expMonth == 05 ? 'selected="selected"' : ''}>May</option>
										<option value="06"
											${modalCreditCard.expMonth == 06 ? 'selected="selected"' : ''}>June</option>
										<option value="07"
											${modalCreditCard.expMonth == 07 ? 'selected="selected"' : ''}>July</option>
										<option value="08"
											${modalCreditCard.expMonth == 08 ? 'selected="selected"' : ''}>Aug</option>
										<option value="09"
											${modalCreditCard.expMonth == 09 ? 'selected="selected"' : ''}>Sep</option>
										<option value="10"
											${modalCreditCard.expMonth == 10 ? 'selected="selected"' : ''}>Oct</option>
										<option value="11"
											${modalCreditCard.expMonth == 11 ? 'selected="selected"' : ''}>Nov</option>
										<option value="12"
											${modalCreditCard.expMonth == 12 ? 'selected="selected"' : ''}>Dec</option>
									</select>
								</div>
								<div class="col-xs-6">
									<select class="form-control" name="expiry-year">
										<option value="2013"
											${modalCreditCard.expYear == 2013 ? 'selected="selected"' : ''}>2013</option>
										<option value="2014"
											${modalCreditCard.expYear == 2014 ? 'selected="selected"' : ''}>2014</option>
										<option value="2015"
											${modalCreditCard.expYear == 2015 ? 'selected="selected"' : ''}>2015</option>
										<option value="2016"
											${modalCreditCard.expYear == 2016 ? 'selected="selected"' : ''}>2016</option>
										<option value="2017"
											${modalCreditCard.expYear == 2017 ? 'selected="selected"' : ''}>2017</option>
										<option value="2018"
											${modalCreditCard.expYear == 2018 ? 'selected="selected"' : ''}>2018</option>
										<option value="2019"
											${modalCreditCard.expYear == 2019 ? 'selected="selected"' : ''}>2019</option>
										<option value="2020"
											${modalCreditCard.expYear == 2020 ? 'selected="selected"' : ''}>2020</option>
										<option value="2021"
											${modalCreditCard.expYear == 2021 ? 'selected="selected"' : ''}>2021</option>
										<option value="2022"
											${modalCreditCard.expYear == 2022 ? 'selected="selected"' : ''}>2022</option>
										<option value="2023"
											${modalCreditCard.expYear == 2023 ? 'selected="selected"' : ''}>2023</option>
									</select>
								</div>
								<span style="color: red"><c:out
										value="${validationErrors.expMonth}" /></span> <span
									style="color: red"><c:out
										value="${validationErrors.expYear}" /></span>
							</div>
						</div>
					</div>

				</div>
			</div>

		</div>
	</div>
	<div class="row">
		<div class="form-group" align="center">
			<div class="col-xs-12 col-sm-12 col-md-12">
				<button type="submit" class="btn btn-primary btn-submit-fix">Place
					Order</button>
			</div>
		</div>
	</div>
</form>