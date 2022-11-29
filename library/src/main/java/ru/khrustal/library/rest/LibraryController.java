package ru.khrustal.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khrustal.dto.PaginationResponse;
import ru.khrustal.dto.library.BookDto;
import ru.khrustal.dto.library.LibraryDto;
import ru.khrustal.library.service.LibraryService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/libraries")
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping(value = "{libraryUid}/book/{bookUid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> getBookInfo(@PathVariable("libraryUid") UUID libraryUid,
                                               @PathVariable("bookUid") UUID bookUid) {
        return libraryService.getBookInfo(libraryUid, bookUid);
    }

    @GetMapping(value = "{libraryUid}/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LibraryDto> getLibInfo(@PathVariable("libraryUid") UUID libraryUid) {
        return libraryService.getLibInfo(libraryUid);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginationResponse<LibraryDto> getCityLibs(@RequestParam("city") String city) {
        return libraryService.getCityLibs(city);
    }

    @GetMapping(value = "/{libraryUid}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginationResponse<BookDto> getLibBooks(@PathVariable("libraryUid") UUID libraryUid,
                                                   @RequestParam("showAll") Boolean showAll) {
        return libraryService.getLibBooks(libraryUid, showAll);
    }
}
