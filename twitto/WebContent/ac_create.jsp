<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ついっと|アカウント作成</title>
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

	<div id="container" class="con2">
		<h1>create</h1>

		<form action="create" method="post">
			<input type="text" name="name" placeholder="Name" value="" required>
			<input type="text" name="userId" placeholder="Userid" value="<%=request.getAttribute("userId") %>" required>
			<input type="password" name="password" placeholder="Password" value="" required>
			<textarea maxlength="100" name="self" placeholder="Self-introduction"></textarea>
			<a><input type="submit" value="Create"></a>
			<a href="index.html">Top</a>
		</form>
	</div>

	<!-- Javascript -->
	<script src="js/Suchas_ac.js"></script>

</body>
</html>