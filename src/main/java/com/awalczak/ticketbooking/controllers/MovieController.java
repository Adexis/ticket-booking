package com.awalczak.ticketbooking.controllers;

import com.awalczak.ticketbooking.dto.MovieDto;
import com.awalczak.ticketbooking.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.awalczak.ticketbooking.controllers.TestConstants.VALID_MOVIE;

@RestController
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(value = "movie", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public MovieDto createMovie(@RequestBody MovieDto movieDto) {
        return (MovieDto) movieService.createEntity(movieDto);
    }

    @GetMapping(value = "movie/all", produces = "application/json")
    public List<MovieDto> getAllMovies() {
        return movieService.getAllEntities();
    }

    @GetMapping(value = "movie/one/{id}", produces = "application/json")
    public MovieDto getMovieById(@PathVariable Long id) {
        return (MovieDto) movieService.getEntityById(id);
    }

    @DeleteMapping(value = "movie/one/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMovieById(@PathVariable Long id) {
        movieService.deleteEntityById(id);
    }

    @GetMapping(value = "movie/sample", produces = "application/json")
    private MovieDto createSampleMovie() {
        return VALID_MOVIE.toDto();
    }
}
