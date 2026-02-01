package com.example.Portfolio_Manager.beans;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset_id", nullable = false)
    private Long assetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    private Integer quantity;

    private Double price;

    private LocalDateTime timestamp;

    // constructors
    public Transaction() {
    }

    public Transaction(Long assetId, TransactionType transactionType,
                       Integer quantity, Double price, LocalDateTime timestamp) {
        this.assetId = assetId;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    // getters & setters
    public Long getId() {
        return id;
    }

    public Long getAssetId() {
        return assetId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setQuantity
            (Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
