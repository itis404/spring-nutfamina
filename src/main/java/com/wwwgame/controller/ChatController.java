package com.wwwgame.controller;

import com.wwwgame.entity.ChatMessage;
import com.wwwgame.entity.User;
import com.wwwgame.service.ChatMessageService;
import com.wwwgame.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final UserService userService;
    private final ChatMessageService chatMessageService;

    public ChatController(UserService userService, ChatMessageService chatMessageService) {
        this.userService = userService;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat/{tournamentId}")
    @SendTo("/topic/chat/{tournamentId}")
    public ChatMessage sendMessage(ChatMessage message, Authentication authentication){
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        message.setUser(user);
        chatMessageService.save(message);
        return message;
    }


}
