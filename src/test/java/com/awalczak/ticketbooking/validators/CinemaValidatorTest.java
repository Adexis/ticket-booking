package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Cinema;
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
public class CinemaValidatorTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private RoomValidator roomValidator;

    @Mock
    private ScreeningValidator screeningValidator;

    @InjectMocks
    private CinemaValidator cinemaValidator;

    public static Object[] provideValidCinemas() {
        return new Object[]{
                new Object[]{Cinema.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).screenings(VALID_SCREENING_LIST).rooms(VALID_ROOM_LIST).build()},
        };
    }

    public static Object[] provideInvalidCinemasWithIncorrectName() {
        return new Object[]{
                new Object[]{Cinema.builder().build()},
                new Object[]{Cinema.builder().name(null).screenings(VALID_SCREENING_LIST).rooms(VALID_ROOM_LIST).build()},
                new Object[]{Cinema.builder().name(INVALID_STRING_EMPTY).screenings(VALID_SCREENING_LIST).rooms(VALID_ROOM_LIST).build()},
                new Object[]{Cinema.builder().name(INVALID_STRING_STARTS_WITH_LOWER_CASE).screenings(VALID_SCREENING_LIST).rooms(VALID_ROOM_LIST).build()},
        };
    }

    public static Object[] provideInvalidCinemasWithIncorrectScreenings() {
        return new Object[]{
                new Object[]{Cinema.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).screenings(null).rooms(VALID_ROOM_LIST).build()},
                new Object[]{Cinema.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).screenings(INVALID_SCREENING_LIST_EMPTY).rooms(VALID_ROOM_LIST).build()},
        };
    }

    public static Object[] provideInvalidCinemasWithIncorrectRooms() {
        return new Object[]{
                new Object[]{Cinema.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).screenings(VALID_SCREENING_LIST).rooms(null).build()},
                new Object[]{Cinema.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).screenings(VALID_SCREENING_LIST).rooms(INVALID_ROOM_LIST_EMPTY).build()},
        };
    }

    @Test
    @Parameters(method = "provideValidCinemas")
    public void cinemaIsValid(BaseEntity baseEntity) {
        setAllMockedMethodReturnValues(true);
        Cinema cinema = (Cinema) baseEntity;
        assertThat(cinemaValidator.isEntityValid(cinema)).isTrue();
    }

    @Test
    @Parameters(method = "provideInvalidCinemasWithIncorrectName")
    public void cinemaIsInvalidIncorrectName(BaseEntity baseEntity) {
        setAllMockedMethodReturnValues(true);
        Cinema cinema = (Cinema) baseEntity;
        assertThat(cinemaValidator.isEntityValid(cinema)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidCinemasWithIncorrectScreenings")
    public void cinemaIsInvalidIncorrectScreenings(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(roomValidator).isListOfEntitiesValid(Mockito.anyList());
        Cinema cinema = (Cinema) baseEntity;
        assertThat(cinemaValidator.isEntityValid(cinema)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidCinemasWithIncorrectRooms")
    public void cinemaIsInvalidIncorrectRooms(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(screeningValidator).isListOfEntitiesValid(Mockito.anyList());
        setAllMockedMethodReturnValues(false);
        Cinema cinema = (Cinema) baseEntity;
        assertThat(cinemaValidator.isEntityValid(cinema)).isFalse();
    }

    private void setAllMockedMethodReturnValues(boolean b) {
        Mockito.doReturn(b).when(screeningValidator).isListOfEntitiesValid(Mockito.anyList());
        Mockito.doReturn(b).when(roomValidator).isListOfEntitiesValid(Mockito.anyList());
    }
}