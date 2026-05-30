package com.wwwgame.repository;

import com.wwwgame.entity.Pack;
import com.wwwgame.entity.Question;
import com.wwwgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByPack(Pack pack);
    @Query("SELECT q FROM Question q WHERE q.pack=:pack AND q NOT IN(SELECT ua.question FROM UserAnswer ua WHERE ua.user = :user)")
    List<Question> findUnasweredByPack(@Param("pack") Pack pack, @Param("user") User user);
}
