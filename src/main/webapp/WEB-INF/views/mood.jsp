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
<body id ="moodbody">
	<div class="container">
		${ message }
		<h1>Pick a gif category</h1>
		<form action="/mood-summary" name="mood-tracker" method="get">
		<div id="category-form">
			<c:forEach var="category" items="${ categories }">
				<input required name="category" type="radio" value="${ category }">${ category }<br>
			</c:forEach>
		</div>
			<div class="slidecontainer">
				<h4>How Are you Feeling?</h4>
				<input type="range" min="0" max="10" value="5" class="slider"
					id="myRange" name="slidervalue">
			</div>

			<div class="drakesadface">
				<img src="https://i.gifer.com/1AYf.gif" height="300px"
					;width="300px" ;
					style="float: left;"Sad">
			</div>

			<div class="drakeface">
				<img src="https://i.gifer.com/ywJ.gif" height="345px"
					; width="300px" ;
					style="float: right;"Happy">
			</div>



			<button class="button" style="position: absolute; float: right;">submit</button>


		</form>
	</div>




</body>
</html>