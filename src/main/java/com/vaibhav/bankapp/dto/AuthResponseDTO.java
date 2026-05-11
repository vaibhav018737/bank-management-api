package com.vaibhav.bankapp.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String email;

    public AuthResponseDTO(String token, String email) {
        this.token = token;
        this.email = email;
    }
}