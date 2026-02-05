 package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Position;
import com.example.portfoliomanager.exception.BadRequestException;
import com.example.portfoliomanager.exception.ConflictException;
import com.example.portfoliomanager.exception.NotFoundException;
import com.example.portfoliomanager.repository.PositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public Double getTotalInvestment() {
        try {
            return repo.calculateTotalInvestment();
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Error calculating total investment: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid arguments calculating total investment: " + ex.getMessage());
        }
    }

    @Override
    public Long getHoldingsCount() {
        try {
            return repo.countActiveHoldings();
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Error counting holdings: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid arguments counting holdings: " + ex.getMessage());
        }
    }

    @Override
    public Double getTotalPortfolioValue() {
        try {
            return repo.calculateTotalPortfolioValue();
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Error calculating total portfolio value: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid arguments calculating portfolio value: " + ex.getMessage());
        }
    }
    @Override
    public Double getTotalGainLoss() {
        try {
            Double portfolioValue = repo.calculateTotalPortfolioValue();
            Double investment = repo.calculateTotalInvestment();
            return portfolioValue - investment;
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Error calculating gain/loss: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid arguments calculating gain/loss: " + ex.getMessage());
        }
    }
    @Override
    public List<Position> findAll() {
        try {
            return repo.findAll();
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Error retrieving positions: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid arguments retrieving positions: " + ex.getMessage());
        }
    }

    @Override
    public Optional<Position> findById(Long id) {
        try {
            return repo.findById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Error retrieving position by id: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid id: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Position save(Position position) {
        if (position == null) {
            throw new BadRequestException("Position must not be null");
        }
        try {
            return repo.save(position);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Conflict saving position: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid position: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Position not found with id: " + id);
        }
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Cannot delete position due to database constraints");
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid id: " + ex.getMessage());
        }
    }

    @Override
    public List<Position> findByInstrumentId(Long instrumentId) {
        try {
            return repo.findAllByInstrumentId(instrumentId);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Error retrieving positions by instrument id: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid instrument id: " + ex.getMessage());
        }
    }
}
