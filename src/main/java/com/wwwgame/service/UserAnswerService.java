package com.wwwgame.service;

import com.wwwgame.entity.User;
import com.wwwgame.entity.UserAnswer;
import java.util.List;

public interface UserAnswerService {
    void save(UserAnswer answer);
    List<UserAnswer> findByUser(User user);
    List<UserAnswer> findUniqueByUser(User user);
}