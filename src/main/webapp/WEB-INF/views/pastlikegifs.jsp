<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<H1>Gifs You've Liked</H1>

	<ol>
		<c:forEach var="userLikes" items="${userLikes }">
			<li><img src="${userLikes}" alt="this is a gif you liked"></li>
		</c:forEach>
	</ol>

</body>
</html>