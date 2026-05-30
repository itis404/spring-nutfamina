package com.wwwgame.service.impl;

import com.wwwgame.entity.*;
import com.wwwgame.repository.QuestionRepository;
import com.wwwgame.repository.TournamentPlayerRepository;
import com.wwwgame.repository.TournamentRepository;
import com.wwwgame.service.TournamentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TournamentPlayerRepository tournamentPlayerRepository;
    private final QuestionRepository questionRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository, TournamentPlayerRepository tournamentPlayerRepository, QuestionRepository questionRepository) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentPlayerRepository = tournamentPlayerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void create(Tournament tournament) {
        tournamentRepository.save(tournament);

    }

    @Override
    public List<Tournament> findWaiting() {
        return tournamentRepository.findByStatus(TournamentStatus.WAITING);
    }

    @Override
    public Tournament findById(Long id) {
        return tournamentRepository.findById(id).orElseThrow();
    }

    @Override
    public void addPlayer(Tournament tournament, User user) {
        TournamentPlayer player = new TournamentPlayer();
        player.setTournament(tournament);
        player.setUser(user);
        player.setScore(0);
        tournamentPlayerRepository.save(player);

    }

    @Override
    public void startTournament(Tournament tournament) {
        tournament.setStatus(TournamentStatus.IN_PROGRESS);
        List<Question> questions = questionRepository.findByPack(tournament.getPack());
        tournament.setCurrentQuestion(questions.get(0));
        tournamentRepository.save(tournament);
    }

    @Override
    public void finishTournament(Tournament tournament) {
        tournament.setStatus(TournamentStatus.FINISHED);
        tournamentRepository.save(tournament);
    }

    @Override
    public List<Tournament> findByPlayer(User user) {
        return tournamentRepository.findByPlayer(user);
    }

    @Override
    public void nextQuestion(Tournament tournament) {
        List<Question> questions = questionRepository.findByPack(tournament.getPack());

        Long currentId = tournament.getCurrentQuestion() != null
                ? tournament.getCurrentQuestion().getId()
                : null;

        int currentIndex = -1;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId().equals(currentId)) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex >= 0 && currentIndex + 1 < questions.size()) {
            tournament.setCurrentQuestion(questions.get(currentIndex + 1));
            tournamentRepository.save(tournament);
        } else {
            finishTournament(tournament);
        }
    }
}
