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
	<title>Login</title>
</head>
<body>
<div id="wrap">
	<c:import url="/inserts/header.jsp" />
	<div class="container content">
		<c:out value="${message}"/>
		<div class="jumbotron">
			<h1>User Information</h1>
		</div><!-- class.jumbotron -->
		<h4>Returning customers please log in below:</h4>
		
		<form class="form-horizontal" action="Login" method="post">
			<input type="hidden" name="action" value="existingCustomer" />
			<fieldset>
			<legend>Returning Customers:</legend>
				<input type="hidden" name="action" value="existingUser" />
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">Email:</label>
					<div class="col-sm-10">
						<input type="email" name="email" maxlength="50" class="form-control" required />
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">Password:</label>
					<div class="col-sm-10">
						<input type="password" name="password" maxlength="50" class="form-control" required />
					</div>
				</div>
				<c:if test="${message1 != null && message1 != ''}">
					<div class="form-group">
						<div class="col-sm-12 col-md-12">
							<c:set var="messageone"><c:out value="${message1}" /></c:set>
							<input type="text" name="message" value="${messageone}" class="alert alert-danger col-sm-12 col-md-12" readonly="readonly" />
						</div>
					</div>
				</c:if>
				<div class="form-group">
    				<div class="col-sm-offset-2 col-sm-10">
						<input type="submit" value="Login" class="btn btn-primary col-sm-2 col-md-2" />
					</div>
				</div>
			</fieldset>
		</form>
		
		<h4>New customers please create an account below:</h4>
		<form class="form-horizontal" action="Login" method="post" id="getCustomer" >
			<input type="hidden" name="action" value="newCustomer" />
			<fieldset>
				<legend>New Customers:</legend>
				<input type="hidden" name="action" value="newUser" />
				<div class="form-group">	
					<label class="col-sm-2 control-label" for="firstName">First Name:</label>
					<div class="col-sm-10">
						<input type="text" name="firstName" id="firstName" maxlength="50" class="form-control" required />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="lastName">Last Name:</label>
					<div class="col-sm-10">
						<input type="text" name="lastName" id="lastName" maxlength="50" class="form-control" required />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="email">Email:</label>
					<div class="col-sm-10">
						<input type="email" name="email" id="email" maxlength="50" class="form-control" 
							pattern="^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$" required />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="phone">Phone:</label>
					<div class="col-sm-10">
						<input type="text" name="phone" id="phone" maxlength="50" class="form-control" required />
					</div>
				</div>
				<div class="form-group">		
					<label class="col-sm-2 control-label" for="password">Password:</label>
					<div class="col-sm-10">
						<input type="password" name="password" id="password" maxlength="50" class="form-control" required />
					</div>
				</div>
				
				<span id="confirmMessage" class="col-sm-12 col-md-12"></span>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for="passwordDup">Password Confirmation:</label>
					<div class="col-sm-10">
						<input type="password" name="passwordDup" id="passwordDup" maxlength="50" class="form-control" required />
					</div>
				</div>
<!--			<div class="form-group"> -->				
<!--				<label class="col-sm-2 control-label" for="creditCard">Credit Card</label> -->
<!-- 				<div class="col-sm-10"> -->
<!--					<input type="text" name="creditCard" maxlength="20" class="form-control" required  -->
<!--				</div> -->
<!--					pattern="^\d{4}-\d{4}-\d{4}-\d{4}$" /> -->
<!--			</div> -->
				<div class="form-group">
					<label class="col-sm-2 control-label" for="shipToStreet">Ship to Address:</label>
					<div class="col-sm-10">
						<input type="text" name="shipToStreet" id="shipToStreet" maxlength="50" class="form-control" required />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="shipToCity">City:</label>
					<div class="col-sm-10">
						<input type="text" name="shipToCity" id="shipToCity" maxlength="50" class="form-control" required />
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
							<option value="<c:out value='${state.stateAbbrv}'/>"><c:out value='${state.stateName}'/></option>
					</c:forEach>
					</select>
 					</div>
 				</div> 


				<div class="form-group">
					<label class="col-sm-2 control-label" for="shipToZip">Zip Code:</label>
					<div class="col-sm-10">
						<input type="text" name="shipToZip" id="shipToZip" maxlength="10" pattern="^(\d{5}|\d{5}\-\d{4})$" class="form-control" required />
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
						<input type="submit" id="calculate" class="btn btn-primary col-sm-2 col-md-2" value="Create Account" />
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