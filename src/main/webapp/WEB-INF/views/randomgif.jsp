<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" href="style.css" />
<%@include file="partials/header.jsp"%>
</head>
<body>
	<div class="container" id="show-gif">
		<form method="get">
			<h2>Category: ${ category }</h2>
			<img src="${ gif }" class="center" height="20%"> 
			<a href="/random-store-info?category=${ category }&count=1&id=${ gifId }"
				class="btn btn-secondary mb-2">Like</a> 
			<a href="/random-store-info?category=${ category }&count=-1&id=${ gifId }"
				class="btn btn-secondary mb-2">Dislike</a>
		</form>
		<p> </p>
		<p>	<a href="/mood">Back to mood</a> </p>
		
		<form>
		<select name="category">
	    <c:forEach items="${categories}" var="userLike">
	        
	        <option <%-- <c:if test="Your Top Ten"><a onclick="gifs?category=Your+Top+Ten"></a></c:if>  --%>
	        
	        value="${userLike}">${userLike} </option>
	        <a onclick="gifs?category=${userLike}"></a>
	        
	    </c:forEach>
		</select>
			<input type="submit" value="Submit">
		</form>
		
	</div>
</body>
</html>