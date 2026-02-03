package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.PortfolioSnapshot;

import java.util.List;
import java.util.Optional;

public interface SnapshotService {
    List<PortfolioSnapshot> findAll();
    Optional<PortfolioSnapshot> findById(Long id);
    PortfolioSnapshot save(PortfolioSnapshot snapshot);
}

