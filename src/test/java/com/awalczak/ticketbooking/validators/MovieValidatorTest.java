package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Movie;
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
public class MovieValidatorTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    private MovieValidator movieValidator;

    public static Object[] provideValidMovies() {
        return new Object[]{
                new Object[]{Movie.builder().title(VALID_STRING_STARTS_WITH_UPPER_CASE).build()},
        };
    }

    public static Object[] provideInvalidMoviesWithIncorrectName() {
        return new Object[]{
                new Object[]{Movie.builder().build()},
                new Object[]{Movie.builder().title(null).build()},
                new Object[]{Movie.builder().title(INVALID_STRING_EMPTY).build()},
                new Object[]{Movie.builder().title(INVALID_STRING_STARTS_WITH_LOWER_CASE).build()},
        };
    }

    @Test
    @Parameters(method = "provideValidMovies")
    public void movieIsValid(BaseEntity baseEntity) {
        Movie movie = (Movie) baseEntity;
        assertThat(movieValidator.isEntityValid(movie)).isTrue();
    }

    @Test
    @Parameters(method = "provideInvalidMoviesWithIncorrectName")
    public void movieIsInvalid(BaseEntity baseEntity) {
        Movie movie = (Movie) baseEntity;
        assertThat(movieValidator.isEntityValid(movie)).isFalse();
    }
}