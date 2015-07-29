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
		<h1>Order History</h1>
		<table class="table table-striped">
			<tr>
				<th>Order ID</th>
				<th>Date</th>
				<th>Total Amount</th>
			</tr>
			<c:forEach var="History" items="${allOrders}">
				<tr>
				
				
<!-- 				Replacement Code 				-->
				<td>
					<form action="OrderDetails" method="post">
						<input type="hidden" name="orderId" value="<c:out value='${History.orderId}'/>" />
						<input type="submit"  class="submitLink"  value="<c:out value='${History.orderId}'/>" />
					</form>
<!-- 				Replacement Code 				-->
				</td>
				
<!-- 				original single line below to retrieve customer  order history -->
<%-- 					<td><a href = 'OrderDetails?orderId=<c:out value='${History.orderId}'/>'><c:out value='${History.orderId}'/></a></td> --%>
					<td><fmt:formatDate type="date" value="${History.orderTimestamp}" /></td>
					<td class="right"><fmt:formatNumber value="${History.orderTotal}" type="currency"/></td>	
				</tr> 	
			</c:forEach>
		</table>
	</div>
	
	<c:import url="/inserts/footer.jsp" />		
	</div><!-- wrap -->
</body>

</html>