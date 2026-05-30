package com.wwwgame.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;
    @ManyToOne
    private Pack pack;
    private String text;
    private String answer;
    private String comment;
    private LocalDateTime createdTime;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    private String topic;
}
