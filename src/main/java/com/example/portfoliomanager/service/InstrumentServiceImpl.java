package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Instrument;
import com.example.portfoliomanager.exception.BadRequestException;
import com.example.portfoliomanager.exception.ConflictException;
import com.example.portfoliomanager.exception.NotFoundException;
import com.example.portfoliomanager.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
        if (instrument == null) {
            throw new BadRequestException("Instrument must not be null");
        }
        if (instrument.getSymbol() != null && repo.existsBySymbol(instrument.getSymbol())) {
            throw new ConflictException("Instrument with symbol already exists: " + instrument.getSymbol());
        }
        try {
            return repo.save(instrument);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Conflict saving instrument: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid instrument: " + ex.getMessage());
        }
    }

    @Override
    public List<Instrument> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Instrument> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Instrument> findBySymbol(String symbol) {
        return repo.findBySymbol(symbol);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Instrument not found with id: " + id);
        }
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Cannot delete instrument due to database constraints");
        }
    }
}