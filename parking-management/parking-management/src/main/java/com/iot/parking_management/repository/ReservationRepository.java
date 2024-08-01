package com.iot.parking_management.repository;

import com.iot.parking_management.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByParkingSpotIdAndEndTimeAfter(Long parkingSpotId, LocalDateTime endTime);
}
