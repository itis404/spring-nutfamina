package com.wwwgame.repository;

import com.wwwgame.entity.Pack;
import com.wwwgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackRepository extends JpaRepository<Pack,Long> {
    List<Pack> findPackByAuthor(User id);

}
