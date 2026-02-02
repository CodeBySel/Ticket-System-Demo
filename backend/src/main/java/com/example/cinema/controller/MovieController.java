package com.example.cinema.controller;

import com.example.cinema.model.Movie;
import com.example.cinema.repository.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Exposes movie catalog endpoints.
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Returns all available movies.
     *
     * @return list of movies
     */
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}

