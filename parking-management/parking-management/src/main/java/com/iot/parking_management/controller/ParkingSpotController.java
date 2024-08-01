package com.iot.parking_management.controller;

import com.iot.parking_management.model.ParkingSpot;
import com.iot.parking_management.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parkingspots")
public class ParkingSpotController {
    @Autowired
   private ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<ParkingSpot> createParkingSpot(@RequestBody ParkingSpot parkingSpot){
        ParkingSpot savedParking = parkingSpotService.saveParkingSpot(parkingSpot);
        return ResponseEntity.ok(savedParking);
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpot>> getAllParkingSpots(){
        List<ParkingSpot> parkingSpots = parkingSpotService.getAllParkingSpots();
        return ResponseEntity.ok(parkingSpots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpot> getParkingSpotById(@PathVariable Long id){
        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(id);
        return ResponseEntity.ok(parkingSpot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpot(@PathVariable Long id){
        parkingSpotService.deleteParking(id);
        return  ResponseEntity.notFound().build();
    }
}
