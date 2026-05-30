package com.wwwgame.service;

import com.wwwgame.entity.Tournament;
import com.wwwgame.entity.User;

import java.util.List;

public interface TournamentService {
    void create(Tournament tournament);

    List<Tournament> findWaiting();

    Tournament findById(Long id);

    void addPlayer(Tournament tournament, User user);

    void startTournament(Tournament tournament);

    void finishTournament(Tournament tournament);

    void nextQuestion(Tournament tournament);

    List<Tournament> findByPlayer(User user);

}
