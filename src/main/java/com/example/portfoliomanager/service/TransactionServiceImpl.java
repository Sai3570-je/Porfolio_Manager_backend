package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Transaction;
import com.example.portfoliomanager.exception.BadRequestException;
import com.example.portfoliomanager.exception.ConflictException;
import com.example.portfoliomanager.exception.NotFoundException;
import com.example.portfoliomanager.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public List<Transaction> findAll() {
        try {
            return repo.findAll();
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Error retrieving transactions: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid arguments retrieving transactions: " + ex.getMessage());
        }
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        try {
            return repo.findById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Error retrieving transaction by id: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid id: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        if (transaction == null) {
            throw new BadRequestException("Transaction must not be null");
        }
        try {
            return repo.save(transaction);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Conflict saving transaction: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid transaction: " + ex.getMessage());
        }
    }
}
