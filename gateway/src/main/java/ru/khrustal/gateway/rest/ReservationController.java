package ru.khrustal.gateway.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.khrustal.dto.MessageDto;
import ru.khrustal.dto.reservation.ReturnBookRequest;
import ru.khrustal.dto.reservation.TakeBookRequest;
import ru.khrustal.dto.reservation.TakeBookResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Value("${services.ports.reservation}")
    private String reservationPort;


    public static final String BASE_URL = "http://reservation:8070/api/v1/reservations";

    private final TaskScheduler scheduler;
    private static final Integer N = 10;
    private Integer errorsNumber = 0;
    private final Runnable healthCheck =
            new Runnable() {
                @Override
                public void run() {
                    try {
                        RestTemplate restTemplate = new RestTemplate();
                        restTemplate.getForEntity("http://reservation:8070/manage/health", ResponseEntity.class);
                        log.info("healthCheck passed");
                        errorsNumber = 0;
                    } catch (Exception e) {
                        log.error("healthCheck - errors: " + errorsNumber);
                        scheduler.schedule(this, new Date(System.currentTimeMillis() + 10000L));
                    }
                }
            };

    @GetMapping
    public ResponseEntity<?> getUserReservedBooks(@RequestHeader("X-User-Name") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "?username=" + username;
        List<?> result = null;
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Reservation Service unavailable", errorsNumber.toString()));
            } else {
                result = restTemplate.getForObject(url, List.class);
                if (result != null) errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Reservation Service unavailable", errorsNumber.toString()));
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> takeBook(@RequestHeader("X-User-Name") String username,
                                      @RequestBody TakeBookRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TakeBookRequest> rq = new HttpEntity<>(request, null);
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Reservation Service unavailable"));
            } else {
                errorsNumber = 0;
                return restTemplate.postForEntity(BASE_URL + "?username=" + username, rq, TakeBookResponse.class);
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Reservation Service unavailable"));
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
        } catch (Exception e) {
            scheduler.schedule(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                restTemplate.postForEntity(BASE_URL + "/" + reservationUid + "/return" + "?username=" + username, rq, ReturnBookRequest.class);
                            } catch (Exception e) {
                                scheduler.schedule(this, new Date(System.currentTimeMillis() + 10000L));
                            }
                        }
                    }, new Date());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
