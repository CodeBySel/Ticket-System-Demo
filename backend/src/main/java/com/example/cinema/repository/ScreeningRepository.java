package com.example.cinema.repository;

import com.example.cinema.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findByMovieIdOrderByStartTimeAsc(Long movieId);
}
