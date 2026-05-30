<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="page-title">
        <h2>Все пакеты вопросов</h2>
        <a href="/packs/my" class="btn-outline">Мои пакеты</a>
    </div>

    <c:choose>
        <c:when test="${empty packs}">
            <div class="empty-state">
                <p>Пока нет ни одного пакета.<br>Будь первым!</p>
                <a href="/packs/my" class="btn-gold">Создать пакет</a>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${packs}" var="pack">
                <c:set var="isFav" value="false"/>
                <c:forEach items="${favoritePacks}" var="fp">
                    <c:if test="${fp.id == pack.id}">
                        <c:set var="isFav" value="true"/>
                    </c:if>
                </c:forEach>

                <div class="wwwg-card">
                    <div style="display:flex; justify-content:space-between; align-items:flex-start; flex-wrap:wrap; gap:.5rem;">
                        <div>
                            <h3>
                                <c:if test="${isFav}"><span title="В избранном"></span> </c:if>
                                <c:out value="${pack.name}"/>
                            </h3>
                            <c:if test="${not empty pack.description}">
                                <p><c:out value="${pack.description}"/></p>
                            </c:if>
                            <span style="color:var(--muted); font-size:.82rem;">
                                 <c:out value="${pack.author.username}"/>
                                &nbsp;·&nbsp;
                                 ${questionCounts[pack.id]} вопрос(ов)
                            </span>
                        </div>
                        <div style="display:flex; gap:.5rem; align-items:center; flex-wrap:wrap;">
                            <form action="/packs/${pack.id}/favorite" method="post" style="margin:0;">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn-outline" style="white-space:nowrap;">
                                    <c:choose>
                                        <c:when test="${isFav}"> Убрать</c:when>
                                        <c:otherwise> В избранное</c:otherwise>
                                    </c:choose>
                                </button>
                            </form>
                            <a href="/packs/${pack.id}" class="btn-outline" style="white-space:nowrap;">Смотреть</a>
                            <c:if test="${isAdmin}">
                                <form action="/packs/delete/${pack.id}" method="post" style="margin:0;"
                                      onsubmit="return confirm('Удалить пакет «${pack.name}»?')">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button type="submit" class="btn-danger-sm">Удалить</button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="fragments/footer.jsp" %>
