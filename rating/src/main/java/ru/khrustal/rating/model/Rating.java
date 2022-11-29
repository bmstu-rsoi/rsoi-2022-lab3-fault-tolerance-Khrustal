package ru.khrustal.rating.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "rating")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 80, nullable = false)
    private String username;

    @Column(name = "stars", columnDefinition = "INT NOT NULL CHECK (stars BETWEEN 0 AND 100)")
    private Integer stars;
}
