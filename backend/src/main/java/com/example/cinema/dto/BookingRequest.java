package com.example.cinema.dto;

import com.example.cinema.model.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Screening ID is required")
    private Long screeningId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<Long> seatIds;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be a positive number")
    private Integer age;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    private String promoCode;
}
