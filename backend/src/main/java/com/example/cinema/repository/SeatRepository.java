package com.example.cinema.repository;

import com.example.cinema.model.Seat;
import com.example.cinema.model.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreeningIdOrderByRowLabelAscSeatNumberAsc(Long screeningId);
    List<Seat> findByScreeningIdAndStatus(Long screeningId, SeatStatus status);
    List<Seat> findByScreeningIdAndStatusAndLockTimeBefore(Long screeningId, SeatStatus status, LocalDateTime time);
    List<Seat> findByStatusAndLockTimeBefore(SeatStatus status, LocalDateTime time);
}
