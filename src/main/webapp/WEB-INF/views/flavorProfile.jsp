<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="style.css" />
	<%@include file="partials/header.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<h1>Let's find out what you like</h1>
		<form>
			<p><img src="${gif.gifUrl }"></p>
			<a href="/store-info?count=1&id=${gif.gfyId }" class="btn btn-secondary mb-2">Like</a>
			<a href="/store-info?count=-1&id=${gif.gfyId }" class="btn btn-secondary mb-2">Dislike</a>
		</form>
	</div>
</body>
</html>


