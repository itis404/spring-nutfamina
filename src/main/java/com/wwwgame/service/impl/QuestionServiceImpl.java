package com.wwwgame.service.impl;

import com.wwwgame.entity.Pack;
import com.wwwgame.entity.Question;
import com.wwwgame.entity.User;
import com.wwwgame.repository.PackRepository;
import com.wwwgame.repository.QuestionRepository;
import com.wwwgame.repository.UserRepository;
import com.wwwgame.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final PackRepository packRepository;
    private final UserRepository userRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                               PackRepository packRepository,
                               UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.packRepository = packRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(Question question) {
        questionRepository.save(question);
    }

    @Transactional
    @Override
    public void createForPack(Question question, Long packId, String authorUsername) {
        Pack pack = packRepository.findById(packId).orElseThrow();
        User author = userRepository.findByUsername(authorUsername);

        Question q = new Question();
        q.setText(question.getText());
        q.setAnswer(question.getAnswer());
        q.setComment(question.getComment());
        q.setTopic(question.getTopic());
        q.setDifficulty(question.getDifficulty());
        q.setCreatedTime(java.time.LocalDateTime.now());
        q.setPack(pack);
        q.setAuthor(author);

        questionRepository.save(q);
    }

    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> findByPack(Pack pack) {
        return questionRepository.findByPack(pack);
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Question> findUnansweredByPackAndUser(Pack pack, User user) {
        return questionRepository.findUnasweredByPack(pack,user);
    }
}
