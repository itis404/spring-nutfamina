package com.wwwgame.controller;

import com.wwwgame.dto.AnswerDto;
import com.wwwgame.entity.AnswerStatus;
import com.wwwgame.entity.Pack;
import com.wwwgame.entity.Question;
import com.wwwgame.entity.User;
import com.wwwgame.entity.UserAnswer;
import com.wwwgame.service.PackService;
import com.wwwgame.service.QuestionService;
import com.wwwgame.service.UserAnswerService;
import com.wwwgame.service.UserService;
import com.wwwgame.service.WikipediaService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/questions")
@RestController
public class QuestionRestController {
    private final QuestionService questionService;
    private final WikipediaService wikipediaService;
    private final UserService userService;
    private final UserAnswerService userAnswerService;
    private final PackService packService;

    public QuestionRestController(QuestionService questionService,
                                  WikipediaService wikipediaService,
                                  UserService userService,
                                  UserAnswerService userAnswerService,
                                  PackService packService) {
        this.questionService = questionService;
        this.wikipediaService = wikipediaService;
        this.userService = userService;
        this.userAnswerService = userAnswerService;
        this.packService = packService;
    }

    @GetMapping
    public List<Question> all(){
        return questionService.findAll();
    }

    @GetMapping("/{id}")
    public Question findById(@PathVariable Long id){
        return questionService.findById(id);
    }

    @PostMapping
    public void createQuestion(@RequestBody Question question){
        questionService.create(question);
    }

    @PutMapping("/{id}")
    public void updateQuestion(@PathVariable Long id,@RequestBody Question questionNew){
        Question question = questionService.findById(id);
        question.setText(questionNew.getText());
        question.setAnswer(questionNew.getAnswer());
        question.setComment(questionNew.getComment());
        question.setPack(questionNew.getPack());
        question.setCreatedTime(questionNew.getCreatedTime());
        question.setDifficulty(questionNew.getDifficulty());
        questionService.create(question);

    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id){
        Question question = questionService.findById(id);
        questionService.delete(question);
    }

    @GetMapping("/{id}/answer")
    public AnswerDto getAnswer(@PathVariable Long id){
        Question question = questionService.findById(id);
        String wiki = wikipediaService.getSummary(question.getTopic());
        return new AnswerDto(question.getAnswer(), question.getComment(), wiki);
    }

    @GetMapping("/unanswered")
    public List<Question> unanswered(@RequestParam Long packId, Authentication authentication) {
        Pack pack = packService.findById(packId);
        User user = userService.findUserByUsername(authentication.getName());
        return questionService.findUnansweredByPackAndUser(pack, user);
    }

    @PostMapping("/{id}/submit")
    public AnswerDto submitAnswer(@PathVariable Long id,
                                  @RequestBody Map<String, String> body,
                                  Authentication authentication) {
        Question question = questionService.findById(id);
        String userAnswer = body.getOrDefault("answer", "").trim();
        boolean isCorrect = userAnswer.equalsIgnoreCase(question.getAnswer().trim());

        User user = userService.findUserByUsername(authentication.getName());
        UserAnswer ua = new UserAnswer();
        ua.setQuestion(question);
        ua.setUser(user);
        ua.setStatus(isCorrect ? AnswerStatus.CORRECT : AnswerStatus.INCORRECT);
        userAnswerService.save(ua);

        String wiki = wikipediaService.getSummary(question.getTopic());
        return new AnswerDto(question.getAnswer(), question.getComment(), wiki);
    }
}

