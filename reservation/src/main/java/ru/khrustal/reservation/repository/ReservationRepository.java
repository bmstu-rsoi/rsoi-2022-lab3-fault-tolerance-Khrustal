package ru.khrustal.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khrustal.reservation.model.Reservation;
import ru.khrustal.reservation.model.Status;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUsernameAndStatusIn(String username, Set<Status> statuses);
    Reservation findByReservationUid(UUID uuid);
}
