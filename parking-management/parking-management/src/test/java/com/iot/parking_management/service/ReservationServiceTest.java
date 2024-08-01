package com.iot.parking_management.service;

import com.iot.parking_management.model.Reservation;
import com.iot.parking_management.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private MqttService mqttService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReservation_ShouldSaveReservation_WhenParkingSpotIsAvailable() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setParkingSpot(1L);
        reservation.setStartTime(LocalDateTime.now());
        reservation.setEndTime(LocalDateTime.now().plusHours(1));

        given(reservationRepository.findByParkingSpotIdAndEndTimeAfter(anyLong(), any(LocalDateTime.class)))
                .willReturn(Arrays.asList());

        given(reservationRepository.save(any(Reservation.class))).willReturn(reservation);

        Reservation createdReservation = reservationService.createReservation(reservation);

        assertNotNull(createdReservation);
        assertEquals(reservation.getId(), createdReservation.getId());
    }

    @Test
    void createReservation_ShouldThrowException_WhenParkingSpotIsNotAvailable() {
        Reservation reservation = new Reservation();
        reservation.setParkingSpot(1L);
        reservation.setStartTime(LocalDateTime.now());
        reservation.setEndTime(LocalDateTime.now().plusHours(1));

        Reservation overlappingReservation = new Reservation();
        overlappingReservation.setParkingSpot(1L);
        overlappingReservation.setStartTime(LocalDateTime.now());
        overlappingReservation.setEndTime(LocalDateTime.now().plusHours(2));

        given(reservationRepository.findByParkingSpotIdAndEndTimeAfter(anyLong(), any(LocalDateTime.class)))
                .willReturn(Arrays.asList(overlappingReservation));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(reservation);
        });

        assertEquals("Parking spot not available for the selected time.", exception.getMessage());
    }

    @Test
    void updateReservation_ShouldUpdateReservation_WhenExists() throws Exception {
        Reservation existingReservation = new Reservation();
        existingReservation.setId(1L);
        existingReservation.setParkingSpot(1L);
        existingReservation.setStartTime(LocalDateTime.now());
        existingReservation.setEndTime(LocalDateTime.now().plusHours(1));

        Reservation updatedDetails = new Reservation();
        updatedDetails.setStartTime(LocalDateTime.now().plusHours(2));
        updatedDetails.setEndTime(LocalDateTime.now().plusHours(3));
        updatedDetails.setParkingSpot(2L);

        given(reservationRepository.findById(anyLong())).willReturn(Optional.of(existingReservation));
        given(reservationRepository.save(any(Reservation.class))).willReturn(existingReservation);

        Reservation updatedReservation = reservationService.updateReservation(1L, updatedDetails);

        assertNotNull(updatedReservation);
        assertEquals(updatedDetails.getStartTime(), updatedReservation.getStartTime());
        assertEquals(updatedDetails.getEndTime(), updatedReservation.getEndTime());
        assertEquals(updatedDetails.getParkingSpot(), updatedReservation.getParkingSpot());
    }

    @Test
    void updateReservation_ShouldThrowException_WhenNotExists() {
        Reservation updatedDetails = new Reservation();
        updatedDetails.setStartTime(LocalDateTime.now().plusHours(2));
        updatedDetails.setEndTime(LocalDateTime.now().plusHours(3));

        given(reservationRepository.findById(anyLong())).willReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.updateReservation(1L, updatedDetails);
        });

        assertEquals("Reservation not found", exception.getMessage());
    }

    @Test
    void getReservationById_ShouldReturnReservation_WhenExists() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);

        given(reservationRepository.findById(anyLong())).willReturn(Optional.of(reservation));

        Optional<Reservation> foundReservation = reservationService.getReservationById(1L);

        assertTrue(foundReservation.isPresent());
        assertEquals(reservation.getId(), foundReservation.get().getId());
    }

    @Test
    void getReservationById_ShouldReturnEmpty_WhenNotExists() {
        given(reservationRepository.findById(anyLong())).willReturn(Optional.empty());

        Optional<Reservation> foundReservation = reservationService.getReservationById(1L);

        assertFalse(foundReservation.isPresent());
    }

    @Test
    void deleteReservation_ShouldCallDeleteOnRepository() {
        Long reservationId = 1L;

        reservationService.deleteReservation(reservationId);

        // Verify that deleteById was called with the correct id
        Mockito.verify(reservationRepository).deleteById(reservationId);
    }

    @Test
    void getReservationsByUserId_ShouldReturnListOfReservations() {
        Long userId = 1L;
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);

        given(reservationRepository.findByUserId(userId)).willReturn(Arrays.asList(reservation1, reservation2));

        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);

        assertEquals(2, reservations.size());
        assertEquals(reservation1.getId(), reservations.get(0).getId());
        assertEquals(reservation2.getId(), reservations.get(1).getId());
    }
}
