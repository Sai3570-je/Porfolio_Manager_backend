package com.example.portfoliomanager.repository;

import com.example.portfoliomanager.beans.MarketQuote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketQuoteRepository extends JpaRepository<MarketQuote, Long> {
    List<MarketQuote> findAllByInstrumentIdOrderByTimestampDesc(Long instrumentId);
}
