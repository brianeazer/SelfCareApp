<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="partials/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

</head>
<body>
	<div class="container">
		<h1>Please fill out information</h1>

		<form action="/flavorprofile" method="get">
			<!-- GET/POST: Get is default. Post will clear out the URL -->
			<!-- using min & max sets the requirements on user input -->

			<p>
				Username:<input type="username" name="username">
			</p>
			<p>
				Password (only numbers and
				letters):<input name="password" type="password" required
					pattern="[a-z0-9]*">
			</p>
			<p>
				Re-Enter Password:<input name="password2" type="password" required
					pattern="[a-z0-9]*">
			</p>
			<p>
				<button class="button">Submit</button>
			</p>
		</form>
	</div>
</body>
</html>