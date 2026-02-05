package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Instrument;
import com.example.portfoliomanager.beans.WatchlistItem;
import com.example.portfoliomanager.repository.WatchlistItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceImplTest {

    @Mock
    private WatchlistItemRepository repo;

    @InjectMocks
    private WatchlistServiceImpl service;

    private WatchlistItem item;

    @BeforeEach
    void setup() {
        Instrument instrument = new Instrument();
        instrument.setId(1L);
        instrument.setSymbol("AAPL");
        instrument.setName("Apple Inc");

        item = new WatchlistItem();
        item.setId(10L);
        item.setInstrument(instrument);
        item.setAddedAt(OffsetDateTime.now());
    }

    // ---------- FIND ALL ----------
    @Test
    void findAll_ShouldReturnItems() {
        when(repo.findAll()).thenReturn(List.of(item));

        List<WatchlistItem> result = service.findAll();

        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    // ---------- FIND BY INSTRUMENT ID ----------
    @Test
    void findByInstrumentId_WhenFound_ShouldReturnItem() {
        when(repo.findByInstrumentId(1L)).thenReturn(Optional.of(item));

        Optional<WatchlistItem> result = service.findByInstrumentId(1L);

        assertTrue(result.isPresent());
        assertEquals(10L, result.get().getId());
        verify(repo).findByInstrumentId(1L);
    }

    // ---------- ADD ----------
    @Test
    void add_WhenAddedAtIsNull_ShouldSetTimestamp() {
        item.setAddedAt(null);
        when(repo.save(any(WatchlistItem.class))).thenReturn(item);

        WatchlistItem saved = service.add(item);

        assertNotNull(saved.getAddedAt(), "addedAt should be set by service");
        verify(repo).save(item);
    }

    @Test
    void add_WhenAddedAtAlreadyPresent_ShouldNotOverride() {
        OffsetDateTime originalTime = item.getAddedAt();
        when(repo.save(any(WatchlistItem.class))).thenReturn(item);

        WatchlistItem saved = service.add(item);

        assertEquals(originalTime, saved.getAddedAt());
        verify(repo).save(item);
    }

    // ---------- DELETE BY ID ----------
    @Test
    void delete_ShouldCallRepository() {
        doNothing().when(repo).deleteById(10L);

        service.delete(10L);

        verify(repo).deleteById(10L);
    }

    // ---------- DELETE BY INSTRUMENT ID ----------
    @Test
    void deleteByInstrumentId_ShouldCallRepository() {
        doNothing().when(repo).deleteByInstrumentId(1L);

        service.deleteByInstrumentId(1L);

        verify(repo).deleteByInstrumentId(1L);
    }
}
