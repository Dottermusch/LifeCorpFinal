<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${isAdmin != true}"><jsp:forward page="404"/></c:if>
<c:if test="${isAdmin == true}">
<html>
	<c:import url="/inserts/head.jsp" />
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">
	
	<h1>Admin:
		<c:choose>
			<c:when test="${requestScope.single != true}">
				List of All Customer Orders
			</c:when>
				
			<c:otherwise>
				Customer <c:out value="${ordList[0].demoCustomer.custFirstName} ${ordList[0].demoCustomer.custLastName}"/>'s Order History
			</c:otherwise>
		</c:choose>	
	</h1>
	<table class="table table-striped">
		<tr>
			<th><span class="pull-right" >Order ID</span></th>
			<th></th>
			<c:if test="${requestScope.single != true}">
				<th>Customer Name</th>
			</c:if>
			<th><span class="pull-right">Number of Line Items</span></th>
			<th><span class="pull-right">Order Timestamp</span></th>
			<th><span class="pull-right">Order Total</span></th>
		</tr>
		<c:forEach var="ord" items="${ordList}">
			<tr>
				<th>
					<form action="AdminCustomerOrderDetails" method="post">
						<c:set var="orderid"><c:out value="${ord.orderId}" /></c:set>
						<input type="hidden" name="id" value="${orderid}" />
						<span class="pull-right" ><input  class="submitLink" type="submit" value="<c:out value="${ord.orderId}"/>"></span>
					</form>
				</th>
				<td></td>
				<c:if test="${requestScope.single != true}">
					<td><c:out value="${ord.demoCustomer.custFirstName} ${ord.demoCustomer.custLastName}"/></td>
				</c:if>
				
				<td><span class="pull-right" ><c:out value="${ord.demoOrderItems.size()}"/></span></td>
				<td><span class="pull-right" ><fmt:formatDate value="${ord.orderTimestamp}" pattern="MMM dd, yyyy"/></span></td>
				<td><span class="pull-right" ><fmt:formatNumber type="currency" value="${ord.orderTotal}"/></span></td>
			</tr> 
		</c:forEach>
	
	</table>
	
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
</div><!-- wrap -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
</c:if>