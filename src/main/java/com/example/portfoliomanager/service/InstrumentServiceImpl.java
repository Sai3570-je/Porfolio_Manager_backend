package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Instrument;
import com.example.portfoliomanager.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class InstrumentServiceImpl implements InstrumentService {
    private final InstrumentRepository repo;

    @Autowired
    public InstrumentServiceImpl(InstrumentRepository repo) {
        this.repo = repo;
    }
    @Override
    public Instrument save(Instrument instrument) {
        if (instrument.getSymbol() != null && repo.existsBySymbol(instrument.getSymbol())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Instrument with symbol already exists: " + instrument.getSymbol());
        }
        return repo.save(instrument);
    }

    @Override
    public List<Instrument> findAll() { return repo.findAll(); }

    @Override
    public Optional<Instrument> findById(Long id) { return repo.findById(id); }

    @Override
    public Optional<Instrument> findBySymbol(String symbol) { return repo.findBySymbol(symbol); }

    @Override
    public void delete(Long id) { repo.deleteById(id); }
}
