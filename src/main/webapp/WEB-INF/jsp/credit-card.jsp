<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="fragments/page_header.jsp">
	<jsp:param name="name" value="ENTER YOUR CRREDIT CARD DETAILS" />
</jsp:include>
<div class="row">
	<div class="col-sm-8 col-sm-offset-2">
		<form class="form-horizontal" role="form" method="post"
			action="<c:url value='home.do?action=CreditCard' />">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="card-owner">Name
						on Card</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="card-owner"
							placeholder="Card Holder's Name"
							value="${modalCreditCard.cardOwner}"> <span
							style="color: red"><c:out
								value="${validationErrors.cOwner}" /></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="card-type">Card
						Type</label>
					<div class="col-sm-9">
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
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="card-number">Card
						Number</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="card-number"
							placeholder="Debit/Credit Card Number"
							value="${modalCreditCard.cardNumber}"> <span
							style="color: red"><c:out
								value="${validationErrors.cNumber}" /></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="expiry-month">Expiration
						Date</label>
					<div class="col-sm-9">
						<div class="row">
							<div class="col-xs-3">
								<select class="form-control col-sm-2" name="expiry-month">
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
							<div class="col-xs-3">
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
				<div class="form-group">
					<div class="col-sm-3 col-sm-offset-3">
						<input type="hidden" name="hdnId"
							value="<c:out value='${modalCreditCard.id}' />" /> <input
							type="submit" class="btn btn-primary" name="op"
							value="${modalCreditCard.id == null ? 'Save' : 'Update'}" />
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>