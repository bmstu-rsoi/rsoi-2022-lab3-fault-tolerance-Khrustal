package ru.khrustal.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "library_uid", nullable = false)
    private UUID libraryUid;
    @Column(name = "name", length = 80, nullable = false)
    private String name;
    @Column(name = "city", length = 255, nullable = false)
    private String city;
    @Column(name = "address", length = 255, nullable = false)
    private String address;
    @ManyToMany
    private List<Book> books;
}
