package com.example.Portfolio_Manager.repository;

import com.example.Portfolio_Manager.beans.Transaction;
import com.example.Portfolio_Manager.beans.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAssetId(Long assetId);

    List<Transaction> findByTransactionType(TransactionType transactionType);

    List<Transaction> findByTimestampBetween(LocalDateTime from, LocalDateTime to);
}
