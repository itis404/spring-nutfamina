<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация — WWW Game</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="auth-container">
    <h1>Что? Где? Когда?</h1>
    <h2>Регистрация</h2>

    <c:if test="${not empty error}">
        <div class="error-message"><c:out value="${error}"/></div>
    </c:if>

    <c:if test="${not empty validationErrors}">
        <c:forEach items="${validationErrors}" var="err">
            <div class="error-message"><c:out value="${err}"/></div>
        </c:forEach>
    </c:if>

    <form action="/register" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label>Имя пользователя</label>
            <input type="text" name="username" required/>
        </div>
        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" required/>
        </div>
        <div class="form-group">
            <label>Пароль</label>
            <input type="password" name="password" required/>
        </div>
        <button type="submit">Зарегистрироваться</button>
    </form>

    <p>Уже есть аккаунт? <a href="/login">Войти</a></p>
</div>
</body>
</html>