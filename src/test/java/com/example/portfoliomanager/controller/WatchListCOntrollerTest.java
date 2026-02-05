package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.Instrument;
import com.example.portfoliomanager.beans.WatchlistItem;
import com.example.portfoliomanager.service.WatchlistService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WatchlistController.class)
@AutoConfigureMockMvc(addFilters = false)
class WatchlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WatchlistService watchlistService;

    // ---------- TEST DATA ----------
    private Instrument instrument() {
        Instrument instrument = new Instrument();
        instrument.setId(100L);
        instrument.setSymbol("AAPL");
        instrument.setName("Apple Inc");
        return instrument;
    }

    private WatchlistItem watchlistItem() {
        WatchlistItem item = new WatchlistItem();
        item.setId(1L);
        item.setInstrument(instrument()); // ✅ correct
        return item;
    }

    // ---------- GET ALL ----------
    @Test
    void list_ShouldReturnAllWatchlistItems() throws Exception {
        Mockito.when(watchlistService.findAll())
                .thenReturn(List.of(watchlistItem()));

        mockMvc.perform(get("/api/watchlist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].instrument.symbol").value("AAPL"));
    }

    // ---------- ADD ----------
    @Test
    void add_ShouldReturnCreatedItem() throws Exception {
        Mockito.when(watchlistService.add(Mockito.any(WatchlistItem.class)))
                .thenReturn(watchlistItem());

        mockMvc.perform(post("/api/watchlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(watchlistItem())))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/watchlist/1"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    // ---------- DELETE BY ID ----------
    @Test
    void deleteById_ShouldReturn204() throws Exception {
        Mockito.doNothing().when(watchlistService).delete(1L);

        mockMvc.perform(delete("/api/watchlist/1"))
                .andExpect(status().isNoContent());
    }

    // ---------- DELETE BY INSTRUMENT ----------
    @Test
    void deleteByInstrument_ShouldReturn204() throws Exception {
        Mockito.doNothing().when(watchlistService).deleteByInstrumentId(100L);

        mockMvc.perform(delete("/api/watchlist/by-instrument/100"))
                .andExpect(status().isNoContent());
    }
}
