<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
	<c:import url="/inserts/head.jsp" />
	</head>
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">
	    <c:out value="${message}"/>
	    <div class="jumbotron">
			<h1>Welcome to LifeCorp</h1>
		</div>
		  <h3>Please select from the product list below:</h3>
		  <form action="DisplayProducts" method="post">
		  <select class="dropdown-toggle" name="category">
		  	<c:forEach var="c" items="${productCategories}">
		  		<option value="<c:out value='${c}'/>" <c:if test="${productCategory == c}">Selected</c:if>><c:out value="${c}"/></option>
<%-- 		  		<option value="Accessories" <c:if test="${productCategory == 'Accessories'}">Selected</c:if>>Accessories</option> --%>
<%-- 				<option value="Mens" <c:if test="${productCategory == 'Mens'}">Selected</c:if>>Mens</option> --%>
<%-- 				<option value="Womens" <c:if test="${productCategory == 'Womens'}">Selected</c:if>>Womens</option>		  	 --%>
		  	</c:forEach>
		  </select>
		  		  	
		  <input style="margin-left:3em;" class="btn btn-default" type="submit" value="View Category" />
		  </form>
			<table class="table table-striped">
			    <tr>
			        <th>Description</th>
			        <th class="right">Price</th>
			        <th>&nbsp;</th>
			        <th>&nbsp;</th>
			    </tr>
		    <c:forEach var="p" items="${products}">
			    <tr>
			        <td><c:out value='${p.productName}'/></td>
			        <td class="right"><fmt:formatNumber value="${p.listPrice}" type="currency"/></td>
			        
			        <td><form action="Details" method="post">
			        	<input type="hidden" name="action" value="productDetail">
			            <input type="hidden" name="productId" value="<c:out value='${p.productId}'/>">
			                <input class="btn btn-default" type="submit" value="See Detail" >
			            </form></td>
			        
			        
			        <td>
			        	<form action="OrderBuilderServlet" method="post">
			        	<input type="hidden" name="action" value="cart">
			            <input type="hidden" name="productId" value="<c:out value='${p.productId}'/>">
			                <input class="btn btn-default" type="submit" value="Add To Cart">
			            </form>
			       </td>
			    </tr>
		    </c:forEach>
			</table>
  	
  		
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</div><!-- wrap -->
</body>
</html>