package ru.khrustal.rating.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khrustal.dto.rating.UserRatingResponse;
import ru.khrustal.rating.model.Rating;
import ru.khrustal.rating.repository.RatingRepository;
import ru.khrustal.rating.service.RatingService;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    @Override
    public UserRatingResponse getUserRating(String user) {
        Rating rating = ratingRepository.findByUsername(user);
        if(rating == null) {
            rating = new Rating();
            rating.setUsername(user);
            rating.setStars(75);
            ratingRepository.save(rating);
        }
        return new UserRatingResponse(rating.getStars());
    }

    @Override
    public void decreaseUserRating(String username, Boolean expired, Boolean badCondition) {
        Rating rating = ratingRepository.findByUsername(username);
        Integer stars = rating.getStars();
        if(expired) stars -= 10;
        if(badCondition) stars -= 10;
        if(stars <= 0) stars = 1;
        rating.setStars(stars);
        ratingRepository.save(rating);
    }

    @Override
    public void increaseUserRating(String username) {
        Rating rating = ratingRepository.findByUsername(username);
        Integer stars = rating.getStars() + 1;
        if (stars > 100) stars = 100;
        rating.setStars(stars);
        ratingRepository.save(rating);
    }
}
