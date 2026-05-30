<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="page-content">
    <div class="page-title">
        <h2>Игровая комната</h2>

             <c:out value="${user.username}"/>
        </span>
    </div>

    <c:choose>
        <c:when test="${empty question}">
            <div class="empty-state">
                <div class="icon"></div>
                <p>Ждём начала раунда…<br>
                   Ведущий скоро задаст вопрос.</p>
            </div>
        </c:when>

        <c:otherwise>
            <div class="game-layout">

                <div>
                    <div class="question-card">
                        <div style="display:flex; align-items:center; gap:.75rem; margin-bottom:1rem;">
                            <c:choose>
                                <c:when test="${question.difficulty == 'EASY'}">
                                    <span class="badge-status badge-easy">Лёгкий</span>
                                </c:when>
                                <c:when test="${question.difficulty == 'MEDIUM'}">
                                    <span class="badge-status badge-medium">Средний</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge-status badge-hard">Сложный</span>
                                </c:otherwise>
                            </c:choose>
                            <span style="color:var(--muted); font-size:.82rem;">
                                автор: <c:out value="${question.authorUsername}"/>
                            </span>
                        </div>

                        <div class="timer-wrap">
                            <div class="timer" id="timer">60</div>
                            <p style="color:var(--muted); font-size:.8rem; margin-top:.4rem;">секунд на обсуждение</p>
                        </div>

                        <div class="q-text">
                            <c:out value="${question.text}"/>
                        </div>

                        <button id="btnAnswer" class="btn-gold" style="width:100%; display:none;"
                                onclick="document.getElementById('answerModal').classList.add('show')">
                            ️ Написать ответ
                        </button>

                        <div id="revealSection" style="display:none;" class="answer-reveal">
                            <div class="answer-text" id="correctAnswer"></div>
                            <div id="answerComment" style="color:var(--muted); font-size:.88rem; margin-top:.4rem;"></div>
                            <div id="wikiSummary"   style="color:var(--info);  font-size:.85rem; margin-top:.5rem; font-style:italic;"></div>

                            <form id="nextForm" action="/game/${tournamentId}/next" method="post"
                                  style="margin-top:1.25rem; display:none;">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn-gold" style="width:100%;">
                                    Следующий вопрос →
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="chat-box">
                    <div class="chat-title"> Обсуждение</div>
                    <div class="chat-messages" id="chatMessages"></div>
                    <div class="chat-input-row">
                        <input type="text" id="chatInput"
                               placeholder="Напиши сообщение…" maxlength="300"
                               onkeydown="if(event.key==='Enter') sendChatMessage()"/>
                        <button id="sendBtn" onclick="sendChatMessage()">→</button>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<div class="wwwg-modal-overlay" id="answerModal">
    <div class="wwwg-modal">
        <h3>Ваш ответ</h3>
        <p style="color:var(--muted); font-size:.9rem; margin-bottom:1rem;">
            Минута истекла. Введите ваш вариант ответа:
        </p>
        <div class="form-group">
            <input type="text" id="userAnswerInput" placeholder="Ваш ответ…" maxlength="300"
                   onkeydown="if(event.key==='Enter') submitAnswer()"/>
        </div>
        <div style="display:flex; gap:.75rem;">
            <button class="btn-gold" style="flex:1;" onclick="submitAnswer()">Подтвердить</button>
            <button class="btn-outline" style="flex:1;"
                    onclick="document.getElementById('answerModal').classList.remove('show')">
                Отмена
            </button>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

<script>
    var TOURNAMENT_ID = ${tournamentId};
    var QUESTION_ID   = ${not empty question ? question.id : 0};
    var CURRENT_USER  = '<c:out value="${user.username}"/>';
    var CSRF_TOKEN    = '${_csrf.token}';
    var CSRF_HEADER   = '${_csrf.headerName}';

    var timeLeft = 60;
    var timerEl  = document.getElementById('timer');
    var btnAnswer = document.getElementById('btnAnswer');

    if (timerEl) {
        var timerInterval = setInterval(function () {
            timeLeft--;
            timerEl.textContent = timeLeft;

            if (timeLeft <= 10) timerEl.classList.add('danger');

            if (timeLeft <= 0) {
                clearInterval(timerInterval);
                timerEl.textContent = '0';
                if (btnAnswer) btnAnswer.style.display = 'block';
                var input  = document.getElementById('chatInput');
                var sendBtn = document.getElementById('sendBtn');
                if (input)   { input.disabled = true;   input.placeholder = 'Время вышло'; }
                if (sendBtn) sendBtn.disabled = true;
                document.getElementById('answerModal').classList.add('show');
            }
        }, 1000);
    }

    var stompClient = null;

    function connectWS() {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.debug = null;

        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/chat/' + TOURNAMENT_ID, function (msg) {
                var data = JSON.parse(msg.body);
                appendMessage(data.user ? data.user.username : '?', data.text);
            });
        }, function (error) {
            console.error('WS ошибка:', error);
        });
    }

    function appendMessage(author, text) {
        var box = document.getElementById('chatMessages');
        if (!box) return;
        var div = document.createElement('div');
        div.className = 'chat-msg';
        var authorEl = document.createElement('span');
        authorEl.className = 'author';
        authorEl.textContent = author + ': ';
        var textNode = document.createTextNode(text);
        div.appendChild(authorEl);
        div.appendChild(textNode);
        box.appendChild(div);
        box.scrollTop = box.scrollHeight;
    }

    function sendChatMessage() {
        var input = document.getElementById('chatInput');
        if (!input || !input.value.trim() || !stompClient) return;
        var text = input.value.trim();
        stompClient.send(
            '/app/chat/' + TOURNAMENT_ID,
            {},
            JSON.stringify({ text: text, tournament: { id: TOURNAMENT_ID } })
        );
        input.value = '';
    }

    function submitAnswer() {
        var userAnswer = document.getElementById('userAnswerInput').value.trim();
        document.getElementById('answerModal').classList.remove('show');
        if (QUESTION_ID === 0) return;

        var headers = { 'Content-Type': 'application/json' };
        headers[CSRF_HEADER] = CSRF_TOKEN;

        fetch('/api/questions/' + QUESTION_ID + '/submit', {
            method: 'POST',
            headers: headers,
            body: JSON.stringify({ answer: userAnswer })
        })
            .then(function (r) {
                if (!r.ok) throw new Error('Ошибка ' + r.status);
                return r.json();
            })
            .then(function (data) {
                document.getElementById('correctAnswer').textContent =
                    ' Правильный ответ: ' + (data.answer || '—');
                document.getElementById('answerComment').textContent =
                    data.comment || '';
                document.getElementById('wikiSummary').textContent =
                    data.wikipediaSummary ? 'Wikipedia: ' + data.wikipediaSummary : '';
                document.getElementById('revealSection').style.display = 'block';
                if (btnAnswer) btnAnswer.style.display = 'none';
                var nextForm = document.getElementById('nextForm');
                if (nextForm) nextForm.style.display = 'block';
            })
            .catch(function (err) {
                document.getElementById('correctAnswer').textContent =
                    'Не удалось загрузить ответ: ' + err.message;
                document.getElementById('revealSection').style.display = 'block';
                var nextForm = document.getElementById('nextForm');
                if (nextForm) nextForm.style.display = 'block';
            });
    }

    if (QUESTION_ID !== 0) {
        connectWS();
    }
</script>

<%@ include file="fragments/footer.jsp" %>
