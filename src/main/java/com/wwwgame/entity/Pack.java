package com.wwwgame.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "packs")
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User author;

    private String name;
    private String description;
    private LocalDateTime createdAt;

    @JsonIgnore
    @Schema(hidden = true)
    @OneToMany(mappedBy = "pack")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Question> questions = new ArrayList<>();
}
