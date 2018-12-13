<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Registration</title>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
		integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
		crossorigin="anonymous">
	<link rel="stylesheet" href="style.css" />
	<%@include file="partials/header.jsp"%>
</head>
<body>
	<div class="container">
		<h2>Create new account: </h2>
		<p class = "message"> ${message}</p>
		<form id="login-form" action="/flavorprofile" method="get">
			<div class="row">
				<div class="col">
					<input type="text" class="form-control" placeholder="Username" name="username" required />
				</div>
				<div class="col">
					<input type="password" class="form-control" placeholder="Password" name="password" required />
				</div>
			</div>
			<div id="login-button">
					<button type="submit" class="btn btn-dark">Submit</button>
			</div>
		</form>
	</div>
</body>
</html>