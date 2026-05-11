package com.vaibhav.bankapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaibhav.bankapp.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

     List<Transaction> findByAccountId(Integer accountId);

     List<Transaction> findByAccountIdAndType(Integer id , String type);

}
