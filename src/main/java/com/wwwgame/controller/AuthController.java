package com.wwwgame.controller;

import com.wwwgame.entity.User;
import com.wwwgame.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("validationErrors", errors);
            return "register";
        }
        try {
            userService.registrationUser(user);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
