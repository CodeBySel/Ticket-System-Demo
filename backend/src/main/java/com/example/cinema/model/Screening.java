package com.example.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Movie movie;

    @ManyToOne(optional = false)
    @JsonIgnore
    private CinemaHall cinemaHall;

    private LocalDateTime startTime;
    private Double basePrice;

    @OneToMany(mappedBy = "screening")
    @JsonIgnore
    private List<Seat> seats = new ArrayList<>();
}
