package com.vaibhav.bankapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vaibhav.bankapp.dto.AccountRequestDTO;
import com.vaibhav.bankapp.dto.AccountResponseDTO;
import com.vaibhav.bankapp.dto.TransactionResponseDTO;
import com.vaibhav.bankapp.service.AccountService;
import com.vaibhav.bankapp.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable Integer id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponseDTO> deposit(@PathVariable Integer id, @RequestParam double amount) {
        return ResponseEntity.ok(accountService.deposit(id, amount));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponseDTO> withdraw(@PathVariable Integer id, @RequestParam double amount) {
        return ResponseEntity.ok(accountService.withdraw(id, amount));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Integer fromId,
                                           @RequestParam Integer toId,
                                           @RequestParam double amount) {
        return ResponseEntity.ok(accountService.transfer(fromId, toId, amount));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(@PathVariable Integer id) {
        return ResponseEntity.ok(transactionService.geTransactionsByAccount(id));
    }

    @GetMapping("/{id}/transactions/filter")
    public ResponseEntity<List<TransactionResponseDTO>> getByType(@PathVariable Integer id,
                                                                   @RequestParam String type) {
        return ResponseEntity.ok(transactionService.getTransactionsByType(id, type));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Integer id) {
        return ResponseEntity.ok(accountService.getBalance(id));
    }

    @PutMapping("/{id}/update-email")
    public ResponseEntity<AccountResponseDTO> updateEmail(@PathVariable Integer id,
                                                          @RequestParam String email) {
        return ResponseEntity.ok(accountService.updateEmail(id, email));
    }

    @PutMapping("/{id}/update-name")
    public ResponseEntity<AccountResponseDTO> updateName(@PathVariable Integer id,
                                                         @RequestParam String name) {
        return ResponseEntity.ok(accountService.updateName(id, name));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Integer id,
                                                            @RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(accountService.updateAccount(id, name, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
