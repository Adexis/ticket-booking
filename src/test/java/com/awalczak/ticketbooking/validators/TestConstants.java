package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestConstants {

    static final String VALID_STRING_STARTS_WITH_UPPER_CASE_POLISH_LETTERS = "Żółć";
    static String VALID_STRING_STARTS_WITH_UPPER_CASE = "Abc";
    static String VALID_STRING_ONE_CHAR = "A";
    static String INVALID_STRING_TOO_SHORT = "Ab";
    static String INVALID_STRING_EMPTY = "";
    static String INVALID_STRING_STARTS_WITH_LOWER_CASE = "abc";
    static Seat VALID_TAKEN_SEAT_1 = Seat.builder()
            .line("A")
            .number(2L)
            .taken(true)
            .build();
    static Seat INVALID_SINGLE_SEAT_LEFT = Seat.builder()
            .line("A")
            .number(4L)
            .taken(true)
            .build();
    static Seat VALID_SINGLE_SEAT_LEFT = Seat.builder()
            .line("B")
            .number(4L)
            .taken(true)
            .build();
    static Long VALID_LONG_POSITIVE = 1L;
    static Long INVALID_LONG_NEGATIVE = -1L;
    static Double VALID_DOUBLE_POSITIVE = 15D;
    static Double INVALID_DOUBLE_NEGATIVE = -1D;
    static LocalDateTime VALID_DATE = LocalDateTime.now().plusHours(1L);
    static LocalDateTime INVALID_DATE = LocalDateTime.now().minusHours(1L);
    static Movie VALID_MOVIE = Movie.builder().
            title(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .build();
    static List<Screening> INVALID_SCREENING_LIST_EMPTY = new ArrayList<>();
    static List<Seat> INVALID_SEAT_LIST_EMPTY = new ArrayList<>();
    static List<Room> INVALID_ROOM_LIST_EMPTY = new ArrayList<>();
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
    static List<Seat> VALID_SEAT_LIST = Arrays.asList(VALID_FREE_SEAT_1, VALID_FREE_SEAT_2);
    private static Room VALID_ROOM_1 = Room.builder()
            .number(1L)
            .seats(Arrays.asList(
                    VALID_TAKEN_SEAT_1,
                    VALID_TAKEN_SEAT_2,
                    VALID_FREE_SEAT_1,
                    VALID_FREE_SEAT_2))
            .build();
    static List<Room> VALID_ROOM_LIST = Collections.singletonList(VALID_ROOM_1);
    static Screening VALID_SCREENING = Screening.builder()
            .date(VALID_DATE)
            .movie(VALID_MOVIE)
            .room(VALID_ROOM_1)
            .build();
    static List<Screening> VALID_SCREENING_LIST = Collections.singletonList(VALID_SCREENING);
    public static Cinema VALID_CINEMA = Cinema.builder()
            .name(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .rooms(VALID_ROOM_LIST)
            .screenings(VALID_SCREENING_LIST)
            .build();
    private static User VALID_USER_1 = User.builder()
            .name(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .surname(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .build();
    private static User VALID_USER_2 = User.builder()
            .name(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .surname(VALID_STRING_STARTS_WITH_UPPER_CASE)
            .build();
    static List<User> VALID_USER_LIST = Arrays.asList(VALID_USER_1, VALID_USER_2);
}
