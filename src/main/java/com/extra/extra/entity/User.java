package com.extra.extra.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users") 
@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}