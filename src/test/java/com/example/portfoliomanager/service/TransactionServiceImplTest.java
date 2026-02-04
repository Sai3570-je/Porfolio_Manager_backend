package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Transaction;
import com.example.portfoliomanager.repository.TransactionRepository;
import com.example.portfoliomanager.service.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    // ---------------- SAVE TRANSACTION ----------------

    @Test
    void saveTransaction_shouldReturnSavedTransaction() {
        Transaction transactions = new Transaction();
        transactions.setQuantity(10.0);

        when(transactionRepository.save(transactions)).thenReturn(transactions);

        Transaction saved = transactionService.save(transactions);

        assertEquals(10.0, saved.getQuantity());
    }

    // ---------------- GET ALL TRANSACTIONS ----------------

    @Test
    void getAllTransactions_shouldReturnList() {
        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();

        when(transactionRepository.findAll()).thenReturn(List.of(t1, t2));

        List<Transaction> result = transactionService.findAll();

        assertEquals(2, result.size());
    }





}
