package com.example.cinema.controller;

import com.example.cinema.dto.BookingRequest;
import com.example.cinema.dto.BookingResponse;
import com.example.cinema.model.Booking;
import com.example.cinema.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles booking creation requests.
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    /**
     * Creates a booking from the provided request payload.
     *
     * @param request booking request
     * @return booking response or error message
     */
    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest request) {
        try {
            Booking booking = bookingService.confirmBooking(request);
            BookingResponse response = new BookingResponse();
            response.setBookingId(booking.getId());
            response.setBookingDate(booking.getBookingDate());
            response.setTotalPrice(booking.getTotalPrice());
            response.setTicketIds(booking.getTickets().stream().map(t -> t.getId()).toList());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
