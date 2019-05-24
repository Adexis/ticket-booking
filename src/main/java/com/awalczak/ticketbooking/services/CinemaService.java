package com.awalczak.ticketbooking.services;

import com.awalczak.ticketbooking.repositories.CinemaRepository;
import com.awalczak.ticketbooking.validators.CinemaValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CinemaService extends AbstractService {

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository,
                         CinemaValidator cinemaValidator) {
        super(cinemaRepository, cinemaValidator);
    }
}
