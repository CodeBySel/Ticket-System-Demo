package com.example.cinema.controller;

import com.example.cinema.model.Screening;
import com.example.cinema.model.Seat;
import com.example.cinema.repository.ScreeningRepository;
import com.example.cinema.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Exposes screening schedules and seat availability.
 */
@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {
    private final ScreeningRepository screeningRepository;
    private final BookingService bookingService;

    public ScreeningController(ScreeningRepository screeningRepository, BookingService bookingService) {
        this.screeningRepository = screeningRepository;
        this.bookingService = bookingService;
    }

    /**
     * Returns screenings for a movie ordered by start time.
     *
     * @param movieId movie identifier
     * @return list of screenings
     */
    @GetMapping("/movie/{movieId}")
    public List<Screening> getScreeningsByMovie(@PathVariable Long movieId) {
        return screeningRepository.findByMovieIdOrderByStartTimeAsc(movieId);
    }

    /**
     * Returns seat list for a given screening.
     *
     * @param screeningId screening identifier
     * @return list of seats for screening
     */
    @GetMapping("/{screeningId}/seats")
    public List<Seat> getSeatsForScreening(@PathVariable Long screeningId) {
        return bookingService.getSeatsByScreening(screeningId);
    }
}
