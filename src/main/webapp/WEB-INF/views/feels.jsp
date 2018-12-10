<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="now" class="java.util.Date" />
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.*,java.text.*"%>
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
	<div class="container">
		<table class="table">
			<c:forEach var="ue" items="${ daysOfWeek }">
				<tr>
					<th colspan="2"><c:out value="${ue.key}"/></th>
				</tr>
				<tr class="tr">
					<th>Time</th>
					<th>Mood(0-10)</th>
				</tr>
				<c:forEach var="emotion" items="${ue.value}">
					<tr>
						<td class="td">${ emotion.date }</td>
						<td class="td">${ emotion.emotionRating }</td>
					</tr>
				</c:forEach>
			</c:forEach>

		</table>
	</div>
</body>
</html>