package com.example.portfoliomanager.beans;

import java.time.OffsetDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "portfolio_snapshots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime timestamp;

    private Double totalValue;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String jsonPositions;
}
