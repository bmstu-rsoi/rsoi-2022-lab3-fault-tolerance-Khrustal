package ru.khrustal.dto.library;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    public BookDto(String uid) {
        this.bookUid = uid;
    }
    private String bookUid;
    private String name;
    private String author;
    private String genre;
    private String condition;
    private Long avaiblableCount;
}
