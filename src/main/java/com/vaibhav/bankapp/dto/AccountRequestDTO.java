package com.vaibhav.bankapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AccountRequestDTO {
  @NotBlank(message = "Name is required")
  private String name;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Email is required")
  private String email;

  @Positive(message = "Balance must be positive")
  private double balance;
}
//this class is that what the client send details to create account 
// id and created_at cannot touch by client