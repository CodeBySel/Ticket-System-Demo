package com.example.cinema.service;

import com.example.cinema.model.Booking;
import com.example.cinema.model.User;
import org.springframework.stereotype.Service;

/**
 * Sends email notifications related to bookings.
 */
@Service
public class EmailNotificationService {
    /**
     * Sends a booking confirmation message to the user.
     *
     * @param user booking owner
     * @param booking booking details
     */
    public void sendBookingConfirmation(User user, Booking booking) {
        // Placeholder for real email sending logic.
    }
}
