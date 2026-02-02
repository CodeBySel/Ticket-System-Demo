package com.example.cinema.repository;

import com.example.cinema.model.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
}
