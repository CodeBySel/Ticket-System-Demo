package com.example.cinema.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("STAFF")
@NoArgsConstructor
public class Staff extends User {
    public Staff(Long id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password, UserRole.STAFF);
    }
}
