package com.example.portfoliomanager.beans;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "positions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;

    private Double quantity;
    private Double avgPurchasePrice;
    private Double currentPrice;
    private OffsetDateTime purchaseDate;

    @Column(length = 2000)
    private String notes;
}
