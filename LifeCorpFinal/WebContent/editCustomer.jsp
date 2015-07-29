<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="model.State"%>
<%@page import="data.StateDB"%>
<%
    if(session.getAttribute("states") == null)
    {
    	List<State> states = StateDB.getAllStates();
    	session.setAttribute("states", states);
    }
%>

<html>
<head>
	<c:import url="/inserts/head.jsp" />
	<title>Edit Customer Detail</title>
</head>
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">
		<c:out value="${message}"/>
		<div class="jumbotron">
			<h1>Customer Information</h1>
		</div><!-- class.jumbotron -->
		
		<form class="form-horizontal" action="EditCustomerDetail" method="post" id="getCustomer" >
			<input type="hidden" name="url" value="editCustomer.jsp" />
			<fieldset>
				<legend>Edit Customer Data:</legend>
				<input type="hidden" name="action" value="newUser" />
				<input type="hidden" name="custId" value="<c:out value='${editCustomer.customerId}' />" />
				<div class="form-group">	
					<label class="col-sm-2 control-label" for="firstName">First Name:</label>
					<div class="col-sm-10">
						<input type="text" name="firstName" id="firstName" maxlength="50" class="form-control" value="<c:out value='${editCustomer.custFirstName}' />" required />
				</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="lastName">Last Name:</label>
					<div class="col-sm-10">
						<input type="text" name="lastName" id="lastName" maxlength="50" class="form-control" value="<c:out value='${editCustomer.custLastName}' />" required />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="email">Email:</label>
					<div class="col-sm-10">
						<input type="email" name="email" id="email" maxlength="50" class="form-control" value="<c:out value='${editCustomer.custEmail}' />" 
							pattern="^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$" 
							readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="phone">Phone:</label>
					<div class="col-sm-10">
						<input type="text" name="phone" id="phone" maxlength="50" class="form-control" value="<c:out value='${editCustomer.phoneNumber1}' />" required />
					</div>
				</div>
				<div class="form-group">		
					<label class="col-sm-2 control-label" for="password">Password:</label>
					<div class="col-sm-10">
						<input type="password" name="password" id="password" maxlength="50" class="form-control" value="<c:out value='${editCustomer.custPassword}' />" required />
					</div>
				</div>
				
				<span id="confirmMessage" class="col-sm-12 col-md-12"></span>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="passwordDup">Password Confirmation:</label>
					<div class="col-sm-10">
						<input type="password" name="passwordDup" id="passwordDup" maxlength="50" class="form-control" value="<c:out value='${editCustomer.custPassword}' />" required />
					</div>
				</div>
				<div class="form-group">				
					<label class="col-sm-2 control-label" for="creditLimit">Credit Limit</label>
					<div class="col-sm-10">
						<input type="number" name="creditLimit" maxlength="5" class="form-control" min="0" max="5000"
						value="<c:out value='${editCustomer.creditLimit}' />" required  />
					</div>
						
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="shipToStreet">Ship to Address:</label>
					<div class="col-sm-10">
						<input type="text" name="shipToStreet" id="shipToStreet" maxlength="50" class="form-control" value="<c:out value='${editCustomer.custStreetAddress1}' />" required />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="shipToCity">City:</label>
					<div class="col-sm-10">
						<input type="text" name="shipToCity" id="shipToCity" maxlength="50" class="form-control" value="<c:out value='${editCustomer.custCity}' />" required />
					</div>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-sm-2 control-label" for="shipToState">State:</label> -->
<!-- 					<div class="col-sm-10"> -->
<!-- 					<input type="text" name="shipToState" id="shipToState" maxlength="2" class="form-control" required /> -->
<!-- 					</div> -->
<!-- 				</div> -->

				<div class="form-group"> 
 					<label class="col-sm-2 control-label" for="shipToState">State:</label>
 					<div class="col-sm-10"> 
 					<select class="form-control" name="shipToState">
 					<c:forEach var="state" items="${states}">
							<option value="<c:out value='${state.stateAbbrv}'/>" <c:if test="${state.stateAbbrv == editCustomer.custState}">SELECTED</c:if> ><c:out value='${state.stateName}'/></option>
					</c:forEach>
					</select>
 					</div>
 				</div> 


				<div class="form-group">
					<label class="col-sm-2 control-label" for="shipToZip">Zip Code:</label>
					<div class="col-sm-10">
						<input type="text" name="shipToZip" id="shipToZip" maxlength="10" pattern="^(\d{5}|\d{5}\-\d{4})$" 
						class="form-control" value="<c:out value='${editCustomer.custPostalCode}' />" required />
					</div>
				</div>
<!--			<div class="form-group"> -->		
<!-- 				<label class="col-sm-2 control-label" for="billToStreet">Bill to Address:</label> -->
<!-- 				<div class="col-sm-10"> -->
<!-- 					<input type="text" name="billToStreet" maxlength="50" class="form-control" required /> -->
<!--				</div> -->
<!--			</div> -->

<!--			<div class="form-group"> -->		
<!-- 				<label class="col-sm-2 control-label" for="billToCity">City:</label> -->
<!-- 				<div class="col-sm-10"> -->
<!-- 					<input type="text" name="billToCity" maxlength="50" class="form-control" required /> -->
<!--				</div> -->
<!--			</div> -->

<!--			<div class="form-group"> -->		
<!-- 				<label class="col-sm-2 control-label" for="billToState">State:</label> -->
<!-- 				<div class="col-sm-10"> -->
<!-- 					<input type="text" name="billToState" maxlength="50" class="form-control" required /> -->
<!--				</div> -->
<!--			</div> -->

<!--			<div class="form-group"> -->		
<!-- 				<label class="col-sm-2 control-label" for="billToZip">Zip Code:</label> -->
<!-- 				<div class="col-sm-10"> -->
<!-- 					<input type="text" name="billToZip" maxlength="50" class="form-control" required /> -->
<!--				</div> -->
<!--			</div> -->

<!--			<label class="col-sm-2 control-label" for="message">&nbsp;</label> -->
				<c:if test="${message2 != null && message2 != ''}">
					<div class="form-group">
						<div class="col-sm-12 col-md-12">
							<c:set var="messagetwo"><c:out value="${message2}" /></c:set>
							<input type="text" name="message" value="${messagetwo}" class="alert alert-danger col-sm-12 col-md-12" readonly="readonly" />
						</div>
					</div>
				</c:if>
					
					
<!-- 			<input type="submit" value="Create Account" class="buttonFont" /> -->
<!-- 			input control below done with javaScript submit to test the password field match -->
				<div class="form-group">
	    			<div class="col-sm-offset-2 col-sm-10">
						<input type="submit" id="calculate" class="btn btn-primary col-sm-2 col-md-2" value="Update Info" />
<!-- 						<input type="button" id="calculate" class="btn btn-primary col-sm-2 col-md-2" value="Create Account" /> -->
					</div>
				</div>
			</fieldset>
		</form>
  	
	
	</div> <!-- /container -->
	<c:import url="/inserts/footer.jsp" />
	<!-- Local script for create account button -->
	<script type="text/javascript" src="scripts/checkPass.js" ></script>
</div><!-- wrap -->
	
	
	
</body>
</html>