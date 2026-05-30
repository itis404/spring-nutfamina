<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="page-title">
        <h2>Новый вопрос</h2>
        <a href="/packs/${packId}" class="btn-outline">← Назад к пакету</a>
    </div>

    <div class="wwwg-form">
        <form action="/packs/${packId}/questions/create" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label for="qText">Текст вопроса *</label>
                <textarea id="qText" name="text" rows="4" required
                          placeholder="Напишите формулировку вопроса…"></textarea>
            </div>

            <div class="form-group">
                <label for="qAnswer">Правильный ответ *</label>
                <input type="text" id="qAnswer" name="answer" required
                       placeholder="Ответ, который принимается"/>
            </div>

            <div class="form-group">
                <label for="qComment">Комментарий / источник</label>
                <input type="text" id="qComment" name="comment"
                       placeholder="Откуда взят вопрос, дополнительная информация"/>
            </div>

            <div class="form-group">
                <label for="qTopic">Тема для Wikipedia</label>
                <input type="text" id="qTopic" name="topic"
                       placeholder="Ключевое слово для поиска (например: «Эйнштейн»)"/>
            </div>

            <div class="form-group">
                <label for="qDiff">Сложность</label>
                <select id="qDiff" name="difficulty" required>
                    <option value="EASY"> Лёгкий</option>
                    <option value="MEDIUM" selected> Средний</option>
                    <option value="HARD"> Сложный</option>
                </select>
            </div>

            <div style="display:flex; gap:.75rem; margin-top:1.5rem;">
                <button type="submit" class="btn-gold" style="flex:1;">Добавить вопрос</button>
                <a href="/packs/${packId}" class="btn-outline" style="flex:1; text-align:center; padding:.5rem 1.2rem;">
                    Отмена
                </a>
            </div>
        </form>
    </div>
</div>

<%@ include file="fragments/footer.jsp" %>
