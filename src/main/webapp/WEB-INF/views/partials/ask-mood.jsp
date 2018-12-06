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
</head>
<body>
	<div>
		<form name="mood-tracker" method="post">
			<c:forEach var="category" items="${ categories }">
				<input name="category" type="radio" value="${ category }">${ category }<br>
			</c:forEach>
			<button>See GIFs</button>

		</form>
	</div>
	<div class="slidecontainer">
	<form action="/mood">
		<input type="range" min="0" max="10" value="5" class="slider"
			id="myRange" name="slidervalue">
		<button>submit</button>
	</form>
		<h4>How Are you Feeling?</h4>
		<!-- </input> -->
	</div>


</body>
</html>