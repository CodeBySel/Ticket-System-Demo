package com.example.cinema.service;

import com.example.cinema.dto.BookingRequest;
import com.example.cinema.model.Booking;
import com.example.cinema.model.BookingStatus;
import com.example.cinema.model.Customer;
import com.example.cinema.model.Payment;
import com.example.cinema.model.PaymentMethod;
import com.example.cinema.model.Screening;
import com.example.cinema.model.Seat;
import com.example.cinema.model.SeatStatus;
import com.example.cinema.model.Ticket;
import com.example.cinema.repository.BookingRepository;
import com.example.cinema.repository.PromoCodeRepository;
import com.example.cinema.repository.ScreeningRepository;
import com.example.cinema.repository.SeatRepository;
import com.example.cinema.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private TicketService ticketService;

    @Mock
    private EmailNotificationService emailNotificationService;

    @InjectMocks
    private BookingService bookingService;

    private Seat seat;
    private BookingRequest request;
    private Screening screening;
    private Customer customer;

    @BeforeEach
    void setUp() {
        screening = new Screening();
        screening.setId(1L);
        screening.setBasePrice(10.0);

        seat = new Seat();
        seat.setId(1L);
        seat.setRowLabel("A");
        seat.setSeatNumber(1);
        seat.setStatus(SeatStatus.AVAILABLE);
        seat.setScreening(screening);

        request = new BookingRequest();
        request.setUserId(1L);
        request.setSeatIds(Collections.singletonList(1L));
        request.setScreeningId(1L);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setAge(25);
        request.setPaymentMethod(PaymentMethod.CARD);

        customer = new Customer();
        customer.setId(1L);
    }

    @Test
    void checkSeatAvailability_ReturnsTrue() {
        when(seatRepository.findAllById(any())).thenReturn(Collections.singletonList(seat));

        boolean available = bookingService.checkSeatAvailability(1L, List.of(1L));

        assertTrue(available);
    }

    @Test
    void checkSeatAvailability_ReturnsFalse_WhenSeatBooked() {
        seat.setStatus(SeatStatus.BOOKED);
        when(seatRepository.findAllById(any())).thenReturn(Collections.singletonList(seat));

        boolean available = bookingService.checkSeatAvailability(1L, List.of(1L));

        assertFalse(available);
    }

    @Test
    void confirmBooking_Success() {
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(seatRepository.findAllById(any())).thenReturn(Collections.singletonList(seat));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentService.processPayment(any(), any(), any())).thenReturn(new Payment());
        when(ticketService.generateTickets(any(), any(), any(), any(), any())).thenReturn(List.of(new Ticket()));

        Booking booking = bookingService.confirmBooking(request);

        assertNotNull(booking);
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        verify(seatRepository, times(2)).saveAll(any());
        verify(emailNotificationService, times(1)).sendBookingConfirmation(any(), any());
    }

    @Test
    void confirmBooking_FailsWhenSeatUnavailable() {
        seat.setStatus(SeatStatus.BOOKED);
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(seatRepository.findAllById(any())).thenReturn(Collections.singletonList(seat));

        Exception exception = assertThrows(RuntimeException.class, () -> bookingService.confirmBooking(request));
        assertTrue(exception.getMessage().contains("not available"));
    }
}
