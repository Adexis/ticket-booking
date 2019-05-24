package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.*;
import com.awalczak.ticketbooking.enums.Type;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.awalczak.ticketbooking.validators.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class UserValidatorTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    private UserValidator userValidator;

    public static Object[] provideValidUsers() {
        return new Object[]{
                new Object[]{User.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).surname(VALID_STRING_STARTS_WITH_UPPER_CASE).type(Type.ADULT).build()},
                new Object[]{User.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE_POLISH_LETTERS).surname(VALID_STRING_STARTS_WITH_UPPER_CASE).type(Type.ADULT).build()},
                new Object[]{User.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).surname(VALID_STRING_STARTS_WITH_UPPER_CASE_POLISH_LETTERS).type(Type.ADULT).build()},
                new Object[]{User.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE_POLISH_LETTERS).surname(VALID_STRING_STARTS_WITH_UPPER_CASE_POLISH_LETTERS + "-" + VALID_STRING_STARTS_WITH_UPPER_CASE_POLISH_LETTERS).type(Type.ADULT).build()},
        };
    }

    public static Object[] provideInvalidUsersIncorrectName() {
        return new Object[]{
                new Object[]{User.builder().build()},
                new Object[]{User.builder().name(null).surname(VALID_STRING_STARTS_WITH_UPPER_CASE).type(Type.ADULT).build()},
                new Object[]{User.builder().name(INVALID_STRING_EMPTY).surname(VALID_STRING_STARTS_WITH_UPPER_CASE).type(Type.ADULT).build()},
                new Object[]{User.builder().name(INVALID_STRING_STARTS_WITH_LOWER_CASE).surname(VALID_STRING_STARTS_WITH_UPPER_CASE).type(Type.ADULT).build()},
                new Object[]{User.builder().name(INVALID_STRING_TOO_SHORT).surname(VALID_STRING_STARTS_WITH_UPPER_CASE).type(Type.ADULT).build()},
        };
    }

    public static Object[] provideInvalidUsersIncorrectSurname() {
        return new Object[]{
                new Object[]{User.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).surname(null).type(Type.ADULT).build()},
                new Object[]{User.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).surname(INVALID_STRING_EMPTY).type(Type.ADULT).build()},
                new Object[]{User.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).surname(INVALID_STRING_STARTS_WITH_LOWER_CASE).type(Type.ADULT).build()},
                new Object[]{User.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).surname(INVALID_STRING_TOO_SHORT).type(Type.ADULT).build()},
        };
    }

    public static Object[] provideInvalidUsersIncorrectType() {
        return new Object[]{
                new Object[]{User.builder().name(VALID_STRING_STARTS_WITH_UPPER_CASE).surname(INVALID_STRING_TOO_SHORT).type(null).build()},
        };
    }

    @Test
    @Parameters(method = "provideValidUsers")
    public void userIsValid(BaseEntity baseEntity) {
        User user = (User) baseEntity;
        assertThat(userValidator.isEntityValid(user)).isTrue();
    }

    @Test
    @Parameters(method = "provideInvalidUsersIncorrectName")
    public void userIsInvalidIncorrectName(BaseEntity baseEntity) {
        User user = (User) baseEntity;
        assertThat(userValidator.isEntityValid(user)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidUsersIncorrectSurname")
    public void userIsInvalidIncorrectSurname(BaseEntity baseEntity) {
        User user = (User) baseEntity;
        assertThat(userValidator.isEntityValid(user)).isFalse();
    }

    @Test
    @Parameters(method = "provideInvalidUsersIncorrectType")
    public void userIsInvalidIncorrectType(BaseEntity baseEntity) {
        User user = (User) baseEntity;
        assertThat(userValidator.isEntityValid(user)).isFalse();
    }
}