<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход — WWW Game</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="auth-container">
    <h1>Что? Где? Когда?</h1>
    <h2>Вход</h2>

    <c:if test="${param.error != null}">
        <div class="error-message">Неверный логин или пароль</div>
    </c:if>


    <form action="/login" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label>Имя пользователя</label>
            <input type="text" name="username" required/>
        </div>
        <div class="form-group">
            <label>Пароль</label>
            <input type="password" name="password" required/>
        </div>
        <button type="submit">Войти</button>
    </form>

    <p>Нет аккаунта? <a href="/register">Зарегистрироваться</a></p>
</div>
</body>
</html>