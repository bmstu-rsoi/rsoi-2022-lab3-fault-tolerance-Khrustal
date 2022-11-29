package ru.khrustal.gateway.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.khrustal.dto.rating.UserRatingResponse;

@Controller
@RequestMapping("/api/v1/rating")
public class RatingRest {

    @Value("${services.ports.rating}")
    private String ratingPort;

    public static final String BASE_URL = "http://rating:8050/api/v1/rating";

    @GetMapping
    public ResponseEntity<UserRatingResponse> getUserRating(@RequestHeader("X-User-Name") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "?username=" + username;
        UserRatingResponse result = restTemplate.getForObject(url, UserRatingResponse.class);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/decrease")
    public ResponseEntity<?> decreaseUserRating(@RequestParam("username") String username,
                                   @RequestParam("expired") Boolean expired,
                                   @RequestParam("badCondition") Boolean badCondition) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "/decrease" + "?username=" + username + "&expired=" + expired + "&badCondition=" + badCondition;
        restTemplate.postForLocation(url, null);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/increase")
    public ResponseEntity<?> decreaseUserRating(@RequestParam("username") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "/increase" + "?username=" + username;
        restTemplate.postForLocation(url, null);
        return ResponseEntity.noContent().build();
    }
}
