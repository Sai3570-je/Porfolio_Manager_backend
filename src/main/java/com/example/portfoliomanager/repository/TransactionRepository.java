package com.example.portfoliomanager.repository;

import com.example.portfoliomanager.beans.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
