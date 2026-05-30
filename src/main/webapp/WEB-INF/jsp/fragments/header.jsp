<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Что? Где? Когда?</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<nav class="navbar navbar-expand-lg wwwg-navbar">
    <div class="container">
        <a class="navbar-brand wwwg-brand" href="/home"> ЧГК</a>
        <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navMenu">
            <ul class="navbar-nav ms-auto align-items-lg-center gap-1">
                <li class="nav-item"><a class="nav-link" href="/packs">Пакеты</a></li>
                <li class="nav-item"><a class="nav-link" href="/packs/my">Мои пакеты</a></li>
                <li class="nav-item"><a class="nav-link" href="/tournaments">Турниры</a></li>
                <li class="nav-item"><a class="nav-link" href="/profile">Профиль</a></li>
                <li class="nav-item">
                    <form action="/logout" method="post" class="m-0">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-sm wwwg-btn-danger">Выйти</button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
