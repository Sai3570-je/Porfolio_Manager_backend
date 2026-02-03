package com.example.portfoliomanager.repository;

import com.example.portfoliomanager.beans.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findAllByInstrumentId(Long instrumentId);
}
