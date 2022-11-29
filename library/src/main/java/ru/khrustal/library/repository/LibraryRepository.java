package ru.khrustal.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khrustal.library.model.Library;

import java.util.List;
import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    List<Library> findAllByCity(String city);
    Library findByLibraryUid(UUID uuid);
}
