package ru.khrustal.library.service;

import org.springframework.http.ResponseEntity;
import ru.khrustal.dto.PaginationResponse;
import ru.khrustal.dto.library.BookDto;
import ru.khrustal.dto.library.LibraryDto;

import java.util.UUID;

public interface LibraryService {
    PaginationResponse<LibraryDto> getCityLibs(String city);
    PaginationResponse<BookDto> getLibBooks(UUID libraryUUID, Boolean showAll);
    ResponseEntity<LibraryDto> getLibInfo(UUID uuid);
    ResponseEntity<BookDto> getBookInfo(UUID libraryUuid, UUID bookUuid);
}
