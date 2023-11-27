package com.siva.Spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    String username;
    String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
}
