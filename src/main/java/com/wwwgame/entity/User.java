package com.wwwgame.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Email(message = "Некорректный email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @JsonIgnore
    @Schema(hidden = true)
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @Schema(hidden = true)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_favorite_packs",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "pack_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Pack> favoritePacks = new ArrayList<>();
}


