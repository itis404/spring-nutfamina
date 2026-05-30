package com.wwwgame.repository;

import com.wwwgame.entity.Tournament;
import com.wwwgame.entity.TournamentStatus;
import com.wwwgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    List<Tournament> findByStatus(TournamentStatus status);

    @Query("SELECT t FROM Tournament t JOIN t.players p WHERE p.user = :user")
    List<Tournament> findByPlayer(@Param("user") User user);
}
