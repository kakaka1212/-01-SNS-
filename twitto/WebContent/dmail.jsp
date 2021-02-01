<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ついっと｜メール</title>
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/style_mail.css">
<script src="js/jquery-3.5.1.min.js"></script>
</head>
<body>

	<!-- サイドメニュー -->
	<nav>
		<div class="menu">
			<p class="home"><button onClick="location.href='http://localhost:8080/D20201006_keiji/index.html'" class="homeBtn">
				<img src="img/airplane.png" alt="TOP">
			</button></p>
			<p>
				<a href="follow">
					<img src="img/account.png" alt="フォロー" class="menuImg">
					<span>ホーム</span>
				</a>
			</p>
			<p>
				<a href="account">
					<img src="img/follow.png" alt="アカウント" class="menuImg">
					<span>プロフィール</span>
				</a>
			</p>
			<p>
				<a href="mail?addressId=<%= request.getAttribute("userId") %>">
					<img src="img/mail.png" alt="メッセージ" class="menuImg">
					<span>メッセージ</span>
				</a>
			</p>
			<p>
				<a href="search">
					<img src="img/search.png" alt="検索" class="menuImg">
					<span>検索</span>
				</a>
			</p>
			<p>
				<a href="update">
					<img src="img/setting.png" alt="設定" class="menuImg">
					<span>設定</span>
				</a>
			</p>
			<p>
				<a href="logout">
				<img src="img/logout.png" alt="ログアウト" class="menuImg">
				<span>ログアウト</span>
				</a>
			</p>
		</div>
	</nav>

	<!-- メール関連 -->
	<div class="mail_all">

		<!-- メール相手名表示 -->
		<div class="add_name">
			<div class="add_wa">
				<div class="add_image">
					<img src="img/top.png" alt="addname">
				</div>
				<div class="name">
					<span id="nm" class="nm"><%= request.getAttribute("name") %></span>
					<span id="userId" class="nm"><%= request.getAttribute("userId") %></span>
				</div>
			</div>
		</div>


		<!-- メール内容一覧表示 -->
		<div class="scroll">
			<div class="mail_disp">
				<% ArrayList<HashMap<String, String>> mails = (ArrayList<HashMap<String, String>>)request.getAttribute("mails"); %>
				<% for(HashMap<String, String> log: mails) { %>
				<div class="mail_inline  <%= request.getAttribute("userId").equals(log.get("userId")) ? "other" :"me" %>">
					<div class="mail_list <%= request.getAttribute("userId").equals(log.get("userId")) ? "other" :"me" %>">
						<p id="text"><%= log.get("mtext") %></p>
						<p id="time"><%= log.get("tmStamp") %></p>
					</div>
				</div>
				<% } %>
			</div>
		</div>
	</div>
	<!-- メール入力欄 -->
	<div class="mail_in">
		<div class="in_in">
			<form action="mail" method="post">
				<input type="text" name="mtext" >
				<input type="hidden" name="addressId" value=<%= request.getAttribute("userId") %>>
				<input type="image" src="img/submit.png" alt="">
			</form>
		</div>
	</div>

	<!-- フォローリスト -->
	<div class="fl_all">
		<div class="fl_title">
			<span>フォローリスト</span>
		</div>
		<div class="lists">
			<% ArrayList<HashMap<String, String>> flw = (ArrayList<HashMap<String, String>>)request.getAttribute("flw"); %>
			<% for(HashMap<String, String> log: flw) { %>
			<div class="fl_list">
				<a href="mail?addressId=<%= log.get("userId") %>">
					<div class="fl_top">
						<div class="fl_img">
							<img src="img/top.png" alt="top">
						</div>
						<div class="fl_right">
							<div class="fl_top2">
								<div class="fl_name">
									<span id="name"><%= log.get("name") %></span>
									<span id="followId"><%= log.get("userId") %></span>
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>
			<%} %>
		</div>
	</div>

	<!-- フォロワーリスト -->
	<div class="fl_all2">
		<div class="fl_title">
			<span>フォロワーリスト</span>
		</div>
		<div class="lists">
			<% ArrayList<HashMap<String, String>> flwer = (ArrayList<HashMap<String, String>>)request.getAttribute("flwer"); %>
			<% for(HashMap<String, String> log: flwer) { %>
			<div class="fl_list">
				<a href="mail?addressId=<%= log.get("userId") %>">
					<div class="fl_top">
						<div class="fl_img">
							<img src="img/top.png" alt="top">
						</div>
						<div class="fl_right">
							<div class="fl_top2">
								<div class="fl_name">
									<span id="name"><%= log.get("name") %></span>
									<span id="followId"><%= log.get("userId") %></span>
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>
			<%} %>
		</div>
	</div>



	<script src="js/scroll.js"></script>
</body>
</html>