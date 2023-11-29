package com.siva.Spring.model;

import lombok.Data;

@Data
public class SignupRequest {
    String username;
    String email;
    String password;
}
