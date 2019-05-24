package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Screening;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.awalczak.ticketbooking.validators.Constants.MINIMUM_MINUTES_BEFORE_SCREENING;

@Component
@Slf4j
public class ScreeningValidator extends AbstractValidator {

    private final MovieValidator movieValidator;
    private final RoomValidator roomValidator;

    @Autowired
    public ScreeningValidator(MovieValidator movieValidator, RoomValidator roomValidator, SeatValidator seatValidator) {
        this.movieValidator = movieValidator;
        this.roomValidator = roomValidator;
    }

    public boolean isEntityValid(BaseEntity entity) {
        Screening screening = (Screening) entity;
        boolean result = false;
        if (screening != null) {
            log.info("Validation of screening " + screening.toString() + " in progress");
            result = this.containsRequiredFields(screening.getDate(), screening.getMovie())
                    && isDateValid(screening.getDate())
                    && movieValidator.isEntityValid(screening.getMovie())
                    && roomValidator.isEntityValid(screening.getRoom());
        } else {
            log.error("Received null screening object during validation");
        }
        return result;
    }

    private boolean isDateValid(LocalDateTime date) {
        boolean result = false;
        if (date != null) {
            result = date.isAfter(LocalDateTime.now().plusMinutes(MINIMUM_MINUTES_BEFORE_SCREENING));
        } else {
            log.error("Screening date cannot be null");
        }
        if (!result) {
            log.error("Minimum time before creating screening is equal to " + MINIMUM_MINUTES_BEFORE_SCREENING + " minutes");
        }
        return result;
    }
}
