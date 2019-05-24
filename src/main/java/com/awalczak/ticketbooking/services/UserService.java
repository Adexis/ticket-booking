package com.awalczak.ticketbooking.services;

import com.awalczak.ticketbooking.repositories.UserRepository;
import com.awalczak.ticketbooking.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService extends AbstractService {

    @Autowired
    public UserService(UserRepository userRepository, UserValidator userValidator) {
        super(userRepository, userValidator);
    }
}
