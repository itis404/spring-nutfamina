<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="page-title">
        <h2>Профиль</h2>
    </div>

    <div style="display:grid; grid-template-columns:repeat(auto-fit, minmax(180px,1fr)); gap:1rem; margin-bottom:2rem;">
        <div class="stat-box">
            <div class="value"></div>
            <div class="label" style="font-size:1rem; color:var(--text); margin-top:.5rem;">
                <c:out value="${user.username}"/>
            </div>
            <div class="label"><c:out value="${user.email}"/></div>
        </div>

        <div class="stat-box">
            <div class="value">${answers.size()}</div>
            <div class="label">Всего ответов</div>
        </div>

        <div class="stat-box">
            <div class="value">
                <fmt:formatNumber value="${percentage}" maxFractionDigits="1"/>%
            </div>
            <div class="label">Правильных ответов</div>
        </div>
    </div>

    <div style="margin-bottom:2rem;">
        <div style="display:flex; justify-content:space-between; margin-bottom:.4rem; font-size:.85rem; color:var(--muted);">
            <span>Точность</span>
            <span><fmt:formatNumber value="${percentage}" maxFractionDigits="1"/>%</span>
        </div>
        <div style="background:var(--border); border-radius:99px; height:8px;">
            <div style="background:var(--gold); border-radius:99px; height:8px; width:${percentage}%;
                        transition:width .6s ease;"></div>
        </div>
    </div>

    <c:if test="${not empty progressPacks}">
        <h3 style="margin-bottom:1rem;">Прогресс по пакам</h3>
        <c:forEach items="${progressPacks}" var="pack">
            <c:set var="answered"  value="${answeredCounts[pack.id]}"/>
            <c:set var="remaining" value="${remainingCounts[pack.id]}"/>
            <c:set var="total"     value="${answered + remaining}"/>
            <div class="wwwg-card" style="margin-bottom:.75rem; padding:1rem 1.25rem;">
                <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:.5rem;">
                    <span style="font-weight:600;"><c:out value="${pack.name}"/></span>
                    <span style="color:var(--muted); font-size:.85rem;">
                        ${answered} / ${total} вопросов
                    </span>
                </div>
                <div style="background:var(--border); border-radius:99px; height:8px;">
                    <c:set var="pct" value="${total > 0 ? answered * 100 / total : 0}"/>
                    <div style="background:var(--gold); border-radius:99px; height:8px;
                                width:${pct}%; transition:width .6s ease;"></div>
                </div>
                <div style="color:var(--muted); font-size:.8rem; margin-top:.4rem;">
                    Осталось без ответа: ${remaining}
                </div>
            </div>
        </c:forEach>
    </c:if>

    <h3 style="margin-bottom:1rem;">История ответов</h3>

    <c:choose>
        <c:when test="${empty answers}">
            <div class="empty-state">
                <div class="icon"></div>
                <p>Ты ещё не отвечал(а) ни на один вопрос.<br>
                    <a href="/tournaments">Вступи в турнир!</a>
                </p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="wwwg-card" style="padding:0; overflow:hidden;">
                <table class="wwwg-table">
                    <thead>
                        <tr>
                            <th>Вопрос</th>
                            <th>Результат</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${answers}" var="a">
                            <tr>
                                <td>
                                    <c:out value="${a.question.text}"/>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${a.status == 'CORRECT'}">
                                            <span class="badge-status badge-correct">Верно</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge-status badge-incorrect">Неверно</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="fragments/footer.jsp" %>
