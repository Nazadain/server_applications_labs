package ru.nikita.labs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value", nullable = false, unique = true)
    private String value;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
