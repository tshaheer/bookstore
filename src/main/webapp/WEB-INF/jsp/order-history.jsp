<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://bookstore/jsptags/time" prefix="javatime" %>
<jsp:include page="fragments/page_header.jsp">
	<jsp:param name="name" value="ORDER HISTORY" />
</jsp:include>
<div class="row">
	<div class="table-responsive">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<th>Order Number</th>
					<th>Order Date</th>
					<th>Total Price</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="order" items="${orders}" varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td><a
							href="<c:url value='home.do?action=OrderHistory&orderno=${order.orderNumber}' />">${order.orderNumber}</a></td>
						<%-- <td>${order.orderDate}</td> --%>
						<td><javatime:formatDate value="${order.orderDate}" style="M-" /></td>
						<td>${order.orderTotalString}</td>
						<td><c:choose>
								<c:when test="${order.orderStatus eq 'PROCESSING'}">
									<span class="label label-default">${order.orderStatus}</span>
								</c:when>
								<c:when test="${order.orderStatus eq 'COMPLETED'}">
									<span class="label label-info">${order.orderStatus}</span>
								</c:when>
								<c:when test="${order.orderStatus eq 'CANCELLED'}">
									<span class="label label-danger">${order.orderStatus}</span>
								</c:when>
								<c:when test="${order.orderStatus eq 'ONHOLD'}">
									<span class="label label-warning">${order.orderStatus}</span>
								</c:when>
								<c:when test="${order.orderStatus eq 'SHIPPED'}">
									<span class="label label-success">${order.orderStatus}</span>
								</c:when>
								<c:when test="${order.orderStatus eq 'DELIVERED'}">
									<span class="label label-primary">${order.orderStatus}</span>
								</c:when>

							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
