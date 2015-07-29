<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<c:import url="/inserts/head.jsp" />
</head>

<body BGCOLOR=lightgrey background=sunset.jpg id="index">
<div id="wrap">
	<c:import url="/inserts/header.jsp" />

	<div class="container index-container content">
	<div class="intro-header">
		<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3"></div>
		<form id="indexform" class="col-xs-6 col-sm-6 col-md-6 col-lg-6" action="login.jsp">
			<h1>Welcome to ....</h1><br />
			<h1><strong>LifeCorp Shopping</strong></h1> <br>
			<hr class="intro-divider" />
			<div class="buttons col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="form-group form-group-sm col-xs-12 col-sm-12 col-md-12 col-lg-12">
					
					<div class="col-xs-12 col-sm-1 col-md-1 col-lg-1"></div>
					<div class="form-group form-group-sm col-xs-12 col-sm-4 col-md-4 col-lg-4">
						<input class="btn btn-default btn-lg col-xs-12 col-sm-12 col-md-12 col-lg-12" type="button" value="Product List" onclick="location.href='DisplayProducts'">
					</div>
					
					<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2"></div>
					
					<div class="form-group form-group-sm col-xs-12 col-sm-4 col-md-4 col-lg-4">
						<input class="btn btn-default btn-lg col-xs-12 col-sm-12 col-md-12 col-lg-12" type="submit" value="Login" onclick="location.href='login.jsp'">
					</div>
					<div class="col-xs-12 col-sm-1 col-md-1 col-lg-1"></div>			
				</div>
			</div>
		
		</form>
		<marquee><h2><font>Enjoy shopping online.</font></h2></marquee>
		<div class="col-sm-3 col-md-3 col-lg-3"></div>
	</div>
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
</div><!-- wrap -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>