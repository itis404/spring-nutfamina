package com.wwwgame.service;

import com.wwwgame.entity.Difficulty;
import com.wwwgame.entity.Pack;
import com.wwwgame.entity.Question;
import com.wwwgame.entity.User;
import com.wwwgame.repository.PackRepository;
import com.wwwgame.repository.QuestionRepository;
import com.wwwgame.repository.UserRepository;
import com.wwwgame.service.impl.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private PackRepository packRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    void createForPack_savesQuestionWithPackAndAuthor() {
        Pack pack = new Pack();
        pack.setId(1L);

        User author = new User();
        author.setUsername("alice");

        Question input = new Question();
        input.setText("Кто написал «Войну и мир»?");
        input.setAnswer("Толстой");
        input.setComment("Классика");
        input.setTopic("Лев Толстой");
        input.setDifficulty(Difficulty.MEDIUM);

        when(packRepository.findById(1L)).thenReturn(Optional.of(pack));
        when(userRepository.findByUsername("alice")).thenReturn(author);

        questionService.createForPack(input, 1L, "alice");

        ArgumentCaptor<Question> captor = ArgumentCaptor.forClass(Question.class);
        verify(questionRepository).save(captor.capture());

        Question saved = captor.getValue();
        assertEquals("Кто написал «Войну и мир»?", saved.getText());
        assertEquals("Толстой", saved.getAnswer());
        assertEquals(pack, saved.getPack());
        assertEquals(author, saved.getAuthor());
        assertEquals(Difficulty.MEDIUM, saved.getDifficulty());
        assertNotNull(saved.getCreatedTime());
    }

    @Test
    void findById_throwsWhenNotFound() {
        when(questionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> questionService.findById(99L));
    }

    @Test
    void delete_callsRepositoryDelete() {
        Question question = new Question();
        question.setId(5L);

        questionService.delete(question);

        verify(questionRepository).delete(question);
    }
}
