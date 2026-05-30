package com.wwwgame.repository;

import com.wwwgame.entity.ChatMessage;
import com.wwwgame.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByTournament(Tournament tournament);
}
