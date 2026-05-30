package com.wwwgame.controller;

import com.wwwgame.converter.QuestionConverter;
import com.wwwgame.converter.UserConverter;
import com.wwwgame.entity.Tournament;
import com.wwwgame.entity.User;
import com.wwwgame.service.QuestionService;
import com.wwwgame.service.TournamentService;
import com.wwwgame.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/game")
@Controller
public class GameController {

    private final TournamentService tournamentService;
    private final UserService userService;
    private final QuestionService questionService;
    private final UserConverter userConverter;
    private final QuestionConverter questionConverter;

    public GameController(TournamentService tournamentService, UserService userService, QuestionService questionService, UserConverter userConverter, QuestionConverter questionConverter) {
        this.tournamentService = tournamentService;
        this.userService = userService;
        this.questionService = questionService;
        this.userConverter = userConverter;
        this.questionConverter = questionConverter;
    }

    @GetMapping("/{id}")
    public String gameId(@PathVariable Long id, Authentication authentication, Model model){
        Tournament tournament = tournamentService.findById(id);

        String username = authentication.getName();
        User user = userService.findUserByUsername(username);

        model.addAttribute("user", userConverter.toDto(user));
        model.addAttribute("tournamentId", id);
        model.addAttribute("tournament", tournament);
        if (tournament.getCurrentQuestion() != null) {
            model.addAttribute("question", questionConverter.toDto(tournament.getCurrentQuestion()));
        }

        return "game";
    }

    @PostMapping("/{id}/next")
    public String nextQuestion(@PathVariable Long id) {
        Tournament tournament = tournamentService.findById(id);
        tournamentService.nextQuestion(tournament);
        return "redirect:/game/" + id;
    }
}
