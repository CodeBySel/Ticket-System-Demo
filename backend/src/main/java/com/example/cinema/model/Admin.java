package com.example.cinema.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ADMIN")
@NoArgsConstructor
public class Admin extends User {
    public Admin(Long id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password, UserRole.ADMIN);
    }
}
