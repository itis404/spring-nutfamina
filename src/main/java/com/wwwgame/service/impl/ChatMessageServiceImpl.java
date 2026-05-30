package com.wwwgame.service.impl;

import com.wwwgame.entity.ChatMessage;
import com.wwwgame.entity.Tournament;
import com.wwwgame.repository.ChatMessageRepository;
import com.wwwgame.service.ChatMessageService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public void save(ChatMessage message) {
        chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> findByTournament(Tournament tournament) {
        return chatMessageRepository.findByTournament(tournament);
    }
}