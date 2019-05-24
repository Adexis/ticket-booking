package com.awalczak.ticketbooking.services;

import com.awalczak.ticketbooking.dto.AbstractDto;
import com.awalczak.ticketbooking.dto.RoomDto;
import com.awalczak.ticketbooking.entities.Cinema;
import com.awalczak.ticketbooking.entities.Room;
import com.awalczak.ticketbooking.repositories.CinemaRepository;
import com.awalczak.ticketbooking.repositories.RoomRepository;
import com.awalczak.ticketbooking.validators.RoomValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoomService extends AbstractService {

    private static final String LOG_INFO_ROOM_DOES_NOT_EXIST = "Screening does not exist";
    private static final String LOG_INFO_ROOM_IS_NULL = "Screening is null. Therefore screening does not exist";
    private static final String LOG_INFO_ROOM_CINEMA_DOES_NOT_EXIST = "Cannot create room, the cinema does not exist.";

    private final RoomRepository roomRepository;
    private final CinemaRepository cinemaRepository;
    private final RoomValidator roomValidator;

    @Autowired
    public RoomService(RoomRepository roomRepository, RoomValidator roomValidator, RoomRepository roomRepository1, CinemaRepository cinemaRepository, RoomValidator roomValidator1) {
        super(roomRepository, roomValidator);
        this.roomRepository = roomRepository1;
        this.cinemaRepository = cinemaRepository;
        this.roomValidator = roomValidator1;
    }

    public RoomDto createEntity(AbstractDto abstractDto) {
        RoomDto roomDto = (RoomDto) abstractDto;
        if (roomDto != null) {
            Room room = roomDto.toEntity();
            if (roomDto.getCinema() != null && roomValidator.isEntityValid(room)) {
                addRoomToExistingCinema(roomDto);
                return room.toDto();
            } else {
                if (roomValidator.isEntityValid(room)) {
                    roomRepository.save(room);
                    log.info(LOG_INFO_CREATE_SUCCESS + room.toString());
                    return room.toDto();
                } else {
                    log.error(LOG_INFO_CREATE_FAILURE_VALIDATION + roomDto.toString());
                }
            }
        } else {
            log.error(LOG_INFO_CREATE_FAILURE_DTO_IS_NULL);
        }
        return null;
    }

    public boolean roomExist(Room room) {
        boolean result = false;
        if (room != null) {
            List<Room> rooms = roomRepository.findByNumber(room.getNumber());
            if (!rooms.isEmpty()) {
                result = true;
            } else {
                log.error(LOG_INFO_ROOM_DOES_NOT_EXIST);
            }
        } else {
            log.error(LOG_INFO_ROOM_IS_NULL);
        }
        return result;
    }

    private void addRoomToExistingCinema(RoomDto roomDto) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(roomDto.getCinema());
        if (cinemaOptional.isPresent()) {
            Cinema cinema = cinemaOptional.get();
            Room room = roomDto.toEntity();
            List<Room> rooms = cinema.getRooms();
            rooms.add(room);
            cinema.setRooms(rooms);
            cinemaRepository.save(cinema);
            log.info(LOG_INFO_CREATE_SUCCESS + room.toString());
        } else {
            log.error(LOG_INFO_ROOM_CINEMA_DOES_NOT_EXIST);
        }
    }
}
