package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.Instrument;
import com.example.portfoliomanager.beans.Position;
import com.example.portfoliomanager.dto.PositionRequest;
import com.example.portfoliomanager.repository.InstrumentRepository;
import com.example.portfoliomanager.service.PositionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PositionController.class)
@AutoConfigureMockMvc(addFilters = false)
class PositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PositionService positionService;

    @MockBean
    private InstrumentRepository instrumentRepository;

    // ---------- TEST DATA ----------
    private Instrument instrument() {
        Instrument i = new Instrument();
        i.setId(1L);
        i.setSymbol("AAPL");
        i.setName("Apple Inc");
        return i;
    }

    private Position position() {
        Position p = new Position();
        p.setId(1L);
        p.setInstrument(instrument());
        p.setQuantity(10.0);
        p.setAvgPurchasePrice(150.0);
        p.setCurrentPrice(170.0);
        p.setPurchaseDate(
                OffsetDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
        );
        p.setNotes("Long term");
        return p;
    }

    private PositionRequest request() {
        PositionRequest r = new PositionRequest();
        r.setInstrumentId(1L);
        r.setQuantity(10.0);
        r.setAvgPurchasePrice(150.0);
        r.setCurrentPrice(170.0);
        r.setPurchaseDate(
                OffsetDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
        );
        r.setNotes("Long term");
        return r;
    }

    // ---------- METRICS ----------
    @Test
    void getHoldingsCount_ShouldReturnValue() throws Exception {
        Mockito.when(positionService.getHoldingsCount()).thenReturn(2L);

        mockMvc.perform(get("/api/positions/holdings-count"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void getTotalPortfolioValue_ShouldReturnValue() throws Exception {
        Mockito.when(positionService.getTotalPortfolioValue()).thenReturn(25000.0);

        mockMvc.perform(get("/api/positions/total-portfolio-value"))
                .andExpect(status().isOk())
                .andExpect(content().string("25000.0"));
    }

    @Test
    void getTotalInvestment_ShouldReturnValue() throws Exception {
        Mockito.when(positionService.getTotalInvestment()).thenReturn(20000.0);

        mockMvc.perform(get("/api/positions/total-investment"))
                .andExpect(status().isOk())
                .andExpect(content().string("20000.0"));
    }

    @Test
    void getTotalGainLoss_ShouldReturnValue() throws Exception {
        Mockito.when(positionService.getTotalGainLoss()).thenReturn(5000.0);

        mockMvc.perform(get("/api/positions/total-gain-loss"))
                .andExpect(status().isOk())
                .andExpect(content().string("5000.0"));
    }

    // ---------- GET ALL ----------
    @Test
    void list_ShouldReturnAllPositions() throws Exception {
        Mockito.when(positionService.findAll()).thenReturn(List.of(position()));

        mockMvc.perform(get("/api/positions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(10));
    }

    // ---------- GET BY ID ----------
    @Test
    void getById_WhenFound_ShouldReturnPosition() throws Exception {
        Mockito.when(positionService.findById(1L)).thenReturn(Optional.of(position()));

        mockMvc.perform(get("/api/positions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrument.symbol").value("AAPL"));
    }

    @Test
    void getById_WhenNotFound_ShouldReturn404() throws Exception {
        Mockito.when(positionService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/positions/99"))
                .andExpect(status().isNotFound());
    }

    // ---------- CREATE ----------
    @Test
    void create_WhenValid_ShouldReturn201() throws Exception {
        Mockito.when(instrumentRepository.findById(1L)).thenReturn(Optional.of(instrument()));
        Mockito.when(positionService.save(Mockito.any(Position.class))).thenReturn(position());

        mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void create_WhenInstrumentIdMissing_ShouldReturn400() throws Exception {
        PositionRequest r = new PositionRequest();

        mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isBadRequest());
    }

    // ---------- UPDATE ----------
    @Test
    void update_WhenFound_ShouldReturn200() throws Exception {
        Mockito.when(positionService.findById(1L)).thenReturn(Optional.of(position()));
        Mockito.when(positionService.save(Mockito.any(Position.class))).thenReturn(position());
        Mockito.when(instrumentRepository.findById(1L)).thenReturn(Optional.of(instrument()));

        mockMvc.perform(put("/api/positions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isOk());
    }

    @Test
    void update_WhenNotFound_ShouldReturn404() throws Exception {
        Mockito.when(positionService.findById(1L))
                .thenThrow(new com.example.portfoliomanager.exception.NotFoundException("Position not found with id: 1"));

        mockMvc.perform(put("/api/positions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isNotFound());
    }


    // ---------- DELETE ----------
    @Test
    void delete_ShouldReturn204() throws Exception {
        Mockito.doNothing().when(positionService).delete(1L);

        mockMvc.perform(delete("/api/positions/1"))
                .andExpect(status().isNoContent());
    }
}