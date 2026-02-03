package com.example.portfoliomanager.repository;

import com.example.portfoliomanager.beans.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findAllByInstrumentId(Long instrumentId);
    @Query("SELECT COUNT(p) FROM Position p WHERE p.quantity > 0")
    Long countActiveHoldings();

    @Query("""
    SELECT COALESCE(SUM(p.quantity * mq.price), 0)
    FROM Position p
    JOIN MarketQuote mq ON p.asset.symbol = mq.symbol
    WHERE p.quantity > 0
""")
    Double calculateTotalPortfolioValue();
    @Query("""
    SELECT COALESCE(SUM(p.quantity * p.avgPurchasePrice), 0)
    FROM Position p
    WHERE p.quantity > 0
""")
    Double calculateTotalInvestment();
}
