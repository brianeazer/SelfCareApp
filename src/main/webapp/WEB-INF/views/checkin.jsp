<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>How are you feeling now?</h1>
	
	<p> <button> <a onclick="history.back()">Same group of memes</a> </button> </p>
	
	<form action="/gifs" name="mood-tracker" method="get">
			<c:forEach var="category" items="${ categories }">
				<input required name="category" type="radio"
					value="${ category }">${ category }<br>
			</c:forEach>
			<div class="slidecontainer">
				<h4>How Are you Feeling?</h4>
				<input type="range" min="0" max="10" value="5" class="slider"
					id="myRange" name="slidervalue">
				<button>submit</button>
			</div>
		</form>
			
</body>
</html>