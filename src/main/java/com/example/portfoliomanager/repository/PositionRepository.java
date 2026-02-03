package com.example.portfoliomanager.repository;

import com.example.portfoliomanager.beans.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findAllByInstrumentId(Long instrumentId);
    @Query("SELECT COUNT(p) FROM Position p WHERE p.quantity > 0")
    Long countActiveHoldings();

    // 2️⃣ Total Investment = SUM(quantity × avgPurchasePrice)
    @Query("""
        SELECT COALESCE(SUM(p.quantity * p.avgPurchasePrice), 0)
        FROM Position p
        WHERE p.quantity > 0
    """)
    Double calculateTotalInvestment();

    // 3️⃣ Total Portfolio Value = SUM(quantity × currentPrice)
    @Query("""
        SELECT COALESCE(SUM(p.quantity * p.currentPrice), 0)
        FROM Position p
        WHERE p.quantity > 0
    """)
    Double calculateTotalPortfolioValue();
}
