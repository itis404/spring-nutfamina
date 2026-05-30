<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="page-title">
        <h2>Открытые турниры</h2>
        <a href="/tournaments/create" class="btn-gold">+ Создать турнир</a>
    </div>

    <c:choose>
        <c:when test="${empty tournaments}">
            <div class="empty-state">
                <p>Пока нет открытых турниров.<br>Создай первый и зови друзей!</p>
                <a href="/tournaments/create" class="btn-gold">Создать турнир</a>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${tournaments}" var="t">
                <div class="wwwg-card">
                    <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:.5rem;">
                        <div>
                            <h3><c:out value="${t.pack.name}"/></h3>
                            <span class="badge-status badge-waiting">Ожидание</span>
                            <span style="color:var(--muted); font-size:.85rem; margin-left:.5rem;">
                                Игроков: ${t.players.size()}
                            </span>
                        </div>
                        <form action="/tournaments/${t.id}/join" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn-gold">Присоединиться</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="fragments/footer.jsp" %>
