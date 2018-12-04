<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<H1>Gifs You've Liked</H1>

		<c:forEach var="like" items="${likes }">
		<ol>
		<li> TEST${like.like.tag }</li>
	</ol>
		</c:forEach>

</body>
</html>