package com.wwwgame.service.impl;

import com.wwwgame.entity.User;
import com.wwwgame.entity.UserAnswer;
import com.wwwgame.repository.UserAnswerRepository;
import com.wwwgame.service.UserAnswerService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;

    public UserAnswerServiceImpl(UserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    @Override
    public void save(UserAnswer answer) {
        userAnswerRepository.save(answer);
    }

    @Override
    public List<UserAnswer> findByUser(User user) {
        return userAnswerRepository.findByUser(user);
    }

    @Override
    public List<UserAnswer> findUniqueByUser(User user) {
        return userAnswerRepository.findFirstAnswerPerQuestionByUser(user);
    }
}