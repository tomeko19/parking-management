package com.iot.parking_management.repository;

import com.iot.parking_management.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot,Long> {
}
