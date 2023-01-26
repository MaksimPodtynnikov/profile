<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Профиль</title>
</head>
<body>
  <form action="/userEdit" method="post" enctype="multipart/form-data">
        <input id="id" name="id" type="hidden" value="${id}">
        <label for="firstname">Имя: </label>
        <input type="text" name="firstname" id="firstname" value="${firstname}"></br>
        <label for="lastname">Фамилия:</label>
        <input type="text" name="lastname" id="lastname" value="${lastname}"></br>
        <label for="patronymic">Отчество: </label>
        <input type="text" name="patronymic" id="patronymic" value="${patronymic}"></br>
        <label for="image">Фото: </label>
        <img src="data:image/jpg;base64, ${photo}" name="photo" id="photo" width="350" height="350"></br>
        <label for="image">Для изменения фотографии загрузите файл: </label>
        <input type="file" name="file" id="file" size="50" /></br>
        <label for="hobbies">Увлечения: </label>
        <textarea name="hobbies" id="hobbies" cols="40" rows="3">${hobbies}</textarea></br>
        <input type="submit" name="signup" value="Изменить данные">
        <p>Количество посещений: ${enters}</p>
    </form>
    <p>Друзья:</p>
    <c:if test="${friends != null}">
            <c:forEach items="${friends}" var="friend">
                <p><a href="${friend}">${friend}</a></p>
            </c:forEach>
        </c:if>
</body>
</html>