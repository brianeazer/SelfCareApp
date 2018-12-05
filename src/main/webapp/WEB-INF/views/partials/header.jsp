<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

</head>


<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-*"
		style="background-color: black; padding: 30px; font-size: 1em;">
		<a class="navbar-brand" href="/" style="font-size: 2em;"></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link" href="/">Welcome ${ user.username }</a></li>
				<c:if test="${ empty user }">
				<li class="nav-item"><a class="nav-link" href="/register">Register</a></li>


				<li class="nav-item"><a class="nav-link" href="/">Login</a>
				</li>
				</c:if>
				<c:if test="${not empty user }">
				<li class="nav-item"><a class="nav-link" href="/logout">Logout</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="/mood">Mood</a>
				</li>
				</c:if>

			</ul>
		</div>
	</nav>

</body>
</html>