package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.Instrument;
import com.example.portfoliomanager.beans.Order;
import com.example.portfoliomanager.beans.Order.OrderStatus;
import com.example.portfoliomanager.beans.Order.OrderType;
import com.example.portfoliomanager.beans.Order.Side;
import com.example.portfoliomanager.service.OrderService;
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

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    // ---------- TEST DATA ----------
    private Instrument instrument() {
        Instrument i = new Instrument();
        i.setId(1L);
        i.setSymbol("AAPL");
        i.setName("Apple Inc");
        return i;
    }

    private Order order() {
        return Order.builder()
                .id(1L)
                .instrument(instrument())
                .side(Side.BUY)
                .orderType(OrderType.LIMIT)
                .quantity(10.0)
                .limitPrice(150.0)
                .status(OrderStatus.PENDING)
                .placedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
    }

    // ---------- GET ALL ----------
    @Test
    void list_ShouldReturnAllOrders() throws Exception {
        Mockito.when(orderService.findAll())
                .thenReturn(List.of(order()));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }

    // ---------- GET BY ID ----------
    @Test
    void getById_WhenFound_ShouldReturnOrder() throws Exception {
        Mockito.when(orderService.findById(1L))
                .thenReturn(Optional.of(order()));

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderType").value("LIMIT"));
    }

    @Test
    void getById_WhenNotFound_ShouldReturn404() throws Exception {
        Mockito.when(orderService.findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/orders/99"))
                .andExpect(status().isNotFound());
    }

    // ---------- PLACE ORDER ----------
    @Test
    void place_ShouldReturnCreatedOrder() throws Exception {
        Mockito.when(orderService.place(Mockito.any(Order.class)))
                .thenReturn(order());

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order())))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/orders/1"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    // ---------- EXECUTE ORDER ----------
    @Test
    void execute_ShouldReturnExecutedOrder() throws Exception {
        Order executed = Order.builder()
                .id(1L)
                .instrument(instrument())
                .side(Side.BUY)
                .orderType(OrderType.LIMIT)
                .quantity(10.0)
                .limitPrice(150.0)
                .status(OrderStatus.EXECUTED)
                .placedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .executedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        Mockito.when(orderService.execute(1L))
                .thenReturn(executed);

        mockMvc.perform(post("/api/orders/1/execute"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("EXECUTED"));
    }

    // ---------- UPDATE ORDER ----------
    @Test
    void update_WhenFound_ShouldReturnUpdatedOrder() throws Exception {
        Mockito.when(orderService.findById(1L))
                .thenReturn(Optional.of(order()));
        Mockito.when(orderService.save(Mockito.any(Order.class)))
                .thenReturn(order());

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void update_WhenNotFound_ShouldReturn404() throws Exception {
        Mockito.when(orderService.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order())))
                .andExpect(status().isNotFound());
    }

    // ---------- DELETE ----------
    @Test
    void delete_ShouldReturn204() throws Exception {
        Mockito.doNothing().when(orderService).delete(1L);

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
    }
}
