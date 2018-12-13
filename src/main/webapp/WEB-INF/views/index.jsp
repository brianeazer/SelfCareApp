<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Home Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="style.css" />
	<%@include file="partials/header.jsp"%>
</head>
<body  id ="indexbody">
	<div class="container" id="index-div">
		<h1 id="intro-message">Just hold on, calm is coming home.</h1>
		<h2>${ message } </h2>
		<div>
			<form id="login-form" action="/" method="post">
				<div class="row">
					<div class="col">
						<input type="text" class="form-control" placeholder="Username" name="username" />
					</div>
				
					<div class="col">
						<input type="password" class="form-control" placeholder="Password" name="password" />
					</div>
				</div>
				<div id="login-button">
					<button type="submit" class="btn btn-dark">Submit</button>
				</div>
			</form>
			
			
		</div>
		<%--<div id="christmas">
		  <div class="flake large f-1"></div>
		  <div class="flake large f-2"></div>
		  <div class="flake large f-3"></div>
		  <div class="flake large f-4"></div>
		  <div class="flake large f-5"></div>
		  <div class="flake large f-6"></div>
		  <div class="flake large f-7"></div>
		  <div class="flake large f-8"></div>
		  <div class="flake large f-9"></div>
		  <div class="flake large f-10"></div>
		  <div class="flake large f-11"></div>
		  <div class="flake large f-12"></div>
		  <div class="flake large f-13"></div>
		  <div class="flake large f-14"></div>
		  <div class="flake large f-15"></div>
		  <div class="flake large f-16"></div>
		  <div class="flake large f-17"></div>
		  <div class="flake f-18"></div>
		  <div class="flake f-19"></div>
		  <div class="flake f-20"></div>
		  <div class="flake f-21"></div>
		  <div class="flake f-22"></div>
		  <div class="flake f-23"></div>
		  <div class="flake f-24"></div>
		  <div class="flake f-25"></div>
		  <div class="flake f-26"></div>
		  <div class="flake f-27"></div>
		  <div class="flake f-28"></div>
		  <div class="flake f-29"></div>
		  <div class="flake f-30"></div>
		  <div class="flake f-31"></div>
		  <div class="tree left">
		    <div class="snow"></div>
		  </div>
		  <div class="tree right">
		    <div class="snow"></div>
		  </div>
		  <div class="ground"></div>
		</div>--%>
	</div>
</body>
</html>