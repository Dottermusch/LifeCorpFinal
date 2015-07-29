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
				<h1>Product Details</h1>
		</div>
		<h3>Below are additional details for <c:out value='${productDetails.productName}'/></h3>
		<form class="form-horizontal">
		<div class="col-sm-4">	
			<img src="img/<c:out value='${productDetails.filename}'/>" alt="<c:out value='${productDetails.productName}'/> Image" style="max-height: 200px;">
		</div>
		
		
		<div class="form-group col-sm-8">
			<h3 class="col-sm-9">	<c:out value='${productDetails.productName}'/></h3>
		</div>
		
		<div class="col-sm-12"></div>
		
		<div class="form-group">
			<label for="productCategory" class="col-sm-2 control-label">Category:</label>
			<div class="col-sm-10">	
				<input type="text" name="productCategory" value="<c:out value='${productDetails.category}'/>" class="form-control" readonly="readonly" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="listPrice" class="col-sm-2 control-label">Price:</label>
			<div class="col-sm-10">	
				<input type="text" name="listPrice" value="<fmt:formatNumber value='${productDetails.listPrice}' type="currency"/>" class="form-control" readonly="readonly" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="productAvailability" class="col-sm-2 control-label">Availability:</label>
			<div class="col-sm-10">
				<input type="text" name="productAvailability" value="<c:out value='${productDetails.productAvail}'/>" class="form-control" readonly="readonly" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="productDescription" class="col-sm-2 control-label">Description:</label>
			<div class="col-sm-10">
				<input name="productDescription" class="form-control"  value="<c:out value='${productDetails.productDescription}'/>" readonly="readonly" />
			</div>
		</div>
		</form>
		<a href="DisplayProducts"><button class="btn btn-default" >Return to Products</button></a>
		
	
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
</div><!-- wrap -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>