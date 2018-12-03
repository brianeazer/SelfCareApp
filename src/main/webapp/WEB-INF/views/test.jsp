<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Gifs</h1>
<ol>
	<c:forEach var="gif" items="${gifs }">
		<li>
			<img src="${gif}" alt="this is a gif">
		</li>
	</c:forEach>
</ol>
</body>
</html>