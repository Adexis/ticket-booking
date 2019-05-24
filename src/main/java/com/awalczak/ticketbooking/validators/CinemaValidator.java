package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Cinema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CinemaValidator extends AbstractValidator {

    private final RoomValidator roomValidator;
    private final ScreeningValidator screeningValidator;

    @Autowired
    public CinemaValidator(RoomValidator roomValidator, ScreeningValidator screeningValidator) {
        this.roomValidator = roomValidator;
        this.screeningValidator = screeningValidator;
    }

    public boolean isEntityValid(BaseEntity baseEntity) {
        Cinema cinema = (Cinema) baseEntity;
        boolean result = false;
        if (cinema != null) {
            log.info("Validation of cinema " + cinema.toString() + " in progress");
            result = this.containsRequiredFields(cinema.getName(), cinema.getRooms(), cinema.getScreenings())
                    && isNameValid(cinema.getName())
                    && roomValidator.isListOfEntitiesValid(cinema.getRooms())
                    && screeningValidator.isListOfEntitiesValid(cinema.getScreenings());
        } else {
            log.error("Received null cinema object during validation");
        }
        return result;
    }

    private boolean isNameValid(String name) {
        boolean result = false;
        if (name != null) {
            result = !name.isEmpty() && Character.isUpperCase(name.charAt(0));
        } else {
            log.error("Name cannot be null");
        }
        if (!result) {
            log.error("Cinema name " + name + " is not is valid");
        }
        return result;
    }
}
