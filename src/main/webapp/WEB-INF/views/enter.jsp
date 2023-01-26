<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
</head>
<body>
    <h1>Вход</h1>

    <c:if test="${requestScope.violations != null}">
        <c:forEach items="${requestScope.violations}" var="violation">
            <p>${violation}.</p>
        </c:forEach>
    </c:if>

    <form action="/user" method="post">
        <label for="nickname">Имя: </label>
        <input type="text" name="nickname" id="nickname"  value="${nickname}"></br>
        <label for="password">Пароль: </label>
        <input type="password" name="password" id="password" value="${password}"></br>
        <input type="submit" name="signin" value="Войти">
    </form>
</body>
</html>