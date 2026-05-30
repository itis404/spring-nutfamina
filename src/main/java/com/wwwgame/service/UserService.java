package com.wwwgame.service;

import com.wwwgame.entity.Pack;
import com.wwwgame.entity.User;

public interface UserService {
        void registrationUser(User user);
        User findUserByUsername(String username);
        void addFavoritePack(User user, Pack pack);
        void removeFavoritePack(User user, Pack pack);
}
