<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="fragments/page_header.jsp">
	<jsp:param name="name" value="Order Receipt" />
</jsp:include>
<div class="row">
	<p class="larger_p">
		Thank you for your Order, Your card has been charged <c:out value='${amount}' />. Your order
		number is <c:out value='${orderNo}' />. Please write down this order number number in case you
		need to refer to the order at future date. You will also be receiving
		a copy of your receipt via e-mail. We will begin processing your order
		right away. If you have any questions about your order, please feel
		free to contact us at <a href="mailto:contact@bookstore.com">contact@bookstore.com</a>
	</p>
</div>