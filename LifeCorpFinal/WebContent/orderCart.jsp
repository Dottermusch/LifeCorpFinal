<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<c:import url="/inserts/head.jsp" />
		<title>Display Products</title>
	</head>
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">
		<c:out value="${message}" />
		<div class="jumbotron">
			<h1>Your cart</h1>
		</div>
			<table class="table table-striped">
				<tr>
			    <th>Quantity</th>
				<th>Description</th>
				<th>Price</th>
				<th>Amount</th>
				<th>Total</th>
				<th class="wide"></th>
			  </tr>

			<c:forEach var="item" items="${order.demoOrderItems}">
				<tr>
					<td class="col-sm-4">
<%--					<form action="" method="post"> SEEMS WIERD TO HAVE ACTION AS BLANK --%>
						<form class="form-horizontal" action="OrderBuilderServlet" method="post">
							<input type="hidden" name="productId" value="<c:out value='${item.demoProductInfo.productId}'/>">
							<div class="col-sm-6">
								<input type="number" name="quantity" class="form-control" <%--  class="right narrow"--%> value="<c:out value='${item.quantity}'/>" id="quantity" maxlength="2" min="1" max="20" />
							</div>
							
							<input type="submit" value="Update" class="btn btn-default  col-sm-5" >
							<div class="col-sm-1"></div>
						</form>
					</td>
					
					<td class="description" ><c:out value='${item.demoProductInfo.productName}'/></td>
					<td class="right"><fmt:formatNumber value="${item.demoProductInfo.listPrice}" type="currency"/></td>
					<td class="right"><c:out value='${item.quantity}'/></td>
					
<%--				Can do this two ways - one with formatting an object variable the other using a String method	--%>
<%--			    <td class="right"> <fmt:formatNumber value="${item.total}" type="currency"/> </td>	--%>
					<td class="right"><fmt:formatNumber value="${item.quantity * item.demoProductInfo.listPrice}" type="currency" /></td>
					<td>
						<form class="form-horizontal" action="OrderBuilderServlet" method="post">
							<input type="hidden" name="productId" value="<c:out value='${item.demoProductInfo.productId}'/>">
				        	<input type="hidden" name="quantity" value="0">
				        	<div  class="col-sm-2"></div>
				        	<input type="submit" value="Remove Item" class="btn btn-default col-sm-7" >
				        	<div  class="col-sm-3"></div>
				    	</form>
				    </td>
				</tr>
			</c:forEach>
			</table>
			
			<p><strong>To change the quantity</strong>, enter the new quantity and click on the Update button.</p>
			
			<form class="form-horizontal" action="DisplayProducts" method="post">
				<input type="hidden" name="action" value="shop">
				<div class="form-group">
    				<div class="col-sm-offset-0 col-sm-7">
					<input type="submit" value="Continue Shopping" class="btn btn-default col-sm-3" >
					</div>
				</div>
			</form>
			
			<form class="form-horizontal" action="OrderBuilderServlet" method="post">
				<input type="hidden" name="action" value="checkout">
				<div class="form-group">
    				<div class="col-sm-offset-0 col-sm-7">
						<input type="submit" value="Checkout" class="btn btn-default col-sm-3" > 
					</div>
				</div>
			</form>
		
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
</div><!-- wrap -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</body>
</html>