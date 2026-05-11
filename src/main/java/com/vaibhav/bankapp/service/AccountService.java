package com.vaibhav.bankapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaibhav.bankapp.dto.AccountRequestDTO;
import com.vaibhav.bankapp.dto.AccountResponseDTO;
import com.vaibhav.bankapp.entity.Account;
import com.vaibhav.bankapp.repository.AccountRepository;
//import com.vaibhav.bankapp.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    // private final TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    // AccountService(TransactionService transactionService) {
    // this.transactionService = transactionService;
    // }
    private AccountResponseDTO toResponseDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setId(account.getId());
        dto.setName(account.getName());
        dto.setEmail(account.getEmail());
        dto.setBalance(account.getBalance());
        dto.setCreated_at(account.getCreatedAt());
        return dto;
    }

    // creating Account
    public AccountResponseDTO createAccount(AccountRequestDTO request) {
        Account account = new Account();
        account.setName(request.getName());
        account.setEmail(request.getEmail());
        account.setBalance(request.getBalance());

        Account saved = accountRepository.save(account);
        return toResponseDTO(saved);
    }

    // find accound by id
    public Account getAccount(Integer id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public AccountResponseDTO getAccountById(Integer id) {
        return toResponseDTO(getAccount(id));

    }

    // get all accounts
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // deposite
    public AccountResponseDTO deposit(Integer id, double amount) {
        Account account = getAccount(id);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        transactionService.saveTransaction(account, "CREDIT", amount);
        return toResponseDTO(account);
        // return accountRepository.save(account);
    }

    public AccountResponseDTO withdraw(Integer id, double amount) {
        Account account = getAccount(id);
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
        transactionService.saveTransaction(account, "DEBIT", amount);
        return toResponseDTO(account);
        // return accountRepository.save(acc
        // ount);
    }

    @Transactional
    public String transfer(Integer fromId, Integer toId, double amount) {
        Account from = getAccount(fromId);
        Account to = getAccount(toId);

        if (from.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        accountRepository.save(from);
        accountRepository.save(to);
        transactionService.saveTransaction(from, "DEBIT", amount);
        transactionService.saveTransaction(to, "CREDIT", amount);
        return "Transfer successful";
    }

    public AccountResponseDTO updateEmail(Integer id, String email) {
        Account account = getAccount(id);
        account.setEmail(email);
        return toResponseDTO(accountRepository.save(account));
    }

    public AccountResponseDTO updateName(Integer id, String name) {
        Account account = getAccount(id);
        account.setName(name);
        return toResponseDTO(accountRepository.save(account));

    }

    public AccountResponseDTO updateAccount(Integer id, String name, String email) {
        Account account = getAccount(id);
        if (name != null)
            account.setName(name);
        if (email != null)
            account.setEmail(email);
        return toResponseDTO(accountRepository.save(account));
    }

    public double getBalance(Integer id) {
        return getAccount(id).getBalance();
    }

    public void deleteAccount(Integer id) {
        getAccount(id);
        accountRepository.deleteById(id);
    }

}
