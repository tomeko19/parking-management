package com.iot.parking_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.parking_management.model.User;
import com.iot.parking_management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void registerUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void getCurrentUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaim("preferred_username")).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/me")
                        .principal((Principal) jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService, times(1)).findByUsername("testuser");
    }

    @Test
    void getCurrentUserUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserByUsername() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        when(userService.findByUsername("testuser")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService, times(1)).findByUsername("testuser");
    }

    @Test
    void getUserByUsernameNotFound() throws Exception {
        when(userService.findByUsername("nonexistent")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/nonexistent"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findByUsername("nonexistent");
    }
}