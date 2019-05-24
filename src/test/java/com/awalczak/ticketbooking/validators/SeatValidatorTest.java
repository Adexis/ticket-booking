package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Seat;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static com.awalczak.ticketbooking.validators.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class SeatValidatorTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    SeatValidator seatValidator;

    public static Object[] provideValidSeats() {
        return new Object[]{
                new Object[]{Seat.builder().number(VALID_LONG_POSITIVE).line(VALID_STRING_ONE_CHAR).build()}
        };
    }

    public static Object[] provideInvalidSeatsIncorrectNumber() {
        return new Object[]{
                new Object[]{Seat.builder().build()},
                new Object[]{Seat.builder().number(null).line(VALID_STRING_ONE_CHAR).build()},
                new Object[]{Seat.builder().number(INVALID_LONG_NEGATIVE).line(VALID_STRING_ONE_CHAR).build()},
        };
    }

    public static Object[] provideInvalidSeatsIncorrectLine() {
        return new Object[]{
                new Object[]{Seat.builder().number(VALID_LONG_POSITIVE).line(null).build()},
                new Object[]{Seat.builder().number(VALID_LONG_POSITIVE).line(INVALID_STRING_EMPTY).build()},
                new Object[]{Seat.builder().number(VALID_LONG_POSITIVE).line(INVALID_STRING_STARTS_WITH_LOWER_CASE).build()},
        };
    }

    @Test
    @Parameters(method = "provideValidSeats")
    public void seatIsValid(BaseEntity baseEntity) {
        Seat seat = (Seat) baseEntity;
        assertThat(seatValidator.isEntityValid(seat)).isTrue();
    }

    @Test
    @Parameters(method = "provideInvalidSeatsIncorrectNumber")
    public void seatIsInvalidIncorrectNumber(BaseEntity baseEntity) {
        Seat seat = (Seat) baseEntity;
        assertThat(seatValidator.isEntityValid(seat)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidSeatsIncorrectNumber")
    public void seatIsInvalidIncorrectLine(BaseEntity baseEntity) {
        Seat seat = (Seat) baseEntity;
        assertThat(seatValidator.isEntityValid(seat)).isFalse();
    }
}