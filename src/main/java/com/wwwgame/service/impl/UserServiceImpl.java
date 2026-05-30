package com.wwwgame.service.impl;

import com.wwwgame.entity.Pack;
import com.wwwgame.entity.Role;
import com.wwwgame.entity.User;
import com.wwwgame.repository.UserRepository;
import com.wwwgame.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void registrationUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email уже существует");
        }
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username уже существует");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public void addFavoritePack(User user, Pack pack) {
        User managed = userRepository.findById(user.getId()).orElseThrow();
        boolean alreadyFav = managed.getFavoritePacks().stream()
                .anyMatch(p -> p.getId().equals(pack.getId()));
        if (!alreadyFav) {
            managed.getFavoritePacks().add(pack);
            userRepository.save(managed);
        }
    }

    @Transactional
    @Override
    public void removeFavoritePack(User user, Pack pack) {
        User managed = userRepository.findById(user.getId()).orElseThrow();
        managed.getFavoritePacks().removeIf(p -> p.getId().equals(pack.getId()));
        userRepository.save(managed);
    }
}
