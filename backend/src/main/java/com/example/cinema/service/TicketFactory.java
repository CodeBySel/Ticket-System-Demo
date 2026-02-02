package com.example.cinema.service;

import com.example.cinema.model.Booking;
import com.example.cinema.model.Screening;
import com.example.cinema.model.Seat;
import com.example.cinema.model.Ticket;
import com.example.cinema.model.TicketType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Factory for creating ticket instances with consistent defaults.
 */
@Component
public class TicketFactory {

    /**
     * Builds a ticket with the given parameters.
     *
     * @param booking booking context
     * @param screening screening context
     * @param seat seat associated with the ticket
     * @param type ticket type
     * @param price ticket price
     * @return new ticket instance
     */
    public Ticket createTicket(Booking booking, Screening screening, Seat seat, TicketType type, Double price) {
        Ticket ticket = new Ticket();
        ticket.setBooking(booking);
        ticket.setScreening(screening);
        ticket.setSeat(seat);
        ticket.setType(type);
        ticket.setPrice(price);
        ticket.setIssuedAt(LocalDateTime.now());
        return ticket;
    }
}
