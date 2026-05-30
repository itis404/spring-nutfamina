<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="page-title">
        <h2>Мои пакеты</h2>
    </div>

    <div class="wwwg-card" style="margin-bottom:2rem;">
        <h3 style="margin-bottom:1rem;">Новый пакет</h3>
        <form action="/packs/create" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div style="display:flex; gap:.75rem; flex-wrap:wrap; align-items:flex-end;">
                <div class="form-group" style="flex:1; min-width:200px; margin:0;">
                    <label for="packName">Название</label>
                    <input type="text" id="packName" name="name" placeholder="Название пакета" required maxlength="100"/>
                </div>
                <div class="form-group" style="flex:2; min-width:240px; margin:0;">
                    <label for="packDesc">Описание</label>
                    <input type="text" id="packDesc" name="description" placeholder="Краткое описание (необязательно)" maxlength="500"/>
                </div>
                <button type="submit" class="btn-gold" style="height:38px; padding:0 1.2rem;">Создать</button>
            </div>
        </form>
    </div>

    <c:choose>
        <c:when test="${empty packs}">
            <div class="empty-state">
                <p>У тебя пока нет пакетов.<br>Создай первый выше!</p>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${packs}" var="pack">
                <div class="wwwg-card">
                    <div style="display:flex; justify-content:space-between; align-items:flex-start; flex-wrap:wrap; gap:.75rem;">
                        <div style="flex:1; min-width:0;">
                            <h3><c:out value="${pack.name}"/></h3>
                            <span style="color:var(--muted); font-size:.82rem;">
                                 ${questionCounts[pack.id]} вопрос(ов)
                            </span>

                            <form action="/packs/${pack.id}/rename" method="post"
                                  style="display:flex; gap:.5rem; margin-top:.75rem;">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="text" name="name" value="${pack.name}"
                                       required maxlength="100"
                                       style="flex:1; font-size:.85rem; padding:.3rem .6rem;"/>
                                <button type="submit" class="btn-outline" style="font-size:.85rem; padding:.3rem .8rem;">
                                    ️ Переименовать
                                </button>
                            </form>
                        </div>

                        <div style="display:flex; gap:.5rem; align-items:center; flex-shrink:0; flex-wrap:wrap;">
                            <a href="/packs/${pack.id}/questions/create" class="btn-gold">+ Вопрос</a>
                            <a href="/packs/${pack.id}" class="btn-outline">Смотреть</a>
                            <form action="/packs/delete/${pack.id}" method="post"
                                  onsubmit="return confirm('Удалить пакет «${pack.name}»?')">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn-danger-sm">Удалить</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="fragments/footer.jsp" %>
