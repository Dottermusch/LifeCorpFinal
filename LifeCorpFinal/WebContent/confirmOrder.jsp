<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
	<c:import url="/inserts/head.jsp" />
	<title>Confirm Order</title>
	</head>
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">

	    <c:out value= "${message}" />
		<div class="jumbotron">
			<h1>Confirm Order</h1>
		</div>
		<h3>Customer Information:</h3>
		<hr />
 
		<form class="form-horizontal">
			
			<fieldset>
				<div class="form-group">
					<label for="name"  class="col-sm-2 control-label">Customer:</label>
					<div class="col-sm-10">
						<input type="text" name="name" class="form-control" readonly="readonly" value="<c:out value='${customer.custFirstName}' /> <c:out value='${customer.custLastName}' />" />
					</div>
				</div>
				
				<div class="form-group">
					<label for="street1" class="col-sm-2 control-label">Address:</label>
					<div class="col-sm-10">
						<input type="text" name="street1" class="form-control" readonly="readonly" value="${customer.custStreetAddress1}" />
					</div>
				</div>
				
				<div class="form-group">
					<label for="cityStateZip" class="col-sm-2 control-label"></label>
					<div class="col-sm-10">
						<input type="text" class="form-control" readonly="readonly" name="cityStateZip"	value="<c:out value='${customer.custCity}' />, <c:out value='${customer.custState}' /> <c:out value="${customer.custPostalCode}" />" />
					</div>
				</div>
				
				<%-- <div class="form-group">
					<label for="creditCard" class="col-sm-2 control-label">Credit Card:</label>
					<div class="col-sm-10">
						<c:set var="last4"><c:out value="${lastFour}" /></c:set>
						<input type="text" name="creditCard" class="form-control" readonly="readonly" value="${last4}" />
					</div>
				</div>
				--%>
			</fieldset>
		</form> 
		
		<h3>Order Items:</h3>
		<hr />
		
		<table  class="table table-striped">
			<tr>
				<th>Item Name</th>
				<th>Quantity</th>
				<th>Price</th>
				<th>Line Total</th>
			</tr>
			<c:forEach var="item" items="${order.demoOrderItems}">
			<tr>
				<td><c:out value="${item.demoProductInfo.productName}" /></td>
				<td><c:out value="${item.quantity}" /></td>
				<td><fmt:formatNumber value="${item.demoProductInfo.listPrice}" type="currency"/></td>
				<td><fmt:formatNumber value="${item.demoProductInfo.listPrice * item.quantity}" type="currency"/></td>
			</tr>		
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
				<td>Order Total:</td>
				<td><fmt:formatNumber value="${order.orderTotal}" type="currency"/></td>
			</tr>
		</table>
		<form action="ConfirmOrder" method="post">
			<table class="form-group">
				<tr>
					<td class="col-sm-2">
							<input type="submit" value="Place Order" class="btn btn-default col-sm-2" />
					</td>
				</tr>
				
				<tr><td class="col-sm-2">&nbsp;</td></tr>
				
				<tr>
					<td class="col-sm-2">
						<a href="orderCart.jsp" class="btn btn-default col-sm-2">Adjust Order</a>
					</td>
				</tr>
			</table>
		</form>
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
</div><!-- wrap -->	
</body>
</html>