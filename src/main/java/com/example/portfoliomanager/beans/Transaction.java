package com.example.portfoliomanager.beans;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instrument_id")
    private Instrument instrument; // nullable

    @Enumerated(EnumType.STRING)
    private Type type; // DEBIT/CREDIT

    private Double amount;
    private Double quantity;
    private Double price;
    private Double fee;
    private OffsetDateTime timestamp;

    public enum Type { DEBIT, CREDIT }
}
