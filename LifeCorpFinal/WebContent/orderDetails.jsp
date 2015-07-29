<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<c:import url="/inserts/head.jsp" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">
	<h1>Order Details</h1>
	<table class="table table-striped">
		<tr>
			<th>Quantity</th>
			<th>Product Name</th>
			<th>Unit Price</th>
			<th>Total Price</th>
		</tr>
		<c:forEach var="item" items="${detailedOrder}">
			<tr>
				<td class="right"><c:out value='${item.quantity}'/></td>
				<td><c:out value='${item.demoProductInfo.productName}'/></td>
				<td class="right"><fmt:formatNumber value="${item.unitPrice}" type="currency"/></td>
				<td ><fmt:formatNumber value="${item.quantity * item.unitPrice}" type="currency"/></td>
				
				
			</tr> 	
		</c:forEach>
	</table>
	<table class="table table-striped">
			<tr>
				<td class="col-sm-1">&nbsp;</td>
				<td class="col-sm-1">&nbsp;</td>
				<td class="col-sm-1 right"><strong>Total Amount</strong></td>
				<td class="col-sm-1 right"><fmt:formatNumber value="${orderTotal}" type="currency"/></td>
			</tr>
	</table>
	</div>
	<c:import url="/inserts/footer.jsp" />
</div><!-- wrap -->
</body>
</html>