package com.iot.parking_management.service;

import com.iot.parking_management.model.ChargingRequest;
import com.iot.parking_management.repository.ChargingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChargingRequestService {

    private final ChargingRequestRepository chargingRequestRepository;

    @Autowired
    public ChargingRequestService(ChargingRequestRepository chargingRequestRepository) {
        this.chargingRequestRepository = chargingRequestRepository;
    }

    public List<ChargingRequest> getAllChargingRequests() {
        return chargingRequestRepository.findAll();
    }

    public Optional<ChargingRequest> getChargingRequestById(Long id) {
        return chargingRequestRepository.findById(id);
    }

    public ChargingRequest createChargingRequest(ChargingRequest chargingRequest) {
        chargingRequest.setStatus("PENDING");
        return chargingRequestRepository.save(chargingRequest);
    }

    public ChargingRequest updateChargingRequest(Long id, ChargingRequest chargingRequest) {
        if (chargingRequestRepository.existsById(id)) {
            chargingRequest.setId(id);
            return chargingRequestRepository.save(chargingRequest);
        }
        return null;
    }

    public void deleteChargingRequest(Long id) {
        chargingRequestRepository.deleteById(id);
    }

    public List<ChargingRequest> getChargingRequestsByUser(Long userId) {
        return chargingRequestRepository.findByUserId(userId);
    }

    public List<ChargingRequest> getChargingRequestsByStatus(String status) {
        return chargingRequestRepository.findByStatus(status);
    }

    public ChargingRequest updateChargingRequestStatus(Long id, String newStatus) {
        Optional<ChargingRequest> chargingRequest = chargingRequestRepository.findById(id);
        if (chargingRequest.isPresent()) {
            ChargingRequest updatedRequest = chargingRequest.get();
            updatedRequest.setStatus(newStatus);
            return chargingRequestRepository.save(updatedRequest);
        }
        return null;
    }
}