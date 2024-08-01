package com.iot.parking_management.service;

import com.iot.parking_management.model.Reservation;
import com.iot.parking_management.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private MqttService mqttService;

    public Reservation createReservation(Reservation reservation) {
        // Logic to check if the parking spot is available
        // e.g., check if there are no overlapping reservations
        List<Reservation> overlappingReservations = reservationRepository
                .findByParkingSpotIdAndEndTimeAfter(reservation.getParkingSpot(), reservation.getStartTime());

        if (overlappingReservations.isEmpty()) {
            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Parking spot not available for the selected time.");
        }
    }
    public Reservation saveReservation(Reservation reservation) throws Exception {

        Reservation savedReservation =  reservationRepository.save(reservation);
        mqttService.publish("parking/reservation", "New reservation created for user: "+reservation.getUser().getUsername());
        return savedReservation;
    }

    public List<Reservation> getReservationsByUserId(Long userId){

        return reservationRepository.findByUserId(userId);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (((Optional<?>) optionalReservation).isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStartTime(reservationDetails.getStartTime());
            reservation.setEndTime(reservationDetails.getEndTime());
            reservation.setParkingSpot(reservationDetails.getParkingSpot());
            reservation.setUser(reservationDetails.getUser());
            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }
}
