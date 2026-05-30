package com.wwwgame.service;

import com.wwwgame.entity.Pack;
import com.wwwgame.entity.Question;
import com.wwwgame.entity.User;

import java.util.List;

public interface QuestionService {
    void create(Question question);

    void createForPack(Question question, Long packId, String authorUsername);

    void delete(Question question);

    List<Question> findAll();

    List<Question> findByPack(Pack pack);

    Question findById(Long id);

    List<Question> findUnansweredByPackAndUser(Pack pack, User user);

}

