package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Position;
import com.example.portfoliomanager.repository.PositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionServiceImpl implements PositionService {
    private final PositionRepository repo;

    @Autowired
    public PositionServiceImpl(PositionRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Position> findAll() { return repo.findAll(); }

    @Override
    public Optional<Position> findById(Long id) { return repo.findById(id); }

    @Override
    @Transactional
    public Position save(Position position) { return repo.save(position); }

    @Override
    @Transactional
    public void delete(Long id) { repo.deleteById(id); }

    @Override
    public List<Position> findByInstrumentId(Long instrumentId) { return repo.findAllByInstrumentId(instrumentId); }
}

