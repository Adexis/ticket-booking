package com.awalczak.ticketbooking.controllers;

import com.awalczak.ticketbooking.dto.RoomDto;
import com.awalczak.ticketbooking.dto.UserDto;
import com.awalczak.ticketbooking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.awalczak.ticketbooking.controllers.TestConstants.VALID_ROOM_1;
import static com.awalczak.ticketbooking.controllers.TestConstants.VALID_USER_1;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "user", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return (UserDto) userService.createEntity(userDto);
    }

    @GetMapping(value = "user/all", produces = "application/json")
    public List<UserDto> getAllUsers() {
        return userService.getAllEntities();
    }

    @GetMapping(value = "user/one/{id}", produces = "application/json")
    public UserDto getUserById(@PathVariable Long id) {
        return (UserDto) userService.getEntityById(id);
    }

    @DeleteMapping(value = "user/one/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteEntityById(id);
    }

    @GetMapping(value = "user/sample")
    private UserDto createSampleUser() {
        return VALID_USER_1.toDto();
    }
}

