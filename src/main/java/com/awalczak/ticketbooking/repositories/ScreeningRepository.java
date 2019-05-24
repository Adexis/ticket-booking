package com.awalczak.ticketbooking.repositories;

import com.awalczak.ticketbooking.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findByDateBetweenOrderByMovieTitleAscDateAsc(LocalDateTime from, LocalDateTime to);

    List<Screening> findByDateAndRoomNumber(LocalDateTime date, Long number);
}
