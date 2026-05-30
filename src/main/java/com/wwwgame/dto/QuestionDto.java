package com.wwwgame.dto;

import com.wwwgame.entity.Difficulty;
import com.wwwgame.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDto {
    private Long id;
    private String text;
    private String authorUsername;
    private String comment;
    private Difficulty difficulty;
    private LocalDateTime createdAt;

}
