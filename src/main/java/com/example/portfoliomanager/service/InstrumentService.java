package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Instrument;

import java.util.List;
import java.util.Optional;

public interface InstrumentService {
    List<Instrument> findAll();
    Optional<Instrument> findById(Long id);
    Optional<Instrument> findBySymbol(String symbol);
    Instrument save(Instrument instrument);
    void delete(Long id);
}

