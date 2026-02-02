package com.example.cinema.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingResponse {
    private Long bookingId;
    private List<Long> ticketIds;
    private Double totalPrice;
    private LocalDateTime bookingDate;
}
