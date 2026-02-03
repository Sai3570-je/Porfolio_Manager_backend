package com.example.Portfolio_Manager.service;

import com.example.Portfolio_Manager.beans.Transaction;
import com.example.Portfolio_Manager.beans.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByAsset(Long assetId);

    List<Transaction> getTransactionsByType(TransactionType type);

    List<Transaction> getTransactionsByDateRange(LocalDateTime from, LocalDateTime to);
}
