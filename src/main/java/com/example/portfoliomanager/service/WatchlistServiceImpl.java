package com.example.portfoliomanager.service;


import com.example.portfoliomanager.beans.WatchlistItem;
import com.example.portfoliomanager.repository.WatchlistItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WatchlistServiceImpl implements WatchlistService {
    private final WatchlistItemRepository repo;

    @Autowired
    public WatchlistServiceImpl(WatchlistItemRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<WatchlistItem> findAll() { return repo.findAll(); }

    @Override
    public Optional<WatchlistItem> findByInstrumentId(Long instrumentId) { return repo.findByInstrumentId(instrumentId); }

    @Override
    @Transactional
    public WatchlistItem add(WatchlistItem item) {
        if (item.getAddedAt() == null) item.setAddedAt(OffsetDateTime.now());
        return repo.save(item);
    }

    @Override
    @Transactional
    public void delete(Long id) { repo.deleteById(id); }

    @Override
    @Transactional
    public void deleteByInstrumentId(Long instrumentId) { repo.deleteByInstrumentId(instrumentId); }
}