package com.wwwgame.service;

import com.wwwgame.entity.ChatMessage;
import com.wwwgame.entity.Tournament;
import java.util.List;

public interface ChatMessageService {
    void save(ChatMessage message);
    List<ChatMessage> findByTournament(Tournament tournament);
}