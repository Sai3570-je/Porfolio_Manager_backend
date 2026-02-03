package com.example.Portfolio_Manager.service;

import com.example.Portfolio_Manager.beans.Transaction;
import com.example.Portfolio_Manager.beans.TransactionType;
import com.example.Portfolio_Manager.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByAsset(Long assetId) {
        return transactionRepository.findByAssetId(assetId);
    }

    @Override
    public List<Transaction> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByTransactionType(type);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDateTime from, LocalDateTime to) {
        return transactionRepository.findByTimestampBetween(from, to);
    }
}
