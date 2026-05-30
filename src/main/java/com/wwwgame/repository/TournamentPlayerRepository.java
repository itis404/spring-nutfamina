package com.wwwgame.repository;

import com.wwwgame.entity.Tournament;
import com.wwwgame.entity.TournamentPlayer;
import com.wwwgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TournamentPlayerRepository extends JpaRepository<TournamentPlayer, Long> {
    List<TournamentPlayer> findByTournament(Tournament tournament);
    TournamentPlayer findByTournamentAndUser(Tournament tournament, User user);
}

