<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${isAdmin != true}"><jsp:forward page="404"/></c:if>
<c:if test="${isAdmin == true}">
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
			<h1>LifeCorp Product Editor</h1>
		</div>
		  <h3>Please select a product below to edit:</h3>
		  <form action="AdminProductList" method="post">
		  <input type="hidden" name="action" value="categoryChanged" />
		  <select class="dropdown-toggle" name="category">
		  	<c:forEach var="c" items="${adminCategories}">
		  		<option value="<c:out value='${c}'/>" <c:if test="${adminCategory == c}">Selected</c:if>><c:out value="${c}"/></option>
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
		    <c:forEach var="p" items="${adminProducts}">
			    <tr>
			        <td><c:out value='${p.productName}'/></td>
			        <td class="right"><fmt:formatNumber value="${p.listPrice}" type="currency"/></td>
			        
			        <td><form action="Details" method="post">
			        	<input type="hidden" name="action" value="productDetail">
			            <input type="hidden" name="productId" value="<c:out value='${p.productId}'/>">
			                <input class="btn btn-default" type="submit" value="See Detail" >
			            </form></td>
			        			        
			        <td>
			        	<form action="AdminProductList" method="post">
			        	<input type="hidden" name="action" value="productEdit">
			            <input type="hidden" name="productId" value="<c:out value='${p.productId}'/>">
			                <input class="btn btn-default" type="submit" value="Edit Product">
			            </form>
			       </td>
			    </tr>
		    </c:forEach>
			</table>
<!-- 			Add Button to go to "Add Product jsp page" -->
  		<form action="AdminAddProduct" method="post" class="form-horizontal">
  			<input type="submit" class="btn btn-primary col-sm-2 col-md-2" value="New Product" />
  		</form>
  		
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</div><!-- wrap -->
</body>
</html>
</c:if>