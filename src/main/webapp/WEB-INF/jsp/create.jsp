<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="page-title">
        <h2>Создать турнир</h2>
        <a href="/tournaments" class="btn-outline">← Назад</a>
    </div>

    <div class="wwwg-form">
        <form action="/tournaments/create" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label for="packId">Пакет вопросов</label>
                <select name="pack.id" id="packId" required>
                    <option value="" disabled selected>— Выберите пакет —</option>
                    <c:forEach items="${packs}" var="pack">
                        <option value="${pack.id}">
                            <c:out value="${pack.name}"/>
                            (автор: <c:out value="${pack.author.username}"/>)
                        </option>
                    </c:forEach>
                </select>
            </div>

            <c:if test="${empty packs}">
                <div class="alert alert-info">
                    Пока нет ни одного пакета.
                    <a href="/packs/my">Создай пакет</a> — потом можно делать турнир.
                </div>
            </c:if>

            <button type="submit" class="btn-gold" style="width:100%; margin-top:.5rem;" ${empty packs ? 'disabled' : ''}>
                Создать турнир
            </button>
        </form>
    </div>
</div>

<%@ include file="fragments/footer.jsp" %>
