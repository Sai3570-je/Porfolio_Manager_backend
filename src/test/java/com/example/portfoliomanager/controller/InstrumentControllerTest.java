package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.Instrument;
import com.example.portfoliomanager.service.InstrumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InstrumentController.class)
@AutoConfigureMockMvc(addFilters = false) // disables security if present
class InstrumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // REQUIRED for @WebMvcTest
    @MockBean
    private InstrumentService instrumentService;

    private Instrument sampleInstrument() {
        Instrument instrument = new Instrument();
        instrument.setId(1L);
        instrument.setSymbol("AAPL");
        instrument.setName("Apple Inc");
        return instrument;
    }

    // ---------- GET ALL ----------
    @Test
    void list_ShouldReturnAllInstruments() throws Exception {
        Mockito.when(instrumentService.findAll())
                .thenReturn(List.of(sampleInstrument()));

        mockMvc.perform(get("/api/instruments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("AAPL"));
    }

    // ---------- GET BY ID ----------
    @Test
    void getById_WhenFound_ShouldReturnInstrument() throws Exception {
        Mockito.when(instrumentService.findById(1L))
                .thenReturn(Optional.of(sampleInstrument()));

        mockMvc.perform(get("/api/instruments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple Inc"));
    }

    @Test
    void getById_WhenNotFound_ShouldReturn404() throws Exception {
        Mockito.when(instrumentService.findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/instruments/99"))
                .andExpect(status().isNotFound());
    }

    // ---------- GET BY SYMBOL ----------
    @Test
    void getBySymbol_WhenFound_ShouldReturnInstrument() throws Exception {
        Mockito.when(instrumentService.findBySymbol("AAPL"))
                .thenReturn(Optional.of(sampleInstrument()));

        mockMvc.perform(get("/api/instruments/by-symbol/AAPL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("AAPL"));
    }

    @Test
    void getBySymbol_WhenNotFound_ShouldReturn404() throws Exception {
        Mockito.when(instrumentService.findBySymbol("TSLA"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/instruments/by-symbol/TSLA"))
                .andExpect(status().isNotFound());
    }

    // ---------- CREATE ----------
    @Test
    void create_ShouldReturnCreatedInstrument() throws Exception {
        Mockito.when(instrumentService.save(Mockito.any(Instrument.class)))
                .thenReturn(sampleInstrument());

        mockMvc.perform(post("/api/instruments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleInstrument())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.symbol").value("AAPL"));
    }

    // ---------- UPDATE ----------
    @Test
    void update_WhenFound_ShouldReturnUpdatedInstrument() throws Exception {
        Mockito.when(instrumentService.findById(1L))
                .thenReturn(Optional.of(sampleInstrument()));
        Mockito.when(instrumentService.save(Mockito.any(Instrument.class)))
                .thenReturn(sampleInstrument());

        mockMvc.perform(put("/api/instruments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleInstrument())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("AAPL"));
    }

    @Test
    void update_WhenNotFound_ShouldReturn404() throws Exception {
        Mockito.when(instrumentService.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/instruments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleInstrument())))
                .andExpect(status().isNotFound());
    }

    // ---------- DELETE ----------
    @Test
    void delete_ShouldReturn204() throws Exception {
        Mockito.doNothing().when(instrumentService).delete(1L);

        mockMvc.perform(delete("/api/instruments/1"))
                .andExpect(status().isNoContent());
    }
}