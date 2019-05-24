package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Room;
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

import static com.awalczak.ticketbooking.validators.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class RoomValidatorTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private SeatValidator seatValidator;

    @InjectMocks
    private RoomValidator roomValidator;

    public static Object[] provideValidRooms() {
        return new Object[]{
                new Object[]{Room.builder().seats(VALID_SEAT_LIST).number(VALID_LONG_POSITIVE).build()},
        };
    }

    public static Object[] provideInvalidRoomsIncorrectSeats() {
        return new Object[]{
                new Object[]{Room.builder().build()},
                new Object[]{Room.builder().seats(null).number(VALID_LONG_POSITIVE).build()},
                new Object[]{Room.builder().seats(INVALID_SEAT_LIST_EMPTY).number(VALID_LONG_POSITIVE).build()},
        };
    }

    public static Object[] provideInvalidRoomsIncorrectNumber() {
        return new Object[]{
                new Object[]{Room.builder().seats(VALID_SEAT_LIST).number(null).screenings(VALID_SCREENING_LIST).build()},
                new Object[]{Room.builder().seats(VALID_SEAT_LIST).number(INVALID_LONG_NEGATIVE).build()},
        };
    }

    @Test
    @Parameters(method = "provideValidRooms")
    public void roomIsValid(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(seatValidator).isListOfEntitiesValid(Mockito.anyList());
        Room room = (Room) baseEntity;
        assertThat(roomValidator.isEntityValid(room)).isTrue();
    }

    @Test
    @Parameters(method = "provideInvalidRoomsIncorrectSeats")
    public void roomIsInvalidIncorrectSeats(BaseEntity baseEntity) {
        Room room = (Room) baseEntity;
        assertThat(roomValidator.isEntityValid(room)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidRoomsIncorrectNumber")
    public void roomIsInvalidIncorrectNumber(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(seatValidator).isListOfEntitiesValid(Mockito.anyList());
        Room room = (Room) baseEntity;
        assertThat(roomValidator.isEntityValid(room)).isFalse();
    }
}