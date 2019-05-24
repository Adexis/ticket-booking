package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Room;
import com.awalczak.ticketbooking.entities.Seat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RoomValidator extends AbstractValidator {

    private final SeatValidator seatValidator;

    public RoomValidator(SeatValidator seatValidator) {
        this.seatValidator = seatValidator;
    }

    public boolean isEntityValid(BaseEntity entity) {
        Room room = (Room) entity;
        boolean result = false;
        if (room != null) {
            log.error("Validation of room " + room.toString() + " in progress");
            result = this.containsRequiredFields(room.getSeats(), room.getNumber())
                    && isRoomNumberValid(room.getNumber())
                    && isListOfSeatsValid(room.getSeats())
                    && seatValidator.isListOfEntitiesValid(room.getSeats());
        } else {
            log.error("Received null room object during validation");
        }
        return result;
    }

    private boolean isRoomNumberValid(Long number) {
        boolean result = false;
        if (number != null) {
            result = number > 0;
        } else {
            log.error("Room number cannot be null");
        }
        if (!result) {
            log.error("Number of the room must be greater than zero");
        }
        return result;
    }

    private boolean isListOfSeatsValid(List<Seat> seats) {
        boolean result = false;
        if (seats != null) {
            result = !seats.isEmpty();
        } else {
            log.error("List of rooms cannot be null");
        }
        if (!result) {
            log.error("List of rooms cannot be empty");
        }
        return result;
    }
}
