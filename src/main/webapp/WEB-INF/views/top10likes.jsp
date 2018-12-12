<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="style.css" />
	<%@include file="partials/header.jsp"%>
</head>
<body>
	<div class="container-fluid" >

		<h1>Your top 10 liked tags of all time</h1>
		<div class = "orderedlist">
			<c:forEach var="userlike" items="${likes }">
				<li>${userlike.tag } ${userlike.count}</li>
			</c:forEach>
		</div>
		<form>
			<p ><img src="${gif.gifUrl }"></p>
			<a href="/top10-store-info?count=1&id=${ gif.gfyId }" class="btn btn-secondary mb-2">Like</a>
			<a href="/top10-store-info?count=-1&id=${ gif.gfyId }" class="btn btn-secondary mb-2">Dislike</a>
		</form>
	</div>
</body>
</html>