package ru.khrustal.gateway.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.khrustal.dto.PaginationResponse;
import ru.khrustal.dto.library.BookDto;
import ru.khrustal.dto.library.LibraryDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/libraries")
public class LibraryController {

    @Value("${services.ports.library}")
    private String libraryPort;

    public static final String BASE_URL = "http://library:8060/api/v1/libraries/";

    @GetMapping("")
    public ResponseEntity<?> getCityLibs(@RequestParam("city") String city) {
        String uri = "http://library:8060/api/v1/libraries?city=" + city;
        RestTemplate restTemplate = new RestTemplate();
        PaginationResponse<?> result = restTemplate.getForObject(uri, PaginationResponse.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{libraryUid}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLibBooks(@PathVariable("libraryUid") UUID libraryUid,
                                                   @RequestParam("showAll") Boolean showAll) {
        String uri = BASE_URL + libraryUid + "/books?showAll=" + showAll;
        RestTemplate restTemplate = new RestTemplate();
        PaginationResponse<?> result = restTemplate.getForObject(uri, PaginationResponse.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "{libraryUid}/book/{bookUid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> getBookInfo(@PathVariable("libraryUid") UUID libraryUid,
                                               @PathVariable("bookUid") UUID bookUid) {
        String uri = BASE_URL + libraryUid + "/book/" + bookUid;
        RestTemplate restTemplate = new RestTemplate();
        BookDto result = restTemplate.getForObject(uri, BookDto.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "{libraryUid}/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LibraryDto> getLibInfo(@PathVariable("libraryUid") UUID libraryUid) {
        String uri = BASE_URL + libraryUid + "/info";
        RestTemplate restTemplate = new RestTemplate();
        LibraryDto result = restTemplate.getForObject(uri, LibraryDto.class);
        return ResponseEntity.ok(result);
    }
}
