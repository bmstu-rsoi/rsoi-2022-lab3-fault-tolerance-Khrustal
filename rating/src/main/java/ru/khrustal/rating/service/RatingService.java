package ru.khrustal.rating.service;

import ru.khrustal.dto.rating.UserRatingResponse;

public interface RatingService {
    UserRatingResponse getUserRating(String user);
    void decreaseUserRating(String username, Boolean expired, Boolean badCondition);
    void increaseUserRating(String username);
}
