<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>GIFs</title>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
		integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
		crossorigin="anonymous">
	<link rel="stylesheet" href="style.css" />
	<%@include file="partials/header.jsp"%>
</head>
<body class = "gifsbody">
	<div class="container">
		<form method="get" class="center">
			<h2>Category: ${ category }</h2>
			<div style="margin-left:auto; margin-right:auto; text-align:center; max-height:15%;">
				<img src="${ gif }"> 
			</div>
			<div id="gif-button">
				<a href="/random-store-info?category=${ category }&count=1&id=${ gifId }"
					class="btn btn-dark mb-2">Like</a> 
				<a href="/random-store-info?category=${ category }&count=-1&id=${ gifId }"
					class="btn btn-dark mb-2">Dislike</a>
			</div>
		</form>
		
		<div id="back-menu">
			<p> <a href="/mood">Back to mood</a> </p>

			<form>
				<select name="category">
			    <c:forEach items="${categories}" var="userLike">
			        <option value="${userLike}"> ${userLike} </option>
			        <a onclick="gifs?category=${userLike}"></a>
			    </c:forEach>
				</select>
					<input type="submit" class="btn btn-dark btn-sm" value="Submit">
			</form>
		</div>
	</div>
</body>
</html>