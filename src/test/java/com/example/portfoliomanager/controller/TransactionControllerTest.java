package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.Transaction;
import com.example.portfoliomanager.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    // ---------- TEST DATA ----------
    private Transaction transaction() {
        Transaction t = new Transaction();
        t.setId(1L);
        t.setType(Transaction.Type.DEBIT);
        t.setQuantity(10.0);
        t.setPrice(150.0);
        return t;
    }

   

    // ---------- GET BY ID ----------
    @Test
    void getById_WhenFound_ShouldReturnTransaction() throws Exception {
        Mockito.when(transactionService.findById(1L))
                .thenReturn(Optional.of(transaction()));

        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void getById_WhenNotFound_ShouldReturn404() throws Exception {
        Mockito.when(transactionService.findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/transactions/99"))
                .andExpect(status().isNotFound());
    }

    // ---------- CREATE ----------
    @Test
    void create_ShouldReturnCreatedTransaction() throws Exception {
        Mockito.when(transactionService.save(Mockito.any(Transaction.class)))
                .thenReturn(transaction());

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction())))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/transactions/1"))
                .andExpect(jsonPath("$.id").value(1L));
    }
}
