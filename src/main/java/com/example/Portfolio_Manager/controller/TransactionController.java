package com.example.Portfolio_Manager.controller;

import com.example.Portfolio_Manager.beans.Transaction;
import com.example.Portfolio_Manager.beans.TransactionType;
import com.example.Portfolio_Manager.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // 1️⃣ Get all transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // 2️⃣ Get transactions by asset
    @GetMapping("/asset/{assetId}")
    public List<Transaction> getByAsset(@PathVariable Long assetId) {
        return transactionService.getTransactionsByAsset(assetId);
    }

    // 3️⃣ Get transactions by type (BUY / SELL)
    @GetMapping("/type/{type}")
    public List<Transaction> getByType(@PathVariable TransactionType type) {
        return transactionService.getTransactionsByType(type);
    }

    // 4️⃣ Get transactions by date range
    @GetMapping("/date-range")
    public List<Transaction> getByDateRange(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ) {
        return transactionService.getTransactionsByDateRange(from, to);
    }
}
