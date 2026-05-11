package com.vaibhav.bankapp.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(Integer id)
    {
        super("Account not found");
    }


}
