<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="error-page">
        <div class="error-code">:(</div>
        <h2>Что-то пошло не так</h2>
        <p>
            <c:choose>
                <c:when test="${not empty error}">
                    <c:out value="${error}"/>
                </c:when>
                <c:otherwise>
                    Произошла непредвиденная ошибка. Попробуй ещё раз.
                </c:otherwise>
            </c:choose>
        </p>
        <div style="margin-top:2rem; display:flex; gap:1rem; justify-content:center; flex-wrap:wrap;">
            <a href="/home" class="btn-gold">На главную</a>
            <a href="javascript:history.back()" class="btn-outline">← Назад</a>
        </div>
    </div>
</div>

<%@ include file="fragments/footer.jsp" %>
