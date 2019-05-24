package com.awalczak.ticketbooking.services;

import com.awalczak.ticketbooking.repositories.MovieRepository;
import com.awalczak.ticketbooking.validators.MovieValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieService extends AbstractService {

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieValidator movieValidator) {
        super(movieRepository, movieValidator);
    }
}
