package com.wwwgame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    private String answer;
    private String comment;
    private String wikipediaSummary;
}
