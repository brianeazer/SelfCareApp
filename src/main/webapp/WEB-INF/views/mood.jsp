<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" href="style.css" />
<%@include file="partials/header.jsp"%>
</head>
<body>

	<h1>Pick a gif category</h1>

	<div class="container">
		<form action="/gifs" name="mood-tracker" method="get">
			<c:forEach var="category" items="${ categories }">
				<input required="true" name="category" type="radio"
					value="${ category }">${ category }<br>
			</c:forEach>
			<div class="slidecontainer">
				<h4>How Are you Feeling?</h4>
				<input type="range" min="0" max="10" value="5" class="slider"
					id="myRange" name="slidervalue">
				<button>submit</button>
			</div>
		</form>
	</div>

	<a href="redirect:/mood?category=food" class="btn btn-secondary mb-2">Like</a>
	<a href="redirect:/mood?category=food" class="btn btn-secondary mb-2">Dislike</a>

	</div>

</body>
</html>