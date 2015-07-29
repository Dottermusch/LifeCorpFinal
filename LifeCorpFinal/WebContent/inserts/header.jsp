<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header id="header">
		<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
			<div class="container topnav">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
					</button>
					<a class="navbar-brand topnav" href="index.jsp">LifeCorp</a>
				</div>		
				<div id="navbar bs-example-navbar-collapse-1" class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<c:if test="${isAdmin == null || isAdmin == false}">
							<li class="active"><a href="index.jsp">Home</a></li>
						</c:if>
						
						<c:if test="${isAdmin == true}">
							<li class="active"><a href="admin.jsp">Admin Home</a></li>
						</c:if>
						
						<c:if test="${customer != null}">
								<li><a href="CustomerOrderHistory">Order History</a></li>
						</c:if>
						
<%-- 						added view cart control --%>
						<c:if test="${order != null}">
								<li><a href="orderCart.jsp">View Cart</a></li>
						</c:if>
						
						<c:choose>
							<c:when test="${customer != null || isAdmin == true}">
								<li><a href="LogOut">Log Out</a></li>
							</c:when>
								
							<c:otherwise>
								<li><a href="login.jsp">Login</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div><!--/.nav-collapse -->	
			</div>
		</nav>		
		<br />
		<br />
		<br />
		<br />
	</header>