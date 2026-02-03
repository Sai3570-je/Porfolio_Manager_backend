package com.example.Portfolio_Manager.repository;

import com.example.Portfolio_Manager.beans.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByPortfolioId(Long portfolioId);
    Optional<Asset> findBySymbolAndPortfolioId(String symbol, Long portfolioId);
}

