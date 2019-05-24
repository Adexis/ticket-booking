package com.awalczak.ticketbooking.controllers;

import com.awalczak.ticketbooking.entities.*;
import com.awalczak.ticketbooking.enums.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestConstants {

    private static String VALID_STRING_STARTS_WITH_UPPER_CASE = "Abc";
    private static Seat VALID_TAKEN_SEAT_1 = Seat.builder()
            .line("A")
            .number(2L)
            .taken(true)
            .build();
    private static Double VALID_DOUBLE_POSITIVE = 15D;
    private static LocalDateTime VALID_DATE = LocalDateTime.now().plusHours(1L);
    static Movie VALID_MOVIE = Movie.builder().
            title(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .build();
    private static Seat VALID_FREE_SEAT_1 = Seat.builder()
            .line("A")
            .number(3L)
            .taken(false)
            .build();
    private static Seat VALID_TAKEN_SEAT_2 = Seat.builder()
            .line("A")
            .number(5L)
            .taken(true)
            .build();
    private static Seat VALID_FREE_SEAT_2 = Seat.builder()
            .line("A")
            .number(4L)
            .taken(false)
            .build();
    private static List<Seat> VALID_SEAT_LIST = Arrays.asList(VALID_FREE_SEAT_1, VALID_FREE_SEAT_2);
    static Room VALID_ROOM_1 = Room.builder()
            .number(1L)
            .seats(Arrays.asList(
                    VALID_TAKEN_SEAT_1,
                    VALID_TAKEN_SEAT_2,
                    VALID_FREE_SEAT_1,
                    VALID_FREE_SEAT_2))
            .build();
    private static List<Room> VALID_ROOM_LIST = Collections.singletonList(VALID_ROOM_1);
    static Screening VALID_SCREENING = Screening.builder()
            .date(VALID_DATE)
            .movie(VALID_MOVIE)
            .room(VALID_ROOM_1)
            .build();
    private static List<Screening> VALID_SCREENING_LIST = Collections.singletonList(VALID_SCREENING);
    static Cinema VALID_CINEMA = Cinema.builder()
            .name(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .rooms(VALID_ROOM_LIST)
            .screenings(VALID_SCREENING_LIST)
            .build();
    static User VALID_USER_1 = User.builder()
            .name(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .surname(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .type(Type.ADULT)
            .build();
    private static User VALID_USER_2 = User.builder()
            .name(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .surname(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .type(Type.STUDENT)
            .build();
    private static List<User> VALID_USER_LIST = Arrays.asList(VALID_USER_1, VALID_USER_2);
    static Reservation VALID_RESERVATION = Reservation.builder().users(VALID_USER_LIST).seats(VALID_SEAT_LIST).screening(VALID_SCREENING).price(VALID_DOUBLE_POSITIVE).build();
}
