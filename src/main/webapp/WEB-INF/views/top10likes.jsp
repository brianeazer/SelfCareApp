<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Top 10 Likes</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="style.css" />
	<%@include file="partials/header.jsp"%>
</head>
<body>
	<div class="container" style="text-align:center;">
		<h3>Your top 10 liked tags of all time:</h3>
		<div class = "orderedlist">
			<table class="table table-bordered">
				<thead class="thead-dark">
					<tr scope="row">
						<th scope="col">Tag:</th>
						<th scope="col">Likes:</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="userlike" items="${ likes }">
						<tr>
							<td>${ userlike.tag }</td>
							<td>${ userlike.count }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="text-align:center;">
			<form>
				<img src="${ gif.gifUrl }">
				<div>
					<a href="/top10-store-info?count=1&id=${ gif.gfyId }" class="btn btn-secondary mb-2">Like</a>
					<a href="/top10-store-info?count=-1&id=${ gif.gfyId }" class="btn btn-secondary mb-2">Dislike</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>