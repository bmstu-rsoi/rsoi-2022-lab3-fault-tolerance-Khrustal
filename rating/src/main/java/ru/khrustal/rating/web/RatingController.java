package ru.khrustal.rating.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khrustal.dto.rating.UserRatingResponse;
import ru.khrustal.rating.service.RatingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rating")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public UserRatingResponse getUserRating(@RequestParam("username") String username) {
        return ratingService.getUserRating(username);
    }

    @PostMapping("/decrease")
    public ResponseEntity<?> decreaseUserRating(@RequestParam("username") String username,
                                             @RequestParam("expired") Boolean expired,
                                             @RequestParam("badCondition") Boolean badCondition) {
        ratingService.decreaseUserRating(username, expired, badCondition);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/increase")
    public ResponseEntity<?> decreaseUserRating(@RequestParam("username") String username) {
        ratingService.increaseUserRating(username);
        return ResponseEntity.noContent().build();
    }
}
