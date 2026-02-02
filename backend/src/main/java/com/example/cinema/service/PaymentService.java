package com.example.cinema.service;

import com.example.cinema.model.Booking;
import com.example.cinema.model.Payment;
import com.example.cinema.model.PaymentMethod;
import com.example.cinema.model.PaymentStatus;
import com.example.cinema.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Processes payments and persists payment records.
 */
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentConfiguration paymentConfiguration;

    public PaymentService(PaymentRepository paymentRepository, PaymentConfiguration paymentConfiguration) {
        this.paymentRepository = paymentRepository;
        this.paymentConfiguration = paymentConfiguration;
    }

    /**
     * Processes a payment for a booking and stores the transaction.
     *
     * @param booking booking to be paid
     * @param method payment method
     * @param amount amount to charge
     * @return persisted payment record
     */
    public Payment processPayment(Booking booking, PaymentMethod method, Double amount) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setExternalReference(paymentConfiguration.getProviderName() + "-" + booking.getId());
        payment.setCreatedAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }
}
