package ru.khrustal.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.khrustal.dto.library.BookDto;
import ru.khrustal.dto.library.LibraryDto;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookReservationResponse {
    private String reservationUid;
    private String status;
    private Date startDate;
    private Date tillDate;
    private BookDto book;
    private LibraryDto library;
}
