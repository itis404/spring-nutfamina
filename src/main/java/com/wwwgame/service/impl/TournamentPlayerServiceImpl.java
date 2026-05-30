package com.wwwgame.service.impl;

import com.wwwgame.entity.Tournament;
import com.wwwgame.entity.TournamentPlayer;
import com.wwwgame.entity.User;
import com.wwwgame.repository.TournamentPlayerRepository;
import com.wwwgame.service.TournamentPlayerService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TournamentPlayerServiceImpl implements TournamentPlayerService {
    private final TournamentPlayerRepository tournamentPlayerRepository;

    public TournamentPlayerServiceImpl(TournamentPlayerRepository tournamentPlayerRepository) {
        this.tournamentPlayerRepository = tournamentPlayerRepository;
    }

    @Override
    public List<TournamentPlayer> findByTournament(Tournament tournament) {
        return tournamentPlayerRepository.findByTournament(tournament);
    }

    @Override
    public TournamentPlayer findByTournamentAndUser(Tournament tournament, User user) {
        return tournamentPlayerRepository.findByTournamentAndUser(tournament, user);
    }

    @Override
    public void updateScore(TournamentPlayer player, int score) {
        player.setScore(player.getScore() + score);
        tournamentPlayerRepository.save(player);
    }
}