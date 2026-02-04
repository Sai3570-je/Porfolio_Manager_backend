package com.example.portfoliomanager.service;

import com.example.portfoliomanager.repository.PositionRepository;
import com.example.portfoliomanager.service.PositionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private PositionServiceImpl positionService;

    // ---------------- HOLDINGS COUNT ----------------

    @Test
    void getHoldingsCount_shouldReturnCount() {
        when(positionRepository.countActiveHoldings()).thenReturn(4L);

        Long result = positionService.getHoldingsCount();

        assertEquals(4L, result);
    }

    // ---------------- TOTAL INVESTMENT ----------------

    @Test
    void getTotalInvestment_shouldReturnInvestment() {
        when(positionRepository.calculateTotalInvestment()).thenReturn(50000.0);

        Double result = positionService.getTotalInvestment();

        assertEquals(50000.0, result);
    }

    // ---------------- TOTAL PORTFOLIO VALUE ----------------

    @Test
    void getTotalPortfolioValue_shouldReturnPortfolioValue() {
        when(positionRepository.calculateTotalPortfolioValue()).thenReturn(62000.0);

        Double result = positionService.getTotalPortfolioValue();

        assertEquals(62000.0, result);
    }

    // ---------------- TOTAL GAIN / LOSS ----------------

    @Test
    void getTotalGainLoss_shouldReturnPositiveGain() {
        when(positionRepository.calculateTotalPortfolioValue()).thenReturn(62000.0);
        when(positionRepository.calculateTotalInvestment()).thenReturn(50000.0);

        Double result = positionService.getTotalGainLoss();

        assertEquals(12000.0, result);
    }

    @Test
    void getTotalGainLoss_shouldReturnLoss() {
        when(positionRepository.calculateTotalPortfolioValue()).thenReturn(45000.0);
        when(positionRepository.calculateTotalInvestment()).thenReturn(50000.0);

        Double result = positionService.getTotalGainLoss();

        assertEquals(-5000.0, result);
    }

    // ---------------- GAIN / LOSS PERCENTAGE ----------------



    @Test
    void getTotalGainLossPercentage_whenInvestmentIsZero_shouldReturnZero() {
        when(positionRepository.calculateTotalInvestment()).thenReturn(0.0);

        Double results = positionService.getTotalGainLoss();

        assertEquals(0.0, results);
    }
}
