package com.iot.parking_management.repository;

import com.iot.parking_management.model.ChargingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargingRequestRepository extends JpaRepository<ChargingRequest, Long> {

    /**
     * Trova tutte le richieste di ricarica per un determinato utente.
     * @param userId L'ID dell'utente
     * @return Una lista di richieste di ricarica
     */
    List<ChargingRequest> findByUserId(Long userId);

    /**
     * Trova tutte le richieste di ricarica con un determinato stato.
     * @param status Lo stato della richiesta di ricarica
     * @return Una lista di richieste di ricarica
     */
    List<ChargingRequest> findByStatus(String status);

    /**
     * Trova tutte le richieste di ricarica per un determinato posto auto.
     * @param parkingSpotId L'ID del posto auto
     * @return Una lista di richieste di ricarica
     */
    List<ChargingRequest> findByParkingSpotId(Long parkingSpotId);

    /**
     * Trova tutte le richieste di ricarica con una percentuale di carica desiderata maggiore di un valore specificato.
     * @param percentage La percentuale minima di carica desiderata
     * @return Una lista di richieste di ricarica
     */
    List<ChargingRequest> findByDesiredChargePercentageGreaterThan(int percentage);

    /**
     * Trova tutte le richieste di ricarica per un determinato utente e stato.
     * @param userId L'ID dell'utente
     * @param status Lo stato della richiesta di ricarica
     * @return Una lista di richieste di ricarica
     */
    List<ChargingRequest> findByUserIdAndStatus(Long userId, String status);

    /**
     * Conta il numero di richieste di ricarica per un determinato stato.
     * @param status Lo stato della richiesta di ricarica
     * @return Il numero di richieste di ricarica
     */
    long countByStatus(String status);
}