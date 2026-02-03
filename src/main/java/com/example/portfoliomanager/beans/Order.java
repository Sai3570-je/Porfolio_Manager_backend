package com.example.portfoliomanager.beans;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;

    @Enumerated(EnumType.STRING)
    private Side side; // BUY/SELL

    @Enumerated(EnumType.STRING)
    private OrderType orderType; // MARKET/LIMIT

    private Double quantity;
    private Double limitPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PENDING/EXECUTED/CANCELLED

    private OffsetDateTime placedAt;
    private OffsetDateTime executedAt;

    public enum Side { BUY, SELL }
    public enum OrderType { MARKET, LIMIT }
    public enum OrderStatus { PENDING, EXECUTED, CANCELLED }
}

