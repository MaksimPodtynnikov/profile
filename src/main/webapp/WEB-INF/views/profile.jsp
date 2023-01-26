<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Профиль</title>
</head>
<body>

<p>${firstname} ${lastname} ${patronymic}</p>
<img src="data:image/jpg;base64, ${photo}" name="photo" id="photo" width="350" height="350"></br>
<p>О себе: </br>${hobbies}</p>
<form action="/addFriend" method="post">
<input type="hidden" name = "idF" id="idF" value="${idF}">
<input type="submit" name="add" id="add" value="Добавить в друзья">
</form>
<p>Друзья:</p>
    <c:if test="${friends != null}">
            <c:forEach items="${friends}" var="friend">
                <p><a href="${friend}">${friend}</a></p>
            </c:forEach>
        </c:if>
</body>
</html>