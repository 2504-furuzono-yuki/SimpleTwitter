<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>簡易Twitter</title>
    </head>
    <body>
<div class="header">
    <c:if test="${ empty loginUser }">
        <a href="login">ログイン</a>
        <a href="signup">登録する</a>
    </c:if>
    <c:if test="${ not empty loginUser }">
        <a href="./">ホーム</a>
        <a href="setting">設定</a>
        <a href="logout">ログアウト</a>
    </c:if>
    <c:if test="${ not empty errorMessages }">
    <div class="errorMessages">
        <ul>
            <c:forEach items="${errorMessages}" var="errorMessage">
                <li><c:out value="${errorMessage}" />
            </c:forEach>
        </ul>
    </div>
    <c:remove var="errorMessages" scope="session" />
</c:if>

<div class="form-area">
    <c:if test="${ isShowMessageForm }">
        <form action="message" method="post">
            いま、どうしてる？<br />
            <textarea name="text" cols="100" rows="5" class="tweet-box"></textarea>
            <br />
            <input type="submit" value="つぶやく">（140文字まで）
        </form>
    </c:if>
</div>
            <div class="copyright"> Copyright(c)Furuzono</div>
        </div>
    </body>
</html>