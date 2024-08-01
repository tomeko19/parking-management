package com.iot.parking_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.parking_management.model.Reservation;
import com.iot.parking_management.service.MqttService;
import com.iot.parking_management.service.ReservationService;
import com.iot.parking_management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReservationService reservationService;

    @Mock
    private UserService userService;

    @Mock
    private MqttService mqttService;

    @InjectMocks
    private ReservationController reservationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    void createReservation() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(1L);

        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/api/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(reservationService, times(1)).createReservation(any(Reservation.class));
    }

    @Test
    void updateReservation() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(1L);

        when(reservationService.updateReservation(eq(1L), any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(put("/api/reservation/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(reservationService, times(1)).updateReservation(eq(1L), any(Reservation.class));
        verify(mqttService, times(1)).publish(eq("parking/updates"), anyString());
    }

    @Test
    void getReservationById() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(1L);

        when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));

        mockMvc.perform(get("/api/reservation/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    void getUserReservations() throws Exception {
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);

        when(reservationService.getReservationsByUserId(1L)).thenReturn(Arrays.asList(reservation1, reservation2));

        mockMvc.perform(get("/api/reservation/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(reservationService, times(1)).getReservationsByUserId(1L);
    }

    @Test
    void deleteReservation() throws Exception {
        mockMvc.perform(delete("/api/reservation/1"))
                .andExpect(status().isNoContent());

        verify(reservationService, times(1)).deleteReservation(1L);
    }
}