package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Transaction;
import com.example.portfoliomanager.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repo;

    @Autowired
    public TransactionServiceImpl(TransactionRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Transaction> findAll() { return repo.findAll(); }

    @Override
    public Optional<Transaction> findById(Long id) { return repo.findById(id); }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) { return repo.save(transaction); }
}
