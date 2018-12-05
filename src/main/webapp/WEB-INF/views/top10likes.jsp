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
	<div class="container-fluid">
		<H1>Gifs You've Liked</H1>

<<<<<<< Updated upstream:src/main/webapp/WEB-INF/views/pastlikegifs.jsp
		<c:forEach var="userlike" items="${Likes }">
			<ol>
				<li> ${userlike.tag }</li>
			</ol>
		</c:forEach>
	</div>
=======
	<H1>Gifs You've Liked</H1>
		<ol>
		<c:forEach var="userlike" items="${likes }">
		<li> ${userlike.tag } ${userlike.count}</li>

		</c:forEach>
		</ol>

>>>>>>> Stashed changes:src/main/webapp/WEB-INF/views/top10likes.jsp
</body>
</html>