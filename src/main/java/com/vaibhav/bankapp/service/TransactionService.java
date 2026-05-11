package com.vaibhav.bankapp.service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaibhav.bankapp.dto.TransactionResponseDTO;
import com.vaibhav.bankapp.entity.Account;
import com.vaibhav.bankapp.entity.Transaction;
import com.vaibhav.bankapp.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    public TransactionRepository transactionRepository;

    private TransactionResponseDTO toResponseDTO(Transaction transaction){
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(transaction.getId());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());
        dto.setAccountId(transaction.getAccount().getId());
        return dto;

    }

    public Transaction saveTransaction(Account account,String typr,double amount){
        Transaction transaction=new Transaction();
        transaction.setAccount(account);
        transaction.setType(typr);
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
        
    }

    public List<TransactionResponseDTO> geTransactionsByAccount(Integer accountId){
        return transactionRepository.findByAccountId(accountId)
               .stream()
               .map(this::toResponseDTO)
               .collect(Collectors.toList());
    }



    public TransactionResponseDTO getTransaction(Integer id)
    {
       return toResponseDTO(transactionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transaction not found")));
    }

    public List<TransactionResponseDTO> getAllTransactions(){
        return transactionRepository.findAll()
        .stream()
        .map(this::toResponseDTO)
        .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> getTransactionsByType(Integer accoundId , String type)
    {
        return transactionRepository.findByAccountIdAndType(accoundId,type)
        .stream()
        .map(this::toResponseDTO)
        .collect(Collectors.toList());
    }
    


}
