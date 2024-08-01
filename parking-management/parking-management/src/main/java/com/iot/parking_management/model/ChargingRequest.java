package com.iot.parking_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ChargingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private ParkingSpot parkingSpot;
    private int desiredChargePercentage;
    private String status;
}
