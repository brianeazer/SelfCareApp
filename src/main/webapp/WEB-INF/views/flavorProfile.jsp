<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Flavor Profile</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="style.css"/>
	<%@include file="partials/header.jsp"%>
</head>

<body id = "flavorbody">
	<div class="container">
		<h3 style="text-align:center;">Let's find out what you like:</h3>
		<form class="center">
			<p><img src="${ gif.gifUrl }"></p>
			<div id="gif-button">
				<a href="/store-info?count=1&id=${ gif.gfyId }" class="btn btn-dark mb-2">Like</a>
				<a href="/store-info?count=-1&id=${ gif.gfyId }" class="btn btn-dark mb-2">Dislike</a>
			</div>
		</form>
	</div>
</body>
</html>


