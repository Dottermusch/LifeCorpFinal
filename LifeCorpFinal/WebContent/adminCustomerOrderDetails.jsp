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
	
	<h1>Admin: Details of <c:out value="${custOrd.demoCustomer.custFirstName} ${custOrd.demoCustomer.custLastName}"/>'s Order: ID# <c:out value="${custOrd.orderId}"/></h1>
	<!-- <th>${ordItem.demoOrder.demoCustomer.custFirstName} ${ordItem.demoOrder.demoCustomer.custLastName}</th>-->
	<table class="table table-striped">
		<tr>
			<th class="col-sm-2"><span class="pull-right" >Order Item ID</span></th>
			<th class="col-sm-2"><span class="pull-right" >Product Name</span></th>
			<th class="col-sm-2"><span class="pull-right" >Quantity</span></th>
			<th class="col-sm-2"><span class="pull-right" >Unit Price</span></th>
			<th class="col-sm-3"><span class="pull-right" >Line Total</span></th>
			<th></th>
		</tr>
		<c:forEach var="ordItem" items="${ordItemList}">
			<tr>
				<td><span class="pull-right" ><c:out value="${ordItem.orderItemId}"/></span></td>
				<td><span class="pull-right" ><c:out value="${ordItem.demoProductInfo.productName}"/></span></td>
				<td><span class="pull-right" ><c:out value="${ordItem.quantity}"/></span></td>
				<td><span class="pull-right" ><fmt:formatNumber type="currency" value="${ordItem.unitPrice}"/></span></td>
				<td><span class="pull-right" ><fmt:formatNumber type="currency" value="${ordItem.quantity * ordItem.unitPrice}"/></span></td>
				<td></td>	
			</tr> 
		</c:forEach>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td><span class="pull-right" ><strong>Order Total</strong></span></td>
			<td class="col-sm-2"><span class="pull-right" ><fmt:formatNumber type="currency" value="${custOrd.orderTotal}"/></span></td>
			<td></td>
		</tr>
	</table>
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
	</div><!-- wrap -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
</body>
</html>
</c:if>