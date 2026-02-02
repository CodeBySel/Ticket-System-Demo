package com.example.cinema.service;

import com.example.cinema.model.Seat;
import com.example.cinema.model.SeatStatus;
import com.example.cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Periodically releases seats that have been locked for too long.
 */
@Service
public class SeatCleanupScheduler {

    @Autowired
    private SeatRepository seatRepository;

    /**
     * Resets expired seat locks back to AVAILABLE.
     */
    @Scheduled(fixedRate = 60000) // Run every minute
    @Transactional
    public void releaseLockedSeats() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<Seat> expiredLocks = seatRepository.findByStatusAndLockTimeBefore(SeatStatus.LOCKED, tenMinutesAgo);

        for (Seat seat : expiredLocks) {
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setLockedByUserId(null);
            seat.setLockTime(null);
        }

        if (!expiredLocks.isEmpty()) {
            seatRepository.saveAll(expiredLocks);
        }
    }
}
