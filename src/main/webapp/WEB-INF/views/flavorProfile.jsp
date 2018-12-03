<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Let's find out what you like</h1>
<form action="/store-info">
<img src="${gif }">
<a href="/store-info?count=1" class="btn btn-secondary mb-2">Like</a>
<a href="/store-info?count=-1" class="btn btn-secondary mb-2">Dislike</a>
</form>
</body>
</html>


