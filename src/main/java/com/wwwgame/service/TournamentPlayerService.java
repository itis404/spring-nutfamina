package com.wwwgame.service;

import com.wwwgame.entity.Tournament;
import com.wwwgame.entity.TournamentPlayer;
import com.wwwgame.entity.User;
import java.util.List;

public interface TournamentPlayerService {
    List<TournamentPlayer> findByTournament(Tournament tournament);
    TournamentPlayer findByTournamentAndUser(Tournament tournament, User user);
    void updateScore(TournamentPlayer player, int score);
}