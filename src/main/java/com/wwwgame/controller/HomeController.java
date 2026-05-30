package com.wwwgame.controller;


import com.wwwgame.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private  final TournamentService tournamentService;

    public HomeController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("/home")
        public String home(Model model){
            model.addAttribute("tournaments", tournamentService.findWaiting());
            return "home";

        }
    }


