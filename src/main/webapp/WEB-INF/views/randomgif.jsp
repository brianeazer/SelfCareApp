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
	<img src="${gifUrl }">
	<a href="/randomgif?count=1&id=${gfyId }" class="btn btn-secondary mb-2">Like</a>
	<a href="/randomgif?count=-1&id=${gfyId }" class="btn btn-secondary mb-2">Dislike</a>
	
	<c:if test="${ not empty userId }">
	<div name="id">${gifId}</div>
	</c:if>
	
</body>
</html>