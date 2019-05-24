package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.User;
import com.awalczak.ticketbooking.enums.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.awalczak.ticketbooking.validators.Constants.REGEX_ANY_LOWER_CASE_CHARACTER_WITH_POLISH;
import static com.awalczak.ticketbooking.validators.Constants.REGEX_ANY_UPPER_CASE_CHARACTER_WITH_POLISH;

@Component
@Slf4j
public class UserValidator extends AbstractValidator {

    public boolean isEntityValid(BaseEntity entity) {
        User user = (User) entity;
        boolean result = false;
        if (user != null) {
            log.info("Validation of user " + user.toString() + " in progress");
            result = this.containsRequiredFields(user.getName(), user.getSurname(), user.getType())
                    && validateName(user.getName())
                    && validateSurname(user.getSurname())
                    && validateType(user.getType());
        } else {
            log.error("Received null user object during validation");
        }
        return result;
    }

    private boolean validateName(String name) {
        boolean result = false;
        if (name != null) {
            result = (!name.isEmpty() && name.matches
                    ("[" + REGEX_ANY_UPPER_CASE_CHARACTER_WITH_POLISH + "]" +
                            "[" + REGEX_ANY_LOWER_CASE_CHARACTER_WITH_POLISH + "]{2,}"));
        }
        if (!result) {
            log.error("Name " + name + " is not is valid");
        }
        return result;
    }

    private boolean validateSurname(String surname) {
        boolean result = false;
        if (surname != null) {
            if (!surname.isEmpty() && surname.length() > 2) {
                if (surname.contains("-")) {
                    result = surname.matches
                            ("[" + REGEX_ANY_UPPER_CASE_CHARACTER_WITH_POLISH + "]" +
                                    "[" + REGEX_ANY_LOWER_CASE_CHARACTER_WITH_POLISH + "]{2,}" +
                                    "-" +
                                    "[" + REGEX_ANY_UPPER_CASE_CHARACTER_WITH_POLISH + "]" +
                                    "[" + REGEX_ANY_LOWER_CASE_CHARACTER_WITH_POLISH + "]{2,}");
                } else {
                    result = surname.matches
                            ("[" + REGEX_ANY_UPPER_CASE_CHARACTER_WITH_POLISH + "]" +
                                    "[" + REGEX_ANY_LOWER_CASE_CHARACTER_WITH_POLISH + "]{2,}");
                }
            }
        } else {
            log.error("Surname cannot be null");
        }
        if (!result) {
            log.error("Surname " + surname + " is not is valid");
        }
        return result;
    }

    private boolean validateType(Type type) {
        boolean result = false;
        if (type != null) {
            for (Type value : Type.values()) {
                if (value.equals(type)) {
                    result = true;
                }
            }
        } else {
            log.error("Type cannot be null");
        }
        return result;
    }
}
