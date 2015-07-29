<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
	<c:import url="/inserts/head.jsp" />
	</head>
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content" >
	
	
	
	<p> We are very sad because you do not have any history with us.</p>
	<p>Start shopping from our product list!</p>
	<form id="indexform" class="col-xs-6 col-sm-6 col-md-6 col-lg-6" action="DisplayProducts">
		<div style="min-width:8em;" class="form-group form-group-sm col-xs-12 col-sm-4 col-md-4 col-lg-4">
			<input style="min-width:8em;" class="btn btn-primary btn-lg col-xs-12 col-sm-12 col-md-12 col-lg-12" type="submit" value="Product List">
		</div>
	</form>
	
	<p class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="color:#0066FF">Thank you, for visiting us.</p>
	
	
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
</div><!-- wrap -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>