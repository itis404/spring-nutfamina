package com.wwwgame.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Pack pack;
    @OneToMany(fetch = FetchType.EAGER)
    private List<TournamentPlayer> players;
    private LocalDateTime playedAt;
    @Enumerated(EnumType.STRING)
    private TournamentStatus status;
    @ManyToOne
    private Question currentQuestion;
}
