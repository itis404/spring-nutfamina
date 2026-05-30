<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="page-title">
        <h2>Турниры</h2>
        <a href="/tournaments/create" class="btn-gold">+ Создать турнир</a>
    </div>

    <c:if test="${not empty myTournaments}">
        <h3 style="margin-bottom:1rem; color:var(--gold);"> Мои турниры</h3>
        <c:forEach items="${myTournaments}" var="t">
            <div class="wwwg-card">
                <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:.75rem;">
                    <div>
                        <h3><c:out value="${t.pack.name}"/></h3>
                        <c:choose>
                            <c:when test="${t.status == 'WAITING'}">
                                <span class="badge-status badge-waiting">Ожидание</span>
                            </c:when>
                            <c:when test="${t.status == 'IN_PROGRESS'}">
                                <span class="badge-status badge-medium">В процессе</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge-status badge-correct">Завершён</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <a href="/tournaments/${t.id}" class="btn-outline">Перейти</a>
                </div>
            </div>
        </c:forEach>
        <div style="border-top:1px solid var(--border); margin:1.5rem 0;"></div>
    </c:if>

    <h3 style="margin-bottom:1rem;"> Открытые турниры</h3>
    <c:choose>
        <c:when test="${empty tournaments}">
            <div class="empty-state">

                <p>Нет открытых турниров. Создай первый!</p>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${tournaments}" var="t">
                <div class="wwwg-card">
                    <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:.75rem;">
                        <div>
                            <h3><c:out value="${t.pack.name}"/></h3>
                            <span class="badge-status badge-waiting">Ожидание игроков</span>
                            <span style="color:var(--muted); font-size:.85rem; margin-left:.75rem;">
                                 ${t.players.size()} игрок(ов)
                            </span>
                        </div>
                        <div style="display:flex; gap:.5rem;">
                            <form action="/tournaments/${t.id}/join" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn-gold">Войти в зал</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="fragments/footer.jsp" %>
