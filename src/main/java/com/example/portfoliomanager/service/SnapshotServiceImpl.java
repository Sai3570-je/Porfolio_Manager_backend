package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.PortfolioSnapshot;
import com.example.portfoliomanager.repository.PortfolioSnapshotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SnapshotServiceImpl implements SnapshotService {
    private final PortfolioSnapshotRepository repo;

    @Autowired
    public SnapshotServiceImpl(PortfolioSnapshotRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<PortfolioSnapshot> findAll() { return repo.findAll(); }

    @Override
    public Optional<PortfolioSnapshot> findById(Long id) { return repo.findById(id); }

    @Override
    @Transactional
    public PortfolioSnapshot save(PortfolioSnapshot snapshot) {
        if (snapshot.getTimestamp() == null) snapshot.setTimestamp(OffsetDateTime.now());
        return repo.save(snapshot);
    }
}
