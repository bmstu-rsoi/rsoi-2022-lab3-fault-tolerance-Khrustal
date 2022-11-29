package ru.khrustal.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.khrustal.dto.library.BookDto;
import ru.khrustal.dto.library.LibraryDto;
import ru.khrustal.dto.rating.UserRatingResponse;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeBookResponse {
    private UUID reservationUid;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date tillDate;
    private BookDto book;
    private LibraryDto library;
    private UserRatingResponse rating;
}
