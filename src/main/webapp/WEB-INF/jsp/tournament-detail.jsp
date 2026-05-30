<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="page-title">
        <h2> Зал ожидания</h2>
        <span class="badge-status badge-waiting">Ожидание</span>
    </div>

    <div style="display:grid; grid-template-columns:1fr 280px; gap:1.5rem; align-items:start;">

        <div>
            <div class="wwwg-card">
                <p style="color:var(--muted); font-size:.85rem; margin-bottom:.25rem;">Пакет вопросов</p>
                <h3 style="font-size:1.3rem;"><c:out value="${tournament.pack.name}"/></h3>
                <c:if test="${not empty tournament.pack.description}">
                    <p><c:out value="${tournament.pack.description}"/></p>
                </c:if>
            </div>

            <div style="margin-top:1rem;">
                <form action="/tournaments/${tournament.id}/start" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn-gold" style="width:100%; padding:.75rem; font-size:1rem;">
                        ▶ Начать игру
                    </button>
                </form>
            </div>
        </div>

        <div class="wwwg-card">
            <h3 style="margin-bottom:1rem;"> Игроки</h3>
            <c:choose>
                <c:when test="${empty tournament.players}">
                    <p style="color:var(--muted); text-align:center; padding:1rem 0;">Пока никого нет</p>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${tournament.players}" var="tp">
                        <div style="padding:.4rem 0; border-bottom:1px solid var(--border); color:var(--text); font-size:.9rem;">
                             <c:out value="${tp.user.username}"/>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="fragments/footer.jsp" %>
