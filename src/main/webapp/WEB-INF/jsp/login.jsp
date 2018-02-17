<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<jsp:include page="fragments/page_header.jsp">
		<jsp:param name="name" value="SIGN IN" />
	</jsp:include>
	<div class="row push_down">
		<div class="col-sm-8 col-sm-offset-2">
			<c:if test="${message != null}">
				<div class="alert alert-danger" role="alert">
					<p>${message}</p>
				</div>
			</c:if>
			<form id="frmLogin" method="POST"
				action="<c:url value='home.do?action=Signin' />">
				<div class="form-group">
					<label>Email Address</label> <input type="email" name="email"
						class="form-control" placeholder="Email Address">
				</div>
				<div class="form-group">
					<label>Password</label> <input type="password" name="password"
						class="form-control" placeholder="Password">
				</div>
				<br />
				<div class="form-group">
					<input type="submit" class="btn btn-primary" value="Login" /> <a
						href="#" class="forgot-password"
						style="text-decoration: underline; color: #888;">Forgot
						Password?</a> <a href="<c:url value='home.do?action=Register' />"
						style="float: right; color: #888;">Register</a>
				</div>
				<div class="form-group">
					<input type="checkbox" class="form-check-input" value=""
						name="remember"> <label class="form-check-label"
						for="remember">Remember me</label>
				</div>
			</form>
		</div>
	</div>
</div>
