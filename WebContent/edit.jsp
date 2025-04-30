<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>編集</title>
</head>
<body>

	<div class="main-contents">
		<!-- わからないので聞く -->
		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="errorMessage">
						<li><c:out value="${errorMessage}" />
					</c:forEach>
				</ul>
			</div>
		</c:if>
		<!-- EditのGetメソッドに送っている -->
		<form action="edit" method="Get">
			<div class="messages">
				<!-- テキストの表示 -->
				<textarea name="text" cols="100" rows="5" class="tweet-box">
				<c:out value="${message.text}" />
				</textarea>
			</div>
		</form>
		<!-- EditのPostメソッドに送っている -->
		<form action="edit" method="Post">
			<div class="messages">
				<!-- メッセージを更新したいとサーバー側に値を送る -->
				<input type="hidden" name="edit" value="${message.id}" />
				<!-- 更新ボタンの実装 -->
				<input type="submit" value="更新">（140文字まで）
			</div>
		</form>
		<div class="copyright">Copyright(c)Furuzono</div>
	</div>
</body>
</html>