<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Let's find out what you like</h1>
<form action="store-info">
<img src="${gif }">
<div name="like" id="like">
	<a href="/flavorprofile">Like</a>
</div>
<div name="dislike" id="dislike">
	<a href="/flavorprofile">Dislike</a>
</div>
</form>
</body>
</html>