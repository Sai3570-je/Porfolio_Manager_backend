package com.example.portfoliomanager.beans;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "instruments", uniqueConstraints = @UniqueConstraint(columnNames = "symbol"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol; // unique

    private String name;

    private String assetType; // STOCK/ETF/etc.

    private String exchange;

    private String sector;
}
