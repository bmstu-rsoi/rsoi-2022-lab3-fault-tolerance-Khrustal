package ru.khrustal.gateway.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.khrustal.dto.MessageDto;
import ru.khrustal.dto.PaginationResponse;
import ru.khrustal.dto.library.BookDto;
import ru.khrustal.dto.library.LibraryDto;

import java.util.Date;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/v1/libraries")
@RequiredArgsConstructor
public class LibraryController {

    @Value("${services.ports.library}")
    private String libraryPort;
    public static final String BASE_URL = "http://library:8060/api/v1/libraries/";
    private final TaskScheduler scheduler;
    private static final Integer N = 10;
    private Integer errorsNumber = 0;
    private final Runnable healthCheck =
            new Runnable() {
                @Override
                public void run() {
                    try {
                        RestTemplate restTemplate = new RestTemplate();
                        restTemplate.getForEntity("http://library:8060/manage/health", ResponseEntity.class);
                        log.info("Library healthCheck passed");
                        errorsNumber = 0;
                    } catch (Exception e) {
                        log.error("Library healthCheck for errors: " + errorsNumber);
                        scheduler.schedule(this, new Date(System.currentTimeMillis() + 10000L));
                    }
                }
            };

    @GetMapping("")
    public ResponseEntity<?> getCityLibs(@RequestParam("city") String city) {
        String uri = "http://library:8060/api/v1/libraries?city=" + city;
        RestTemplate restTemplate = new RestTemplate();
        PaginationResponse<?> result = null;
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Library Service unavailable"));
            } else {
                result = restTemplate.getForObject(uri, PaginationResponse.class);
                if (result != null) errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Library Service unavailable"));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{libraryUid}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLibBooks(@PathVariable("libraryUid") UUID libraryUid,
                                                   @RequestParam("showAll") Boolean showAll) {
        String uri = BASE_URL + libraryUid + "/books?showAll=" + showAll;
        RestTemplate restTemplate = new RestTemplate();
        PaginationResponse<?> result = null;
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Library Service unavailable"));
            } else {
                result = restTemplate.getForObject(uri, PaginationResponse.class);
                if (result != null) errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Library Service unavailable"));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "{libraryUid}/book/{bookUid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookInfo(@PathVariable("libraryUid") UUID libraryUid,
                                               @PathVariable("bookUid") UUID bookUid) {
        String uri = BASE_URL + libraryUid + "/book/" + bookUid;
        RestTemplate restTemplate = new RestTemplate();
        BookDto result = new BookDto(bookUid.toString());
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
            } else {
                result = restTemplate.getForObject(uri, BookDto.class);
                errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Library Service unavailable"));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "{libraryUid}/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLibInfo(@PathVariable("libraryUid") UUID libraryUid) {
        String uri = BASE_URL + libraryUid + "/info";
        RestTemplate restTemplate = new RestTemplate();
        LibraryDto result = new LibraryDto(libraryUid.toString());
        try {
            if (errorsNumber >= N) {
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
            } else {
                result = restTemplate.getForObject(uri, LibraryDto.class);
                errorsNumber = 0;
            }
        } catch (Exception e) {
            errorsNumber += 1;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new MessageDto("Library Service unavailable"));
        }
        return ResponseEntity.ok(result);
    }
}
