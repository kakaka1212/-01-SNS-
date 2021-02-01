<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ついっと|ログイン</title>
<link rel="stylesheet" href="css/style.css">
<script src="js/jquery-3.5.1.min.js"></script>
</head>
<body>

	<div id="login-button">
	  <img src="img/airplane.png"></img>
	</div>

	<div>
		<% if(request.getAttribute("message") != null) { %>
			<div><%=request.getAttribute("message") %></div>
		<% }%>
	</div>

	<div id="container" class="con1">
		<h1>Log in</h1>

		<form action="login" method="post">
			<input type="text" name="userId" placeholder="Userid" value="">
			<input type="password" name="password" placeholder="Password" value="">
		    <a><input type="submit" value="Log in"></a>
		    <a href="index.html">Top</a>
		</form>
	</div>

	<!-- Javascript -->
	<script src="js/Suchas_ac.js"></script>

</body>
</html>