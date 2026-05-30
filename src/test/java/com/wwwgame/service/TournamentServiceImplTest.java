package com.wwwgame.service;

import com.wwwgame.entity.*;
import com.wwwgame.repository.QuestionRepository;
import com.wwwgame.repository.TournamentPlayerRepository;
import com.wwwgame.repository.TournamentRepository;
import com.wwwgame.service.impl.TournamentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentServiceImplTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private TournamentPlayerRepository tournamentPlayerRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    @Test
    void startTournament_setsStatusInProgressAndFirstQuestion() {
        Pack pack = new Pack();
        Question q1 = new Question();
        q1.setId(1L);

        Tournament tournament = new Tournament();
        tournament.setPack(pack);

        when(questionRepository.findByPack(pack)).thenReturn(List.of(q1));

        tournamentService.startTournament(tournament);

        assertEquals(TournamentStatus.IN_PROGRESS, tournament.getStatus());
        assertEquals(q1, tournament.getCurrentQuestion());
        verify(tournamentRepository).save(tournament);
    }

    @Test
    void nextQuestion_advancesToNextQuestion() {
        Pack pack = new Pack();

        Question q1 = new Question();
        q1.setId(1L);

        Question q2 = new Question();
        q2.setId(2L);

        Tournament tournament = new Tournament();
        tournament.setPack(pack);
        tournament.setCurrentQuestion(q1);

        when(questionRepository.findByPack(pack)).thenReturn(List.of(q1, q2));

        tournamentService.nextQuestion(tournament);

        assertEquals(q2, tournament.getCurrentQuestion());
        verify(tournamentRepository).save(tournament);
    }

    @Test
    void nextQuestion_finishesTournamentWhenLastQuestion() {
        Pack pack = new Pack();

        Question q1 = new Question();
        q1.setId(1L);

        Question q2 = new Question();
        q2.setId(2L);

        Tournament tournament = new Tournament();
        tournament.setPack(pack);
        tournament.setCurrentQuestion(q2);

        when(questionRepository.findByPack(pack)).thenReturn(List.of(q1, q2));

        tournamentService.nextQuestion(tournament);

        assertEquals(TournamentStatus.FINISHED, tournament.getStatus());
        verify(tournamentRepository).save(tournament);
    }

    @Test
    void addPlayer_savesPlayerWithCorrectDataAndZeroScore() {
        Tournament tournament = new Tournament();
        User user = new User();

        tournamentService.addPlayer(tournament, user);

        ArgumentCaptor<TournamentPlayer> captor = ArgumentCaptor.forClass(TournamentPlayer.class);
        verify(tournamentPlayerRepository).save(captor.capture());

        TournamentPlayer saved = captor.getValue();
        assertEquals(user, saved.getUser());
        assertEquals(tournament, saved.getTournament());
        assertEquals(0, saved.getScore());
    }

    @Test
    void finishTournament_setsStatusFinished() {
        Tournament tournament = new Tournament();
        tournament.setStatus(TournamentStatus.IN_PROGRESS);

        tournamentService.finishTournament(tournament);

        assertEquals(TournamentStatus.FINISHED, tournament.getStatus());
        verify(tournamentRepository).save(tournament);
    }

    @Test
    void findById_returnsCorrectTournament() {
        Tournament tournament = new Tournament();
        tournament.setId(42L);

        when(tournamentRepository.findById(42L)).thenReturn(Optional.of(tournament));

        Tournament result = tournamentService.findById(42L);

        assertEquals(42L, result.getId());
    }
}
