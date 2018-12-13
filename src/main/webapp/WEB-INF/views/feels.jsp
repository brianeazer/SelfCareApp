<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="now" class="java.util.Date" />
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.*,java.text.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Past Feelings</title>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
		integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
		crossorigin="anonymous">
	<link rel="stylesheet" href="style.css" />
	<%@include file="partials/header.jsp"%>
</head>
<body class ="feelsbody">
	<div class="container">
		<h2>You've visited us ${ days } days!</h2>
		<h3>You've used the app ${consecutiveDays } consecutive days!</h3>
		<table class="table">
			<c:forEach var="element" items="${ daysOfWeek }" varStatus="status">
			<tbody class="accordion">
				<tr>
					<th colspan="1"><button type="button" class="btn btn-dark btn-sm">view</button></th>
					<th><c:out value="${element.key}" /></th>
				</tr>
				</tbody>
			<tbody style="display:none">
				<tr>
					<td><strong>Average mood rating: </strong><fmt:formatNumber type="number" maxFractionDigits="2" value="${averageMoodRatings[status.index] }" />
					</td>
					<td><strong>Your most viewed category: </strong>${categories[status.index] }</td>
				</tr>
				<tr class="tr">
					<th scope="col">Time</th>
					<th scope="col">Mood(0-10)</th>
				</tr>
				<c:forEach var="emotion" items="${element.value}">
					<tr>
						<td scope="row"> <fmt:formatDate type = "time" 
        				 value = "${ emotion.date }" /></td>
						<td class="td">${ emotion.emotionRating }</td>
					</tr>
				</c:forEach>
				</tbody>
			</c:forEach>
		</table>
	</div>
</body>
<script>
	var acc = document.getElementsByClassName("accordion");
	var buttons = document.getElementsByClassName("btn btn-dark btn-sm");
	var i;
	
	for (i = 0; i < acc.length; i++) {
	  acc[i].addEventListener("click", function() {
		if (this.childNodes[1].childNodes[1].childNodes[0].innerHTML === "view"){
			this.childNodes[1].childNodes[1].childNodes[0].innerHTML = "close";
		} else{
			this.childNodes[1].childNodes[1].childNodes[0].innerHTML = "view"
		}
	    /* Toggle between adding and removing the "active" class,
	    to highlight the button that controls the panel */
		if (buttons[0].innerHTML==="view"){
			buttons.innerHTML = "close";
		}
	    /* Toggle between hiding and showing the active panel */
	    var panel = this.nextElementSibling;
	    if (panel.style.display === "") {
	      panel.style.display = "none";
	    } else {
	      panel.style.display = "";
	    }
	  });
	}
</script>
</html>