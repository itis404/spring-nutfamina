package com.wwwgame.controller;

import com.wwwgame.entity.Pack;
import com.wwwgame.entity.Question;
import com.wwwgame.entity.Role;
import com.wwwgame.entity.User;
import com.wwwgame.service.PackService;
import com.wwwgame.service.QuestionService;
import com.wwwgame.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/packs")
public class PackController {
    private final PackService packService;
    private final UserService userService;
    private final QuestionService questionService;

    public PackController(PackService packService, UserService userService, QuestionService questionService) {
        this.packService = packService;
        this.userService = userService;
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public String create(Pack pack, Authentication authentication){
        User author = userService.findUserByUsername(authentication.getName());
        pack.setAuthor(author);
        pack.setCreatedAt(java.time.LocalDateTime.now());
        packService.createPack(pack);
        return "redirect:/packs/my";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Authentication authentication){
        Pack pack = packService.findById(id);
        User user = userService.findUserByUsername(authentication.getName());
        boolean isAuthor = pack.getAuthor() != null &&
                pack.getAuthor().getUsername().equals(authentication.getName());
        boolean isAdmin = user.getRole() == Role.ADMIN;
        if (!isAuthor && !isAdmin) {
            throw new RuntimeException("Нет прав для удаления этого пакета");
        }
        packService.deletePack(pack);
        return isAdmin ? "redirect:/packs" : "redirect:/packs/my";
    }

    @GetMapping
    public String findAll(Model model, Authentication authentication){
        List<Pack> packs = packService.findAll();
        model.addAttribute("packs", packs);

        Map<Long, Integer> questionCounts = new HashMap<>();
        for (Pack pack : packs) {
            questionCounts.put(pack.getId(), questionService.findByPack(pack).size());
        }
        model.addAttribute("questionCounts", questionCounts);

        User user = userService.findUserByUsername(authentication.getName());
        model.addAttribute("favoritePacks", user.getFavoritePacks());
        model.addAttribute("isAdmin", user.getRole() == Role.ADMIN);
        return "packs";
    }

    @PostMapping("/{id}/favorite")
    public String toggleFavorite(@PathVariable Long id, Authentication authentication){
        Pack pack = packService.findById(id);
        User user = userService.findUserByUsername(authentication.getName());
        boolean isFav = user.getFavoritePacks().stream()
                .anyMatch(p -> p.getId().equals(pack.getId()));
        if (isFav) {
            userService.removeFavoritePack(user, pack);
        } else {
            userService.addFavoritePack(user, pack);
        }
        return "redirect:/packs";
    }
    @GetMapping("/my")
    public String myPacks(Model model, Authentication authentication){
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        List<Pack> packs = packService.findByAuthor(user);
        model.addAttribute("packs", packs);

        Map<Long, Integer> questionCounts = new HashMap<>();
        for (Pack pack : packs) {
            questionCounts.put(pack.getId(), questionService.findByPack(pack).size());
        }
        model.addAttribute("questionCounts", questionCounts);
        model.addAttribute("isAdmin", user.getRole() == Role.ADMIN);
        return "packs-my";
    }

    @GetMapping("/{id}")
    public String allQuestions(@PathVariable Long id, Model model, Authentication authentication){
        Pack pack = packService.findById(id);
        List<Question> questions = questionService.findByPack(pack);
        model.addAttribute("questions", questions);
        model.addAttribute("pack", pack);
        model.addAttribute("currentUsername", authentication.getName());
        return "pack-questions";
    }

    @PostMapping("/{id}/rename")
    public String rename(@PathVariable Long id, @RequestParam String name, Authentication authentication){
        Pack pack = packService.findById(id);
        if (!pack.getAuthor().getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Нет прав для переименования этого пакета");
        }
        packService.renamePack(pack, name);
        return "redirect:/packs/my";
    }

    @GetMapping("{id}/questions/create")
    public String createForm(@PathVariable Long id,Model model){
        model.addAttribute("packId", id);
        model.addAttribute("question", new Question());
        return "create-questions";
    }

    @PostMapping("{id}/questions/create")
    public String createQuestion(@PathVariable Long id, @ModelAttribute Question question, Authentication authentication){
        Pack pack = packService.findById(id);
        if (!pack.getAuthor().getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Нет прав для добавления вопросов в этот пакет");
        }
        questionService.createForPack(question, id, authentication.getName());
        return "redirect:/packs/" + id;
    }

}
