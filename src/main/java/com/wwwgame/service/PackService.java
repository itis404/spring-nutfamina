package com.wwwgame.service;

import com.wwwgame.entity.Pack;
import com.wwwgame.entity.Question;
import com.wwwgame.entity.User;

import java.util.List;

public interface PackService {
    void createPack(Pack pack);
    void deletePack(Pack pack);
    List<Pack> findAll();
    List<Pack> findByAuthor(User author);
    Pack findById(Long id);
    void renamePack(Pack pack, String newName);
}
