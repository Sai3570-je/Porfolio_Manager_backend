// java
package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Position;

import java.util.List;
import java.util.Optional;

public interface PositionService {
    Double getTotalPortfolioValue();
    Double getTotalInvestment();
    Double getTotalGainLoss();
    Long getHoldingsCount();
    List<Position> findAll();
    Optional<Position> findById(Long id);
    Position save(Position position);
    void delete(Long id);
    List<Position> findByInstrumentId(Long instrumentId);
}
