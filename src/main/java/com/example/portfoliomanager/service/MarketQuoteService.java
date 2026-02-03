package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.MarketQuote;

import java.util.List;
import java.util.Optional;

public interface MarketQuoteService {
    List<MarketQuote> findAll();
    Optional<MarketQuote> findById(Long id);
    MarketQuote save(MarketQuote quote);
    List<MarketQuote> findByInstrumentId(Long instrumentId);
}

