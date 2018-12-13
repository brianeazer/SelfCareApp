<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Your Mood</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="style.css" />
	<%@include file="partials/header.jsp"%>
</head>
<body class ="moodbody">
	<div class="container" id="mood-div">
		${ message }
		<h2 style="text-align:center;">Pick a Category:</h2>
		<form action="/mood-summary" name="mood-tracker" method="get" id="mood-tracker">

			<div id="category-form">
				<c:forEach var="category" items="${ categories }">
					<input required name="category" type="radio" value="${ category }">${ category }<br>
				</c:forEach>
			</div>
			
			<div class="slidecontainer">
				<h2>How Are You Feeling?</h2>
				<h4>(0-10)</h4>
				<input type="range" min="0" max="10" value="5" class="slider"
					id="myRange" name="slidervalue">
			
				<div style="width: 100%; text-align: center; position: relative;">
					<div style="width: 100%; text-align: center; position: absolute; top: 0;">
						<button class="button">Submit</button>
					</div>
					<img src="https://i.gifer.com/1AYf.gif" height="200px" width="300px" style="float: left" alt="Sad Drake">	
					<img src="https://i.gifer.com/ywJ.gif" height="230px" width="200px"	style="float:right" alt="Happy Drake">
				</div>
			</div>
			
		</form>
	</div>
</body>
</html>