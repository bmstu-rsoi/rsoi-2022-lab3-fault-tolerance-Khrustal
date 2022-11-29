package ru.khrustal.gateway.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.khrustal.dto.MessageDto;
import ru.khrustal.dto.rating.UserRatingResponse;
import ru.khrustal.dto.reservation.Message;

@Controller
@Slf4j
@RequestMapping("/api/v1/rating")
public class RatingRest {

    @Value("${services.ports.rating}")
    private String ratingPort;

    public static final String BASE_URL = "http://rating:8050/api/v1/rating";

    @GetMapping
    public ResponseEntity<?> getUserRating(@RequestHeader("X-User-Name") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "?username=" + username;
        UserRatingResponse result = null;
        try {
            result = restTemplate.getForObject(url, UserRatingResponse.class);
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto(e.getMessage() + "\n|||||||\n" + e.getStackTrace()));
        }
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
    public ResponseEntity<?> increaseUserRating(@RequestParam("username") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "/increase" + "?username=" + username;
        restTemplate.postForLocation(url, null);
        return ResponseEntity.noContent().build();
    }
}
