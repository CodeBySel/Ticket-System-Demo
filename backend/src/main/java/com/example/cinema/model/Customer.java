package com.example.cinema.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CUSTOMER")
@NoArgsConstructor
public class Customer extends User {
    public Customer(Long id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password, UserRole.CUSTOMER);
    }
}
