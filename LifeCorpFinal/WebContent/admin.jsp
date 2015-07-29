<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="javax.servlet.ServletContext" %>
<%@page import="javax.servlet.RequestDispatcher" %>
<c:if test="${isAdmin != true}"><jsp:forward page="404"/></c:if>
<c:if test="${isAdmin == true}"> 
<html>
	<c:import url="/inserts/head.jsp" />
<body>
	<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">
		<h1>Administration:</h1>
		<div class="row">
			<div class="col-md-4">
				<h2>Customer List: </h2>
				<form action="AdminCustomerList" method="post">
					<input class="btn btn-primary col-sm-4 col-md-4" type="submit" value="Customer List"/>
				</form>
			</div>
			<div class="col-md-4">
				<h2>List of all Orders: </h2>
				<form action="AdminOrderList" method="post">
					<input class="btn btn-primary col-sm-4 col-md-4" type="submit" value="Order List" />
				</form>
			</div>
			<div class="col-md-4">
				<h2>Manage Products: </h2>
				<form action="AdminProductList" method="post">
				<input type="hidden" name="action" value="loadProducts" />
					<input class="btn btn-primary col-sm-4 col-md-4" type="submit" value="Product List" />
				</form>
			</div>
		
		</div>
		
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
	</div><!-- wrap -->
</body>
</html>
</c:if>