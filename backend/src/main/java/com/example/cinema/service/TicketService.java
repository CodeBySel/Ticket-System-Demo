package com.example.cinema.service;

import com.example.cinema.model.Booking;
import com.example.cinema.model.Screening;
import com.example.cinema.model.Seat;
import com.example.cinema.model.Ticket;
import com.example.cinema.model.TicketType;
import com.example.cinema.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates tickets for confirmed bookings.
 */
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketFactory ticketFactory;

    public TicketService(TicketRepository ticketRepository, TicketFactory ticketFactory) {
        this.ticketRepository = ticketRepository;
        this.ticketFactory = ticketFactory;
    }

    /**
     * Creates and stores tickets for the selected seats.
     *
     * @param booking booking context
     * @param screening screening context
     * @param seats seats to issue tickets for
     * @param type ticket type to generate
     * @param pricePerSeat unit price
     * @return persisted tickets
     */
    public List<Ticket> generateTickets(Booking booking, Screening screening, List<Seat> seats, TicketType type, Double pricePerSeat) {
        List<Ticket> tickets = new ArrayList<>();
        for (Seat seat : seats) {
            tickets.add(ticketFactory.createTicket(booking, screening, seat, type, pricePerSeat));
        }
        return ticketRepository.saveAll(tickets);
    }
}
