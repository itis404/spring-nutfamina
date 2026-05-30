package com.wwwgame.controller;

import com.wwwgame.converter.UserConverter;
import com.wwwgame.dto.UserDto;
import com.wwwgame.entity.AnswerStatus;
import com.wwwgame.entity.Pack;
import com.wwwgame.entity.User;
import com.wwwgame.entity.UserAnswer;
import com.wwwgame.service.QuestionService;
import com.wwwgame.service.UserAnswerService;
import com.wwwgame.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    private final UserService userService;
    private final UserAnswerService userAnswerService;
    private final UserConverter userConverter;
    private final QuestionService questionService;

    public ProfileController(UserService userService, UserAnswerService userAnswerService,
                             UserConverter userConverter, QuestionService questionService) {
        this.userService = userService;
        this.userAnswerService = userAnswerService;
        this.userConverter = userConverter;
        this.questionService = questionService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication){
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);

        List<UserAnswer> answers = userAnswerService.findUniqueByUser(user);
        long correct = answers.stream()
                .filter(a -> a.getStatus() == AnswerStatus.CORRECT)
                .count();
        double percentage = answers.isEmpty() ? 0 : (double) correct / answers.size() * 100;

        List<Pack> progressPacks = answers.stream()
                .map(ua -> ua.getQuestion().getPack())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, Integer> answeredCounts = new HashMap<>();
        Map<Long, Integer> remainingCounts = new HashMap<>();
        for (Pack pack : progressPacks) {
            long answeredInPack = answers.stream()
                    .filter(ua -> ua.getQuestion().getPack() != null
                               && ua.getQuestion().getPack().getId().equals(pack.getId()))
                    .count();
            int remaining = questionService.findUnansweredByPackAndUser(pack, user).size();
            answeredCounts.put(pack.getId(), (int) answeredInPack);
            remainingCounts.put(pack.getId(), remaining);
        }

        model.addAttribute("user", userConverter.toDto(user));
        model.addAttribute("answers", answers);
        model.addAttribute("percentage", percentage);
        model.addAttribute("progressPacks", progressPacks);
        model.addAttribute("answeredCounts", answeredCounts);
        model.addAttribute("remainingCounts", remainingCounts);
        return "profile";
    }

}
