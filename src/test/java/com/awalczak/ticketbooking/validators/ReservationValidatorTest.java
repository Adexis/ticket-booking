package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Reservation;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;

import static com.awalczak.ticketbooking.validators.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ReservationValidatorTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private SeatValidator seatValidator;

    @Mock
    private UserValidator userValidator;

    @Mock
    private ScreeningValidator screeningValidator;

    @InjectMocks
    private ReservationValidator reservationValidator;

    public static Object[] provideValidReservations() {
        return new Object[]{
                new Object[]{Reservation.builder().screening(VALID_SCREENING).seats(VALID_SEAT_LIST).users(VALID_USER_LIST).price(VALID_DOUBLE_POSITIVE).build()},
                new Object[]{Reservation.builder().screening(VALID_SCREENING).seats(Collections.singletonList(VALID_SINGLE_SEAT_LEFT)).users(VALID_USER_LIST).price(VALID_DOUBLE_POSITIVE).build()},
        };
    }

    public static Object[] provideInvalidReservationsIncorrectScreening() {
        return new Object[]{
                new Object[]{Reservation.builder().build()},
                new Object[]{Reservation.builder().screening(null).seats(VALID_SEAT_LIST).users(VALID_USER_LIST).price(VALID_DOUBLE_POSITIVE).build()},
        };
    }

    public static Object[] provideInvalidReservationsIncorrectSeats() {
        return new Object[]{
                new Object[]{Reservation.builder().screening(VALID_SCREENING).seats(null).users(VALID_USER_LIST).price(VALID_DOUBLE_POSITIVE).build()},
        };
    }

    public static Object[] provideInvalidReservationsSeatIsTaken() {
        return new Object[]{
                new Object[]{Reservation.builder().screening(VALID_SCREENING).seats(Collections.singletonList(VALID_TAKEN_SEAT_1)).users(VALID_USER_LIST).price(VALID_DOUBLE_POSITIVE).build()},
        };
    }

    public static Object[] provideInvalidReservationsSingleSeatLeftBetweenReservations() {
        return new Object[]{
                new Object[]{Reservation.builder().screening(VALID_SCREENING).seats(Collections.singletonList(INVALID_SINGLE_SEAT_LEFT)).users(VALID_USER_LIST).price(VALID_DOUBLE_POSITIVE).build()},
        };
    }

    public static Object[] provideInvalidReservationsIncorrectUsers() {
        return new Object[]{
                new Object[]{Reservation.builder().screening(VALID_SCREENING).seats(VALID_SEAT_LIST).users(null).price(VALID_DOUBLE_POSITIVE).build()},
        };
    }

    public static Object[] provideInvalidReservationsIncorrectPrice() {
        return new Object[]{
                new Object[]{Reservation.builder().screening(VALID_SCREENING).seats(VALID_SEAT_LIST).users(VALID_USER_LIST).price(null).build()},
                new Object[]{Reservation.builder().screening(VALID_SCREENING).seats(VALID_SEAT_LIST).users(VALID_USER_LIST).price(INVALID_DOUBLE_NEGATIVE).build()},
        };
    }

    @Test
    @Parameters(method = "provideValidReservations")
    public void reservationIsValid(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(seatValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(true).when(userValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(true).when(screeningValidator).isEntityValid(Mockito.any());
        Mockito.doReturn(false).when(seatValidator).isAnyOfSeatsTaken(Mockito.anyList(), Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isSingleSeatLeftBetweenReservations(Mockito.anyList(), Mockito.anyList());
        Reservation reservation = (Reservation) baseEntity;
        assertThat(reservationValidator.isEntityValid(reservation)).isTrue();
    }

    @Test
    @Parameters(method = "provideInvalidReservationsIncorrectScreening")
    public void reservationIsInvalidIncorrectScreening(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(seatValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(true).when(userValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isAnyOfSeatsTaken(Mockito.anyList(), Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isSingleSeatLeftBetweenReservations(Mockito.anyList(), Mockito.anyList());
        Reservation reservation = (Reservation) baseEntity;
        assertThat(reservationValidator.isEntityValid(reservation)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidReservationsIncorrectSeats")
    public void reservationIsInvalidIncorrectSeats(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(screeningValidator).isEntityValid(Mockito.any());
        Mockito.doReturn(true).when(userValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doCallRealMethod().when(seatValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isAnyOfSeatsTaken(Mockito.anyList(), Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isSingleSeatLeftBetweenReservations(Mockito.anyList(), Mockito.anyList());
        Reservation reservation = (Reservation) baseEntity;
        assertThat(reservationValidator.isEntityValid(reservation)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidReservationsIncorrectUsers")
    public void reservationIsInvalidIncorrectUsers(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(screeningValidator).isEntityValid(Mockito.any());
        Mockito.doReturn(true).when(seatValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doCallRealMethod().when(userValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isAnyOfSeatsTaken(Mockito.anyList(), Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isSingleSeatLeftBetweenReservations(Mockito.anyList(), Mockito.anyList());
        Reservation reservation = (Reservation) baseEntity;
        assertThat(reservationValidator.isEntityValid(reservation)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidReservationsIncorrectPrice")
    public void reservationIsInvalidIncorrectPrice(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(screeningValidator).isEntityValid(Mockito.any());
        Mockito.doReturn(true).when(seatValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(true).when(userValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isAnyOfSeatsTaken(Mockito.anyList(), Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isSingleSeatLeftBetweenReservations(Mockito.anyList(), Mockito.anyList());
        Reservation reservation = (Reservation) baseEntity;
        assertThat(reservationValidator.isEntityValid(reservation)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidReservationsSeatIsTaken")
    public void reservationIsInvalidSeatIsTaken(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(screeningValidator).isEntityValid(Mockito.any());
        Mockito.doReturn(true).when(seatValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(true).when(userValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isSingleSeatLeftBetweenReservations(Mockito.anyList(), Mockito.anyList());
        Mockito.doCallRealMethod().when(seatValidator).isAnyOfSeatsTaken(Mockito.anyList(), Mockito.anyList());
        Reservation reservation = (Reservation) baseEntity;
        assertThat(reservationValidator.isEntityValid(reservation)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidReservationsSingleSeatLeftBetweenReservations")
    public void reservationIsInvalidSingleSeatLeftBetweenReservations(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(screeningValidator).isEntityValid(Mockito.any());
        Mockito.doReturn(true).when(seatValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(true).when(userValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(false).when(seatValidator).isAnyOfSeatsTaken(Mockito.anyList(), Mockito.anyList());
        Reservation reservation = (Reservation) baseEntity;
        Mockito.doCallRealMethod().when(seatValidator).isSingleSeatLeftBetweenReservations(Mockito.anyList(), Mockito.anyList());
        assertThat(reservationValidator.isEntityValid(reservation)).isFalse();
    }
}