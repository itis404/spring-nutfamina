<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="page-title">
        <div>
            <h2><c:out value="${pack.name}"/></h2>
            <c:if test="${not empty pack.description}">
                <p style="color:var(--muted); margin:.25rem 0 0; font-size:.9rem;">
                    <c:out value="${pack.description}"/>
                </p>
            </c:if>
        </div>
        <a href="/packs" class="btn-outline">← Назад</a>
    </div>

    <c:if test="${pack.author.username == currentUsername}">
    <div class="wwwg-card" style="margin-bottom:2rem;">
        <h3 style="margin-bottom:1.25rem;">Добавить вопрос</h3>
        <form action="/packs/${pack.id}/questions/create" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label for="qText">Текст вопроса *</label>
                <textarea id="qText" name="text" rows="3" required placeholder="Формулировка вопроса…"></textarea>
            </div>

            <div class="form-group">
                <label for="qAnswer">Правильный ответ *</label>
                <input type="text" id="qAnswer" name="answer" required placeholder="Ответ"/>
            </div>

            <div style="display:grid; grid-template-columns:1fr 1fr; gap:1rem;">
                <div class="form-group">
                    <label for="qComment">Комментарий / источник</label>
                    <input type="text" id="qComment" name="comment" placeholder="Необязательно"/>
                </div>
                <div class="form-group">
                    <label for="qTopic">Тема (для Wikipedia)</label>
                    <input type="text" id="qTopic" name="topic" placeholder="Например: Эйнштейн"/>
                </div>
            </div>

            <div class="form-group">
                <label for="qDiff">Сложность</label>
                <select id="qDiff" name="difficulty" required>
                    <option value="EASY">Лёгкий</option>
                    <option value="MEDIUM" selected>Средний</option>
                    <option value="HARD">Сложный</option>
                </select>
            </div>

            <button type="submit" class="btn-gold">Добавить вопрос</button>
        </form>
    </div>
    </c:if>

    <h3 style="margin-bottom:1rem;">Вопросы (${questions.size()})</h3>

    <c:choose>
        <c:when test="${empty questions}">
            <div class="empty-state">
                <div class="icon"></div>
                <p>В этом пакете пока нет вопросов.</p>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${questions}" var="q" varStatus="status">
                <div class="wwwg-card" data-question-id="${q.id}">
                    <div style="display:flex; justify-content:space-between; align-items:flex-start; gap:1rem;">
                        <div style="flex:1;">
                            <div style="display:flex; align-items:center; gap:.6rem; margin-bottom:.5rem;">
                                <span style="color:var(--muted); font-size:.8rem;">#${status.index + 1}</span>
                                <c:choose>
                                    <c:when test="${q.difficulty == 'EASY'}">
                                        <span class="badge-status badge-easy">Лёгкий</span>
                                    </c:when>
                                    <c:when test="${q.difficulty == 'MEDIUM'}">
                                        <span class="badge-status badge-medium">Средний</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-status badge-hard">Сложный</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <p style="color:var(--text); margin-bottom:.5rem; font-size:.95rem;">
                                <c:out value="${q.text}"/>
                            </p>
                        </div>
                        <button class="btn-outline" onclick="revealAnswer(${q.id}, this)"
                                style="white-space:nowrap; flex-shrink:0;">
                            Показать ответ
                        </button>
                    </div>
                    <div id="answer-${q.id}" class="answer-reveal" style="display:none;">
                        <div class="answer-text"></div>
                        <div class="comment-text" style="color:var(--muted); font-size:.88rem; margin-top:.4rem;"></div>
                        <div class="wiki-text" style="color:var(--info); font-size:.85rem; margin-top:.5rem; font-style:italic;"></div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<script>
function revealAnswer(questionId, btn) {
    const block = document.getElementById('answer-' + questionId);
    if (block.style.display !== 'none') {
        block.style.display = 'none';
        btn.textContent = 'Показать ответ';
        return;
    }
    btn.disabled = true;
    btn.textContent = 'Загрузка…';

    fetch('/api/questions/' + questionId + '/answer')
        .then(r => {
            if (!r.ok) throw new Error('Ошибка сети');
            return r.json();
        })
        .then(data => {
            block.querySelector('.answer-text').textContent  = '' + (data.answer  || '—');
            block.querySelector('.comment-text').textContent = data.comment || '';
            block.querySelector('.wiki-text').textContent    = data.wikipediaSummary
                ? 'Wikipedia: ' + data.wikipediaSummary : '';
            block.style.display = 'block';
            btn.textContent = 'Скрыть ответ';
            btn.disabled = false;
        })
        .catch(() => {
            block.querySelector('.answer-text').textContent = 'Не удалось загрузить ответ';
            block.style.display = 'block';
            btn.textContent = 'Скрыть';
            btn.disabled = false;
        });
}
</script>

<%@ include file="fragments/footer.jsp" %>
