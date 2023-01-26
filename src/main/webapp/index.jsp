<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Начальная страница</title>
</head>
<body>
<form action="/auth" method="post">
        <input type="radio" id="authChoice1"
             name="auth" value="reg">
            <label for="authChoice1">Регистрация</label>
            <input type="radio" id="authChoice2"
             name="auth" value="enter">
            <label for="authChoice2">Вход</label>
        <input type="submit" name="auth" value="Авторизация">
    </form>
</body>
</html>
