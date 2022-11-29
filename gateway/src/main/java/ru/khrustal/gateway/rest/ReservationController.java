package ru.khrustal.gateway.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.khrustal.dto.reservation.ReturnBookRequest;
import ru.khrustal.dto.reservation.TakeBookRequest;
import ru.khrustal.dto.reservation.TakeBookResponse;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Value("${services.ports.reservation}")
    private String reservationPort;

    public static final String BASE_URL = "http://reservation:8070/api/v1/reservations";

    @GetMapping
    public ResponseEntity<?> getUserReservedBooks(@RequestHeader("X-User-Name") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "?username=" + username;
        List<?> result = restTemplate.getForObject(url, List.class);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> takeBook(@RequestHeader("X-User-Name") String username,
                                      @RequestBody TakeBookRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TakeBookRequest> rq = new HttpEntity<>(request, null);
        try {
            return restTemplate.postForEntity(BASE_URL + "?username=" + username, rq, TakeBookResponse.class);
        } catch (HttpStatusCodeException e) {
           return ResponseEntity.badRequest().body(e.getResponseBodyAsString());
        }
    }
    @PostMapping("/{reservationUid}/return")
    public ResponseEntity<?> returnBook(@PathVariable("reservationUid")UUID reservationUid,
                                        @RequestHeader("X-User-Name") String username,
                                        @RequestBody ReturnBookRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ReturnBookRequest> rq = new HttpEntity<>(request, null);
        try {
            return restTemplate.postForEntity(BASE_URL + "/" + reservationUid + "/return" + "?username=" + username, rq, ReturnBookRequest.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.badRequest().body(e.getResponseBodyAsString());
        }
    }
}
