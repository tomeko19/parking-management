package com.iot.parking_management.controller;

import com.iot.parking_management.model.ChargingRequest;
import com.iot.parking_management.service.ChargingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charging-requests")
public class ChargingRequestController {

    private final ChargingRequestService chargingRequestService;

    @Autowired
    public ChargingRequestController(ChargingRequestService chargingRequestService) {
        this.chargingRequestService = chargingRequestService;
    }

    @GetMapping
    public ResponseEntity<List<ChargingRequest>> getAllChargingRequests() {
        return ResponseEntity.ok(chargingRequestService.getAllChargingRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingRequest> getChargingRequestById(@PathVariable Long id) {
        return chargingRequestService.getChargingRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ChargingRequest> createChargingRequest(@RequestBody ChargingRequest chargingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chargingRequestService.createChargingRequest(chargingRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargingRequest> updateChargingRequest(@PathVariable Long id, @RequestBody ChargingRequest chargingRequest) {
        ChargingRequest updatedRequest = chargingRequestService.updateChargingRequest(id, chargingRequest);
        if (updatedRequest != null) {
            return ResponseEntity.ok(updatedRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChargingRequest(@PathVariable Long id) {
        chargingRequestService.deleteChargingRequest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChargingRequest>> getChargingRequestsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(chargingRequestService.getChargingRequestsByUser(userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ChargingRequest>> getChargingRequestsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(chargingRequestService.getChargingRequestsByStatus(status));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ChargingRequest> updateChargingRequestStatus(@PathVariable Long id, @RequestBody String newStatus) {
        ChargingRequest updatedRequest = chargingRequestService.updateChargingRequestStatus(id, newStatus);
        if (updatedRequest != null) {
            return ResponseEntity.ok(updatedRequest);
        }
        return ResponseEntity.notFound().build();
    }
}