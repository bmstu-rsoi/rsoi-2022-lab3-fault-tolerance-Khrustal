package ru.khrustal.reservation.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khrustal.dto.reservation.ReturnBookRequest;
import ru.khrustal.dto.reservation.TakeBookRequest;
import ru.khrustal.reservation.service.ReservationService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserBooksInfo(@RequestParam("username") String username) {
        return reservationService.getUserBooksInfo(username);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> takeBook(@RequestParam("username") String username,
                                      @RequestBody TakeBookRequest request) {
        return reservationService.takeBook(username, request);
    }

    @PostMapping(value = "/{reservationUid}/return", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> takeBook(@RequestParam("username") String username,
                                      @PathVariable("reservationUid") UUID reservationUid,
                                      @RequestBody ReturnBookRequest request) {
        return reservationService.returnBook(username, reservationUid, request);
    }
}
