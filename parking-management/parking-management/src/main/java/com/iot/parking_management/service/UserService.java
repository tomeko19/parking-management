package com.iot.parking_management.service;

import com.iot.parking_management.model.User;
import com.iot.parking_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User saveUser(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){

        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByEmail(String email){
        return  userRepository.findByEmail(email);
    }
}
