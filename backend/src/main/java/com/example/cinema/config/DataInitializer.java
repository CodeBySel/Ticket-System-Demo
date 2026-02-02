package com.example.cinema.config;

import com.example.cinema.model.CinemaHall;
import com.example.cinema.model.Customer;
import com.example.cinema.model.Movie;
import com.example.cinema.model.Screening;
import com.example.cinema.model.Seat;
import com.example.cinema.model.SeatStatus;
import com.example.cinema.repository.CinemaHallRepository;
import com.example.cinema.repository.MovieRepository;
import com.example.cinema.repository.ScreeningRepository;
import com.example.cinema.repository.SeatRepository;
import com.example.cinema.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            SeatRepository seatRepository,
            MovieRepository movieRepository,
            ScreeningRepository screeningRepository,
            CinemaHallRepository cinemaHallRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (movieRepository.count() == 0) {
                Movie m1 = new Movie();
                m1.setTitle("Inception");
                m1.setGenre("Sci-Fi");
                m1.setDurationMinutes(148);
                m1.setPosterUrl("/posters/inception.svg");
                m1.setDescription("A thief enters dreams to steal secrets and plant ideas.");
                movieRepository.save(m1);

                Movie m2 = new Movie();
                m2.setTitle("The Matrix");
                m2.setGenre("Action");
                m2.setDurationMinutes(136);
                m2.setPosterUrl("/posters/matrix.svg");
                m2.setDescription("A hacker discovers the world is a simulation.");
                movieRepository.save(m2);

                Movie m3 = new Movie();
                m3.setTitle("Interstellar");
                m3.setGenre("Sci-Fi");
                m3.setDurationMinutes(169);
                m3.setPosterUrl("/posters/interstellar.svg");
                m3.setDescription("Explorers travel through a wormhole to save humanity.");
                movieRepository.save(m3);

                Movie m4 = new Movie();
                m4.setTitle("The Dark Knight");
                m4.setGenre("Action");
                m4.setDurationMinutes(152);
                m4.setPosterUrl("/posters/dark-knight.svg");
                m4.setDescription("Batman faces the Joker in Gotham City.");
                movieRepository.save(m4);

                Movie m5 = new Movie();
                m5.setTitle("Avatar");
                m5.setGenre("Adventure");
                m5.setDurationMinutes(162);
                m5.setPosterUrl("/posters/avatar.svg");
                m5.setDescription("A marine explores the alien world of Pandora.");
                movieRepository.save(m5);

                Movie m6 = new Movie();
                m6.setTitle("Titanic");
                m6.setGenre("Romance");
                m6.setDurationMinutes(195);
                m6.setPosterUrl("/posters/titanic.svg");
                m6.setDescription("A romance unfolds aboard the ill-fated Titanic.");
                movieRepository.save(m6);

                Movie m7 = new Movie();
                m7.setTitle("Joker");
                m7.setGenre("Drama");
                m7.setDurationMinutes(122);
                m7.setPosterUrl("/posters/joker.svg");
                m7.setDescription("The origin story of Gotham's most notorious villain.");
                movieRepository.save(m7);
            }

            if (cinemaHallRepository.count() == 0) {
                CinemaHall hall = new CinemaHall();
                hall.setName("Main Hall");
                hall.setTotalRows(5);
                hall.setSeatsPerRow(8);
                cinemaHallRepository.save(hall);
            }

            if (screeningRepository.count() == 0) {
                CinemaHall hall = cinemaHallRepository.findAll().get(0);
                String[] rows = {"A", "B", "C", "D", "E"};

                for (Movie movie : movieRepository.findAll()) {
                    Screening screening = new Screening();
                    screening.setMovie(movie);
                    screening.setCinemaHall(hall);
                    screening.setStartTime(LocalDateTime.now().plusDays(1));
                    screening.setBasePrice(10.0);
                    screeningRepository.save(screening);

                    for (String row : rows) {
                        for (int i = 1; i <= hall.getSeatsPerRow(); i++) {
                            Seat seat = new Seat();
                            seat.setRowLabel(row);
                            seat.setSeatNumber(i);
                            seat.setStatus(SeatStatus.AVAILABLE);
                            seat.setScreening(screening);
                            seatRepository.save(seat);
                        }
                    }
                }
            }

            if (userRepository.count() == 0) {
                Customer customer = new Customer();
                customer.setFirstName("Demo");
                customer.setLastName("Customer");
                customer.setEmail("demo@example.com");
                customer.setPassword(passwordEncoder.encode("password"));
                customer.setRole(com.example.cinema.model.UserRole.CUSTOMER);
                userRepository.save(customer);
            }
        };
    }
}
