package com.iot.parking_management.service;

import com.iot.parking_management.model.ParkingSpot;
import com.iot.parking_management.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotService {
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public ParkingSpot saveParkingSpot(ParkingSpot parkingSpot){
        return parkingSpotRepository.save(parkingSpot);
    }

    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotRepository.findAll();
    }

    public ParkingSpot getParkingSpotById(Long id){
        return parkingSpotRepository.findById(id).orElse(null);
    }

    public void deleteParking(Long id){
        parkingSpotRepository.deleteById(id);
    }
}
