package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.MarketQuote;
import com.example.portfoliomanager.repository.MarketQuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarketQuoteServiceImpl implements MarketQuoteService {
    private final MarketQuoteRepository repo;

    @Autowired
    public MarketQuoteServiceImpl(MarketQuoteRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<MarketQuote> findAll() { return repo.findAll(); }

    @Override
    public Optional<MarketQuote> findById(Long id) { return repo.findById(id); }

    @Override
    public MarketQuote save(MarketQuote quote) { return repo.save(quote); }

    @Override
    public List<MarketQuote> findByInstrumentId(Long instrumentId) { return repo.findAllByInstrumentIdOrderByTimestampDesc(instrumentId); }
}