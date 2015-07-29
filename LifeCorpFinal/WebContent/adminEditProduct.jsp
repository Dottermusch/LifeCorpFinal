<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${isAdmin != true}"><jsp:forward page="404"/></c:if>
<c:if test="${isAdmin == true}">

<html>
<head>
	<c:import url="/inserts/head.jsp" />
	<title>Edit Product Details</title>
</head>
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">
		<div class="jumbotron">
			<h1>Product Information</h1>
		</div>
		
		<h4>Edit Product Details Below:</h4>
		<br>
		<form class="form-horizontal" action="AdminEditProductDetail" method="post" id="getCustomer" >
			<input type="hidden" name="url" value="adminEditProduct.jsp" />
			<fieldset>
<!-- 				<legend>Edit Product Data:</legend> -->
				<input type="hidden" name="action" value="newUser" />
				<div class="form-group">	
					<label class="col-sm-2 control-label" for="productId">Product ID:</label>
					<div class="col-sm-10">
						<input type="text" name="productId" class="form-control" value="<c:out value='${editProduct.productId}' />" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">	
					<label class="col-sm-2 control-label" for="productName">Product Name:</label>
					<div class="col-sm-10">
						<input type="text" name="productName" id="productName" maxlength="50" class="form-control" value="<c:out value='${editProduct.productName}' />" required />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="productDescription">Product Description:</label>
					<div class="col-sm-10">
						<input type="text" name="productDescription" id="productDescription" maxlength="2000" class="form-control" value="<c:out value='${editProduct.productDescription}' />" required />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="productCategory">Product Category:</label>
					<div class="col-sm-10">
						<select class="dropdown-toggle col-sm-2" name="productCategory">
						  	<c:forEach var="c" items="${adminCategories}">
						  		<option value="<c:out value='${c}'/>" <c:if test="${editProduct.category == c}">Selected</c:if>><c:out value="${c}"/></option>
						  	</c:forEach>
						</select>
						<div class="col-sm-2 text-right" >Add Category >>></div>
<!-- 						<div class="col-sm-2" > -->
							<input type="checkbox" name="addCategory" id="addCategory" class="col-sm-1" onclick="dynInput(this);" />
<!-- 						</div> -->
<!-- 						<div class="col-sm-3" > -->
							<input type="hidden" name="newCategory" id="newCategory" class="col-sm-3" required />
<!-- 						</div> -->
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="productAvail">Availability:</label>
					<div class="col-sm-10">
						<select class="dropdown-toggle" name="productAvail">
						  		<option value="Y" <c:if test="${editProduct.productAvail == 'Y'}">Selected</c:if>>Y</option>
						  		<option value="N" <c:if test="${editProduct.productAvail == 'N'}">Selected</c:if>>N</option>
						</select>
					</div>
				</div>
				<div class="form-group">		
					<label class="col-sm-2 control-label" for="listPrice">List Price:</label>
					<div class="col-sm-10">
						<input type="number" step="0.01" name="listPrice" id="listPrice" maxlength="7" class="form-control" min="0" max="3000"
							value="<c:out value='${editProduct.listPrice}' />" required />
					</div>
				</div>
				
				<span id="confirmMessage" class="col-sm-12 col-md-12"></span>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="productImage">Product Image:</label>
					<div class="col-sm-10">
						<img src="img/<c:out value='${editProduct.filename}'/>" alt="<c:out value='${editProduct.filename}'/> Image" style="max-height: 200px;">
					</div>
				</div>
				<div class="form-group">				
					<label class="col-sm-2 control-label" for="filename">File Name:</label>
					<div class="col-sm-10">
						<input type="text" name="filename" maxlength="5" class="form-control" value="<c:out value='${editProduct.filename}' />" required readonly="readonly"  />
					</div>				
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="mimetype">Mime Type:</label>
					<div class="col-sm-10">
						<input type="text" name="mimetype" id="mimetype" maxlength="50" class="form-control" value="<c:out value='${editProduct.mimetype}' />" 
							readonly="readonly" required />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="imageLastUpdate">Last Updated:</label>
					<div class="col-sm-10">
						<input type="text" name="imageLastUpdate" id="imageLastUpdate" class="form-control" 
						value="<fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" 
						value="${editProduct.imageLastUpdate}" />" readonly="readonly" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="mimetype">&nbsp;</label>
					<c:out value="${message}"/>	
				</div>				
				<div class="form-group">
	    			<div class="col-sm-offset-2 col-sm-10">
						<input type="submit" id="calculate" class="btn btn-primary col-sm-2 col-md-2" value="Update Info" />
					</div>
				</div>
				<script type="text/javascript" src="scripts/createInputField.js" ></script>
			</fieldset>
		</form>
  		<form action="AdminProductList" method="post" class="form-horizontal">
  			<label class="col-sm-2 control-label" for="mimetype">&nbsp;</label>
  			<input type="hidden" name="action" value="loadProducts" />
  			<input type="submit" class="btn btn-primary col-sm-2 col-md-2" value="Product List" />
  		</form>
  		<br>
  		<br>
  		<br>
  		<form action="AdminAddProduct" method="post" class="form-horizontal">
  			<label class="col-sm-2 control-label" for="mimetype">&nbsp;</label>
  			<input type="hidden" name="action" value="loadProducts" />
  			<input type="submit" class="btn btn-primary col-sm-2 col-md-2" value="New Product" />
  		</form>
		
	</div> /container
	<c:import url="/inserts/footer.jsp" />
	<!-- Local script for create account button -->
	<script type="text/javascript" src="scripts/checkPass.js" ></script>
</div>wrap
	
	
	
</body>
</html>
</c:if>