package ru.khrustal.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khrustal.library.model.Book;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByBookUid(UUID uuid);
}
