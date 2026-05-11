package com.vaibhav.bankapp.dto;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class TransactionResponseDTO {
    private Integer id;
    private String  type;
    private double amount;
    private LocalDateTime date;
    private Integer accountId;
}

//— instead of returning the full Account object inside Transaction, we just return accountId. 
// This avoids deeply nested JSON responses.
