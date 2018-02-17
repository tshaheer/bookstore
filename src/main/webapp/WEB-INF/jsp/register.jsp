<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<jsp:include page="fragments/page_header.jsp">
		<jsp:param name="name" value="CREATE ACCOUNT" />
	</jsp:include>
	<div class="row push_down">
		<div class="col-sm-8 col-sm-offset-2">
			<form method="post"
				action="<c:url value='home.do?action=Register' />">
				<div class="form-group">
					<label for="email">Email:</label> <input type="email" name="email"
						id="email" class="form-control" value="${param.email}" /> <span
						style="color: red"><c:out value="${validationErrors.email}" /></span>
				</div>
				<div class="form-group">
					<label for="password">Password:</label> <input type="password"
						name="password" id="password" class="form-control"
						value="${param.password}" /> <span style="color: red"><c:out
							value="${validationErrors.password}" /></span>
				</div>
				<div class="form-group">
					<label for="confirmPassword">Confirm Password:</label> <input
						type="password" name="confirmPassword" id="confirmPassword"
						class="form-control" /> <span style="color: red"><c:out
							value="${validationErrors.confirmPassword}" /></span>
				</div>
				<div class="form-group">
					<input type="submit" class="btn btn-primary" value="Register" />
				</div>
			</form>
		</div>
	</div>
</div>