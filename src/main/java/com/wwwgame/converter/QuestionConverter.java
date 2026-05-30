package com.wwwgame.converter;

import com.wwwgame.dto.QuestionDto;
import com.wwwgame.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionConverter {
    public QuestionDto toDto(Question question){
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setText(question.getText());
        questionDto.setComment(question.getComment());
        questionDto.setDifficulty(question.getDifficulty());
        questionDto.setAuthorUsername(question.getAuthor().getUsername());
        questionDto.setCreatedAt(question.getCreatedTime());
        return questionDto;
    }
}
