package com.awalczak.ticketbooking.controllers;

import com.awalczak.ticketbooking.dto.*;
import com.awalczak.ticketbooking.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.awalczak.ticketbooking.controllers.TestConstants.VALID_CINEMA;

@RestController
public class CinemaController {

    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping(value = "cinema", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    private CinemaDto createCinema(@RequestBody CinemaDto cinemaDto) {
        return (CinemaDto) cinemaService.createEntity(cinemaDto);
    }

    @GetMapping(value = "cinema/all", produces = "application/json")
    private List<CinemaDto> getAllCinemas() {
        return cinemaService.getAllEntities();
    }

    @GetMapping(value = "cinema/one/{id}")
    private CinemaDto getCinemaById(@PathVariable Long id) {
        return (CinemaDto) cinemaService.getEntityById(id);
    }

    @DeleteMapping(value = "cinema/one/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private void deleteCinemaById(@PathVariable Long id) {
        cinemaService.deleteEntityById(id);
    }

    @GetMapping(value = "cinema/sample")
    private CinemaDto createSampleCinema() {
        return VALID_CINEMA.toDto();
    }
}
