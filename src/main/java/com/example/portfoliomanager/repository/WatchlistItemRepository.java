package com.example.portfoliomanager.repository;

import com.example.portfoliomanager.beans.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WatchlistItemRepository extends JpaRepository<WatchlistItem, Long> {
    Optional<WatchlistItem> findByInstrumentId(Long instrumentId);
    void deleteByInstrumentId(Long instrumentId);
}
