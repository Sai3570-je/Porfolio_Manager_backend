package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Instrument;
import com.example.portfoliomanager.repository.InstrumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstrumentServiceImplTest {

    @Mock
    private InstrumentRepository instrumentRepository;

    @InjectMocks
    private InstrumentServiceImpl instrumentService;

    // ---------------- SAVE ----------------

    @Test
    void save_shouldSaveInstrument_whenSymbolDoesNotExist() {
        Instrument instrument = new Instrument();
        instrument.setSymbol("AAPL");

        when(instrumentRepository.existsBySymbol("AAPL")).thenReturn(false);
        when(instrumentRepository.save(instrument)).thenReturn(instrument);

        Instrument results = instrumentService.save(instrument);

        assertEquals("AAPL", results.getSymbol());
    }

    @Test
    void save_shouldThrowConflict_whenSymbolAlreadyExists() {
        Instrument instrument = new Instrument();
        instrument.setSymbol("AAPL");

        when(instrumentRepository.existsBySymbol("AAPL")).thenReturn(true);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class,
                        () -> instrumentService.save(instrument));

        assertEquals(409, exception.getStatusCode().value());
    }

    // ---------------- FIND ALL ----------------

    @Test
    void findAll_shouldReturnList() {
        Instrument i1 = new Instrument();
        Instrument i2 = new Instrument();

        when(instrumentRepository.findAll()).thenReturn(List.of(i1, i2));

        List<Instrument> result = instrumentService.findAll();

        assertEquals(2, result.size());
    }

    // ---------------- FIND BY ID ----------------

    @Test
    void findById_shouldReturnOptional() {
        Instrument instrument = new Instrument();
        instrument.setId(1L);

        when(instrumentRepository.findById(1L))
                .thenReturn(Optional.of(instrument));

        Optional<Instrument> result = instrumentService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    // ---------------- FIND BY SYMBOL ----------------

    @Test
    void findBySymbol_shouldReturnOptional() {
        Instrument instrument = new Instrument();
        instrument.setSymbol("AAPL");

        when(instrumentRepository.findBySymbol("AAPL"))
                .thenReturn(Optional.of(instrument));

        Optional<Instrument> result = instrumentService.findBySymbol("AAPL");

        assertTrue(result.isPresent());
        assertEquals("AAPL", result.get().getSymbol());
    }

    // ---------------- DELETE ----------------

    @Test
    void delete_shouldCallRepositoryDelete() {
        Long id = 10L;

        doNothing().when(instrumentRepository).deleteById(id);

        instrumentService.delete(id);

        verify(instrumentRepository, times(1)).deleteById(id);
    }
}
