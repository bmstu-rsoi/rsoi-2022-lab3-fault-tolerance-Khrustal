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
    private String bookUid;
    private String name;
    private String author;
    private String genre;
    private String condition;
    private Long avaiblableCount;
}
