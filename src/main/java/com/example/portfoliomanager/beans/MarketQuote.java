package com.example.portfoliomanager.beans;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "market_quotes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;

    private Double price;
    private Double bid;
    private Double ask;
    private Double changePercent;
    private String volume;

    private OffsetDateTime timestamp;
}

