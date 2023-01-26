<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
</head>
<body>
    <h1>Регистрация</h1>

    <c:if test="${violations != null}">
        <c:forEach items="${violations}" var="violation">
            <p>${violation}.</p>
        </c:forEach>
    </c:if>

    <form action="/userRegister" method="post" enctype="multipart/form-data">
     <input id="id" name="id" type="hidden" value="-1">
        <label for="nickname">Логин: </label>
        <input type="text" name="nickname" id="nickname" ></br>
        <label for="firstname">Имя: </label>
        <input type="text" name="firstname" id="firstname" ></br>
        <label for="lastname">Фамилия:</label>
        <input type="text" name="lastname" id="lastname" ></br>
        <label for="patronymic">Отчество: </label>
        <input type="text" name="patronymic" id="patronymic" ></br>
        <label for="image">Фото: </label>
        <input type="file" name="photo" id="photo" size="50" /></br>
        <label for="password">Пароль: </label>
        <input type="text" name="password" id="password" ></br>
        <label for="hobbies">Увлечения: </label>
        <textarea name="hobbies" id="hobbies" cols="40" rows="3">${hobbies}</textarea></br>
        <input type="submit" name="signup" value="Зарегистрироваться" onclick="submitFile();">
    </form>
</body>
</html>