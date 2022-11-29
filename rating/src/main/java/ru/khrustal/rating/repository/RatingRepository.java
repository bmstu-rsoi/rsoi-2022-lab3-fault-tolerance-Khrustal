package ru.khrustal.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khrustal.rating.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByUsername(String username);
}
