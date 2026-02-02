package com.example.cinema.service;

import com.example.cinema.dto.BookingRequest;
import com.example.cinema.model.Booking;
import com.example.cinema.model.BookingStatus;
import com.example.cinema.model.Customer;
import com.example.cinema.model.PromoCode;
import com.example.cinema.model.Screening;
import com.example.cinema.model.Seat;
import com.example.cinema.model.SeatStatus;
import com.example.cinema.model.Ticket;
import com.example.cinema.model.TicketType;
import com.example.cinema.repository.BookingRepository;
import com.example.cinema.repository.PromoCodeRepository;
import com.example.cinema.repository.ScreeningRepository;
import com.example.cinema.repository.SeatRepository;
import com.example.cinema.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Handles booking workflows such as seat availability checks, seat locking,
 * payment orchestration, ticket generation, and confirmation.
 */
@Service
public class BookingService {

    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;
    private final BookingRepository bookingRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final TicketService ticketService;
    private final EmailNotificationService emailNotificationService;

    public BookingService(
            SeatRepository seatRepository,
            ScreeningRepository screeningRepository,
            BookingRepository bookingRepository,
            PromoCodeRepository promoCodeRepository,
            UserRepository userRepository,
            PaymentService paymentService,
            TicketService ticketService,
            EmailNotificationService emailNotificationService
    ) {
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.bookingRepository = bookingRepository;
        this.promoCodeRepository = promoCodeRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.ticketService = ticketService;
        this.emailNotificationService = emailNotificationService;
    }

    /**
     * Retrieves all seats for a screening ordered by row and seat number.
     *
     * @param screeningId the screening identifier
     * @return list of seats for the screening
     */
    public List<Seat> getSeatsByScreening(Long screeningId) {
        return seatRepository.findByScreeningIdOrderByRowLabelAscSeatNumberAsc(screeningId);
    }

    /**
     * Checks if the provided seats are available for the given screening.
     *
     * @param screeningId the screening identifier
     * @param seatIds list of seat identifiers
     * @return true when all seats belong to the screening and are AVAILABLE
     */
    public boolean checkSeatAvailability(Long screeningId, List<Long> seatIds) {
        List<Seat> seats = seatRepository.findAllById(seatIds);
        for (Seat seat : seats) {
            if (!seat.getScreening().getId().equals(screeningId)) {
                return false;
            }
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                return false;
            }
        }
        return seats.size() == seatIds.size();
    }

    /**
     * Confirms a booking by locking seats, processing payment, generating tickets,
     * and marking seats as BOOKED.
     *
     * @param request booking request payload
     * @return the confirmed booking
     */
    @Transactional
    public Booking confirmBooking(BookingRequest request) {
        Screening screening = screeningRepository.findById(request.getScreeningId())
                .orElseThrow(() -> new RuntimeException("Screening not found with id: " + request.getScreeningId()));

        Customer customer = userRepository.findById(request.getUserId())
                .filter(Customer.class::isInstance)
                .map(Customer.class::cast)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getUserId()));

        List<Seat> seats = seatRepository.findAllById(request.getSeatIds());
        if (!checkSeatAvailability(request.getScreeningId(), request.getSeatIds())) {
            throw new RuntimeException("One or more seats are not available.");
        }

        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.LOCKED);
            seat.setLockedByUserId(customer.getId());
            seat.setLockTime(LocalDateTime.now());
        }
        seatRepository.saveAll(seats);

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setScreening(screening);
        booking.setSeats(seats);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.RESERVED);

        Optional<PromoCode> promoCode = Optional.empty();
        if (request.getPromoCode() != null && !request.getPromoCode().isBlank()) {
            promoCode = promoCodeRepository.findByCodeAndActiveTrue(request.getPromoCode())
                    .filter(code -> code.getExpiresAt() == null || code.getExpiresAt().isAfter(LocalDateTime.now()));
            promoCode.ifPresent(booking::setPromoCode);
        }

        double basePrice = screening.getBasePrice() != null ? screening.getBasePrice() : 10.0;
        double totalPrice = basePrice * seats.size();
        if (promoCode.isPresent() && promoCode.get().getDiscountPercent() != null) {
            totalPrice = totalPrice * (1 - promoCode.get().getDiscountPercent() / 100.0);
        }
        booking.setTotalPrice(totalPrice);
        bookingRepository.save(booking);

        paymentService.processPayment(booking, request.getPaymentMethod(), totalPrice);
        List<Ticket> tickets = ticketService.generateTickets(booking, screening, seats, TicketType.STANDARD, basePrice);
        booking.setTickets(tickets);
        booking.setStatus(BookingStatus.CONFIRMED);

        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.BOOKED);
            seat.setLockedByUserId(null);
            seat.setLockTime(null);
        }
        seatRepository.saveAll(seats);
        bookingRepository.save(booking);

        emailNotificationService.sendBookingConfirmation(customer, booking);
        return booking;
    }
}
