package com.example.portfoliomanager.repository;

import com.example.portfoliomanager.beans.PortfolioSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioSnapshotRepository extends JpaRepository<PortfolioSnapshot, Long> {
}

