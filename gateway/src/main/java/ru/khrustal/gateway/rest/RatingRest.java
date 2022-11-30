package ru.khrustal.gateway.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.khrustal.dto.MessageDto;
import ru.khrustal.dto.rating.UserRatingResponse;
import ru.khrustal.dto.reservation.Message;

import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingRest {

    @Value("${services.ports.rating}")
    private String ratingPort;
    public static final String BASE_URL = "http://rating:8050/api/v1/rating";
    private final TaskScheduler scheduler;
    private static final Integer N = 2;
    private Integer errorsNumber = 0;
    private final Runnable healthCheck =
            new Runnable() {
                @Override
                public void run() {
                    try {
                        RestTemplate restTemplate = new RestTemplate();
                        restTemplate.getForEntity("http://rating:8050/manage/health", ResponseEntity.class);
                        log.info("Rating healthCheck passed");
                        errorsNumber = 0;
                    } catch (Exception e) {
                        log.error("Rating healthCheck for errors: " + errorsNumber);
                        scheduler.schedule(this, new Date(System.currentTimeMillis() + 10000L));
                    }
                }
            };

    @GetMapping
    public ResponseEntity<?> getUserRating(@RequestHeader("X-User-Name") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "?username=" + username;
        UserRatingResponse result = null;
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Rating Service unavailable"));
            } else {
                result = restTemplate.getForObject(url, UserRatingResponse.class);
                if (result != null) errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
           return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Rating Service unavailable", e.getMessage()));
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
