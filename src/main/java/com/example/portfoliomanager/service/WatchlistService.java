package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.WatchlistItem;

import java.util.List;
import java.util.Optional;

public interface WatchlistService {
    List<WatchlistItem> findAll();
    Optional<WatchlistItem> findByInstrumentId(Long instrumentId);
    WatchlistItem add(WatchlistItem item);
    void delete(Long id);
    void deleteByInstrumentId(Long instrumentId);
}
