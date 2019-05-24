package com.awalczak.ticketbooking.repositories;

import com.awalczak.ticketbooking.entities.Room;
import com.awalczak.ticketbooking.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByNumber(Long number);
}
