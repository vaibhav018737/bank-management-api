package com.vaibhav.bankapp.dto;

import java.time.LocalDateTime;
import lombok.Data;


@Data
public class AccountResponseDTO {

    private Integer id;
    private String name;
    private String email;
    private double balance;
    private LocalDateTime created_at;

}
// this class is reponsible for what client get or receives back
