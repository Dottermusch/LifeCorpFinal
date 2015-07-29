<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
	<c:import url="/inserts/head.jsp" />
	<title>Confirm Order</title>
	</head>
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">
		<div class="jumbotron">
			<h1>Order Confirmation</h1>
		</div>
		<c:choose>
			<c:when test="${message != null}">
				<div class="alert alert-danger"><c:out value="${message}"/></div>
			</c:when>
			<c:otherwise>
				<h3>Your order number is: <c:out value="${orderId}"/></h3>						
			</c:otherwise>
		</c:choose>
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</div><!-- wrap -->
</body>
</html>