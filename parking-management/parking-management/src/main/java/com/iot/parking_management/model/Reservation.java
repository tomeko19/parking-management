package com.iot.parking_management.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long parkingSpot;

    // Costruttore senza argomenti
    public Reservation() {
    }

    public Reservation(long id, Long parkingSpot, String reservedBy, String startTimeStr, long userId) {
        this.id = id;
        this.parkingSpot = parkingSpot;

        // Converti la stringa di data/ora in LocalDateTime
        this.startTime = LocalDateTime.parse(startTimeStr);

        // Per il campo endTime, se hai bisogno di un valore, puoi impostarlo qui
        this.endTime = this.startTime.plusHours(1); // Esempio: aggiungi 1 ora

        // Creare un oggetto User usando userId, supponendo che User ha un costruttore che accetta un id
        this.user = new User(); // Questo è un esempio. Potresti dover recuperare un'istanza di User dal tuo repository
        this.user.setId(userId);

        // Se hai bisogno di impostare altri dettagli su User, fallo qui
        // Esempio: this.user.setUsername(reservedBy); se necessario
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(Long parkingSpot) {
        this.parkingSpot = parkingSpot;
    }




}
