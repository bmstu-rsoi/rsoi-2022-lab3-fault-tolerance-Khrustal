package ru.khrustal.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.khrustal.dto.PaginationResponse;
import ru.khrustal.dto.library.BookDto;
import ru.khrustal.dto.library.LibraryDto;
import ru.khrustal.library.model.Book;
import ru.khrustal.library.model.Library;
import ru.khrustal.library.repository.BookRepository;
import ru.khrustal.library.repository.LibraryRepository;
import ru.khrustal.library.service.LibraryService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    @Value("${services.gateway}")
    private String gatewayPort;

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    private LibraryDto convertToDto(Library library) {
        if(library == null) return null;
        return LibraryDto.builder()
                .libraryUid(library.getLibraryUid().toString())
                .name(library.getName())
                .address(library.getAddress())
                .city(library.getCity())
                .build();
    }

    private BookDto convertToDto(Book book, long available) {
        if(book == null) return null;
        return BookDto.builder()
                .bookUid(book.getBookUid().toString())
                .name(book.getName())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .condition(book.getCondition().name())
                .avaiblableCount(available)
                .build();
    }

    private long countAvailable(List<Book> books, String name) {
        return books.stream().filter(x -> x.getName().equals(name)).count();
    }

    @Override
    public PaginationResponse<LibraryDto> getCityLibs(String city) {
        List<Library> libraries = libraryRepository.findAllByCity(city);
        List<LibraryDto> dtos = libraries.stream().map(this::convertToDto).toList();
        return PaginationResponse.<LibraryDto>builder()
                .page(1)
                .pageSize(dtos.size())
                .totalElements(dtos.size())
                .items(dtos)
                .build();
    }

    @Override
    public PaginationResponse<BookDto> getLibBooks(UUID libraryUUID, Boolean showAll) {
        Library library = libraryRepository.findByLibraryUid(libraryUUID);
        List<Book> books = library.getBooks();
        List<BookDto> dtos = books.stream().map(x -> convertToDto(x, countAvailable(books, x.getName()))).toList();
        return PaginationResponse.<BookDto>builder()
                .page(1)
                .pageSize(dtos.size())
                .totalElements(dtos.size())
                .items(dtos)
                .build();
//        if (showAll) {
//            //ToDO
//        }
//        return null;
    }

    @Override
    public ResponseEntity<LibraryDto> getLibInfo(UUID uuid) {
        return ResponseEntity.ok(convertToDto(libraryRepository.findByLibraryUid(uuid)));
    }

    @Override
    public ResponseEntity<BookDto> getBookInfo(UUID libraryUuid, UUID bookUuid) {
        Library library = libraryRepository.findByLibraryUid(libraryUuid);
        Book book = bookRepository.findByBookUid(bookUuid);
        if(library.getBooks().contains(book)) {
            return ResponseEntity.ok(convertToDto(book, countAvailable(library.getBooks(), book.getName())));
        }
        return null;
    }
}
