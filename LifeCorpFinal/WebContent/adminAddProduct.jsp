<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${isAdmin != true}"><jsp:forward page="404"/></c:if>
<c:if test="${isAdmin == true}">

<html>
<head>
	<c:import url="/inserts/head.jsp" />
	<title>Add Product</title>
</head>
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">
		<div class="jumbotron">
			<h1>New Product Information</h1>
		</div>
		
		<h4>First add the image:</h4>
		<div class="form-group">
		 <form method="post" action="AdminAddProduct" enctype="multipart/form-data">				
			<label class="col-sm-2 control-label" for="filename">File Name:</label>
			<div class="col-sm-10">
				<input type="file" name="uploadFile" class="form-control" /><br>
				<input type="submit" value="Upload" class="btn btn-primary col-sm-2 col-md-2" />
			</div>
		</form>				
		</div>
		<div class="form-group">
			<c:choose>
				<c:when test="${not empty uploadMessage}">
					<br>
					<label class="col-sm-2 control-label" for="mimetype">&nbsp;</label>
					<c:out value="${uploadMessage}"/>	
				</c:when>
				<c:otherwise>
					<label class="col-sm-2 control-label" for="mimetype">&nbsp;</label>
					<span>&nbsp;</span>
				</c:otherwise>
			</c:choose>
		</div>
		<h4>Then add remaining data:</h4>
		<form class="form-horizontal" action="AdminAddProduct" method="post" id="getCustomer" >
			<fieldset>
<!-- 				<legend>Edit Product Data:</legend> -->
				<input type="hidden" name="url" value="adminAddProduct" />
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
						  	<c:forEach var="c" items="${requestScope.adminMinusAllCategories}">
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
						  		<option value="Y" selected >Y</option>
						  		<option value="N" >N</option>
						</select>
					</div>
				</div>
				<div class="form-group">		
					<label class="col-sm-2 control-label" for="listPrice">List Price:</label>
					<div class="col-sm-10">
						<input type="number" step="0.01" name="listPrice" id="listPrice" maxlength="7" class="form-control" min="0" max="3000"
							required />
					</div>
				</div>
				
				<span id="confirmMessage" class="col-sm-12 col-md-12"></span>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="fileName">File Name:</label>
					<div class="col-sm-10">
					
					<c:choose>
						<c:when test="${not empty uploadedFileName}">
							<input type="text" name="fileName" id="fileName" value="<c:out value='${uploadedFileName}'/>" class="form-control" 
								readonly="readonly" required />	
						</c:when>
						<c:otherwise>
							<input type="text" name="fileName" id="fileName" value="" class="form-control" readonly="readonly" required />
						</c:otherwise>
					</c:choose>
					
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="mimetype">Mime Type:</label>
					<div class="col-sm-10">
						<input type="text" name="mimetype" id="mimetype" value="image/jpeg" maxlength="20" class="form-control" 
							readonly="readonly" required />
					</div>
				</div>
				

				<div class="form-group">
					<label class="col-sm-2 control-label" for="mimetype">&nbsp;</label>
					<c:out value="${message}"/>	
				</div>				
				<div class="form-group">
	    			<div class="col-sm-offset-2 col-sm-10">
						<input type="submit" id="calculate" class="btn btn-primary col-sm-2 col-md-2" value="Add Product" />
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
		
	</div> /container
	<c:import url="/inserts/footer.jsp" />
	<!-- Local script for create account button -->
	<script type="text/javascript" src="scripts/checkPass.js" ></script>
</div>wrap
	
	
	
</body>
</html>
</c:if>