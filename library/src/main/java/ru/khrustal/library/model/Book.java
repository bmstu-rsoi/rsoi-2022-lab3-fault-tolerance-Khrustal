package ru.khrustal.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "books")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_uid", nullable = false)
    private UUID bookUid;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "author", length = 255, nullable = false)
    private String author;

    @Column(name = "genre", length = 255, nullable = false)
    private String genre;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'EXCELLENT' CHECK (condition IN ('EXCELLENT', 'GOOD', 'BAD'))")
    private Condition condition = Condition.EXCELLENT;
}
