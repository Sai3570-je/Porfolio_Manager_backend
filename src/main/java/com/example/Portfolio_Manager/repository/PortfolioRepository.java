package com.example.Portfolio_Manager.repository;

import com.example.Portfolio_Manager.beans.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    // Additional queries can be added if needed
}
