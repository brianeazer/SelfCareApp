<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" href="style.css" />
<%@include file="partials/header.jsp"%>
</head>
<body>
	<div class="table">
		<table>
			<tr class="tr">
				<td>Emotion</td>
				<td>Time/Frequency</td>
			</tr>

		</table>
		<table class="table">
			<tr class="tr">
				<th class="th">Date</th>
				<th>Mood</th>
			</tr>
			<c:forEach var="ue" items="${userEmotions }">
				<trclass="tr">
					<td>${ ue.date }</td>
					<td>${ ue.emotionRating }</td>
				</tr>

			</c:forEach>
			
		</table>
	</div>

</body>
</html>