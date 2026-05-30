package com.wwwgame.controller;

import com.wwwgame.entity.Pack;
import com.wwwgame.entity.Tournament;
import com.wwwgame.entity.TournamentStatus;
import com.wwwgame.entity.User;
import com.wwwgame.service.PackService;
import com.wwwgame.service.TournamentService;
import com.wwwgame.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tournaments")
@Controller
public class TournamentController {
    private final UserService userService;
    private final TournamentService tournamentService;
    private final PackService packService;

    public TournamentController(UserService userService, TournamentService tournamentService, PackService packService) {
        this.userService = userService;
        this.tournamentService = tournamentService;
        this.packService = packService;
    }

    @GetMapping
    public String waitingTours(Model model, Authentication authentication) {
        model.addAttribute("tournaments", tournamentService.findWaiting());
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        model.addAttribute("myTournaments", tournamentService.findByPlayer(user));
        return "tournaments";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Tournament tournament = tournamentService.findById(id);
        if (tournament.getStatus() == TournamentStatus.IN_PROGRESS
                || tournament.getStatus() == TournamentStatus.FINISHED) {
            return "redirect:/game/" + id;
        }
        model.addAttribute("tournament", tournament);
        return "tournament-detail";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        List<Pack> packs = packService.findAll();
        model.addAttribute("packs", packs);
        model.addAttribute("tournament", new Tournament());
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Tournament tournament) {
        tournament.setStatus(TournamentStatus.WAITING);
        tournamentService.create(tournament);
        return "redirect:/tournaments";
    }

    @PostMapping("/{id}/join")
    public String join(@PathVariable Long id, Authentication authentication) {
        Tournament tournament = tournamentService.findById(id);
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        tournamentService.addPlayer(tournament, user);
        return "redirect:/tournaments/" + tournament.getId();
    }

    @PostMapping("/{id}/start")
    public String start(@PathVariable Long id) {
        Tournament tournament = tournamentService.findById(id);
        tournamentService.startTournament(tournament);
        return "redirect:/tournaments/" + tournament.getId();
    }

    @PostMapping("/{id}/finish")
    public String finish(@PathVariable Long id) {
        Tournament tournament = tournamentService.findById(id);
        tournamentService.finishTournament(tournament);
        return "redirect:/tournaments/" + tournament.getId();
    }
}
