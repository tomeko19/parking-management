package com.iot.parking_management.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    @Enumerated(EnumType.STRING)
    private Role roles;

    public void setRoles(Role roles) {
        this.roles = roles;
    }
    public Role getRoles(){
        return roles;
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
enum Role {
    BASE, PREMIUM, ADMIN
}
