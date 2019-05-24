package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Screening;
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
public class ScreeningValidatorTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private MovieValidator movieValidator;

    @Mock
    private RoomValidator roomValidator;

    @InjectMocks
    private ScreeningValidator screeningValidator;

    public static Object[] provideValidScreenings() {
        return new Object[]{
                new Object[]{Screening.builder().date(VALID_DATE).movie(VALID_MOVIE).build()},
        };
    }

    public static Object[] provideInvalidScreeningsIncorrectDate() {
        return new Object[]{
                new Object[]{Screening.builder().date(null).movie(VALID_MOVIE).build()},
                new Object[]{Screening.builder().date(INVALID_DATE).movie(VALID_MOVIE).build()},
        };
    }

    public static Object[] provideInvalidScreeningsIncorrectMovie() {
        return new Object[]{
                new Object[]{Screening.builder().date(VALID_DATE).movie(null).build()},
        };
    }

    @Test
    @Parameters(method = "provideValidScreenings")
    public void screeningIsValid(BaseEntity baseEntity) {
        Mockito.doReturn(true).when(movieValidator).isEntityValid(Mockito.any());
        Mockito.doReturn(true).when(roomValidator).isEntityValid(Mockito.any());
        Screening screening = (Screening) baseEntity;
        assertThat(screeningValidator.isEntityValid(screening)).isTrue();
    }

    @Test
    @Parameters(method = "provideInvalidScreeningsIncorrectDate")
    public void screeningIsInvalidIncorrectDate(BaseEntity baseEntity) {
        Screening screening = (Screening) baseEntity;
        assertThat(screeningValidator.isEntityValid(screening)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidScreeningsIncorrectMovie")
    public void screeningIsInvalidIncorrectMovie(BaseEntity baseEntity) {
        Screening screening = (Screening) baseEntity;
        assertThat(screeningValidator.isEntityValid(screening)).isFalse();
    }
}