package com.iot.parking_management.service;

import com.iot.parking_management.model.Reservation;
import com.iot.parking_management.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void testReservationConstructorAndGetters() {
        long id = 1L;
        Long parkingSpot = 101L;
        long userId = 1L;
        String reservedBy = "testUser";
        String startTimeStr = "2023-07-30T10:00:00"; // Format ISO 8601

        Reservation reservation = new Reservation(id, parkingSpot, reservedBy, startTimeStr, userId);

        assertEquals(id, reservation.getId());
        assertEquals(parkingSpot, reservation.getParkingSpot());
        assertEquals(userId, reservation.getUser().getId());
        assertEquals(LocalDateTime.parse(startTimeStr), reservation.getStartTime());
        assertEquals(reservation.getStartTime().plusHours(1), reservation.getEndTime());
    }

    @Test
    void testSettersAndGetters() {
        Reservation reservation = new Reservation();
        User user = new User();
        user.setId(1L);

        reservation.setId(1L);
        reservation.setUser(user);
        reservation.setStartTime(LocalDateTime.now());
        reservation.setEndTime(LocalDateTime.now().plusHours(2));
        reservation.setParkingSpot(101L);

        assertEquals(1L, reservation.getId());
        assertEquals(user, reservation.getUser());
        assertNotNull(reservation.getStartTime());
        assertNotNull(reservation.getEndTime());
        assertEquals(101L, reservation.getParkingSpot());
    }
}