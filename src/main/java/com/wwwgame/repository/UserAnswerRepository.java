package com.wwwgame.repository;

import com.wwwgame.entity.AnswerStatus;
import com.wwwgame.entity.Question;
import com.wwwgame.entity.User;
import com.wwwgame.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByUser(User user);
    long countByUserAndStatus(User user, AnswerStatus status);
    long countByUser(User user);
    void deleteByQuestion(Question question);

    @Query("SELECT ua FROM UserAnswer ua WHERE ua.user = :user AND ua.id IN " +
           "(SELECT MIN(ua2.id) FROM UserAnswer ua2 WHERE ua2.user = :user GROUP BY ua2.question)")
    List<UserAnswer> findFirstAnswerPerQuestionByUser(@Param("user") User user);
}
