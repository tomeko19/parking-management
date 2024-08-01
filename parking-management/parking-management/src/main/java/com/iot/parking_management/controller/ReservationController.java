package com.iot.parking_management.controller;

import com.iot.parking_management.model.Reservation;
import com.iot.parking_management.service.MqttService;
import com.iot.parking_management.service.ReservationService;
import com.iot.parking_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;

    @Autowired
    private MqttService mqttService;

    // end poi per creare una prenotazione
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation newReservation = reservationService.createReservation(reservation);
            return ResponseEntity.ok(newReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails) throws Exception {
        Reservation updatedReservation = reservationService.updateReservation(id, reservationDetails);
        mqttService.publish("parking/updates", "Reservation updated: " + updatedReservation.getId());
        return ResponseEntity.ok(updatedReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    private void sendMqttNotification(String topic, String message){
        try{
            mqttService.publish(topic,message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
