package ru.khrustal.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "reservation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_uid", nullable = false)
    private UUID reservationUid;

    @Column(name = "username", length = 80, nullable = false)
    private String username;

    @Column(name = "book_uid", nullable = false)
    private UUID bookUid;

    @Column(name = "library_uid", nullable = false)
    private UUID libraryUid;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) NOT NULL CHECK (status IN ('RENTED', 'RETURNED', 'EXPIRED'))")
    private Status status;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "till_date", nullable = false)
    private Date tillDate;
}
