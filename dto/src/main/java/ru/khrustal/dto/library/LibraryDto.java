package ru.khrustal.dto.library;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibraryDto {
    public LibraryDto(String uid) {
        this.libraryUid = uid;
    }
    private String libraryUid;
    private String name;
    private String address;
    private String city;
}
