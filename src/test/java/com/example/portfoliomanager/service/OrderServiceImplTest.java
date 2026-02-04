package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Order;
import com.example.portfoliomanager.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    // ---------------- FIND ALL ----------------

    @Test
    void findAll_shouldReturnList() {
        Order o1 = new Order();
        Order o2 = new Order();

        when(orderRepository.findAll()).thenReturn(List.of(o1, o2));

        List<Order> result = orderService.findAll();

        assertEquals(2, result.size());
    }

    // ---------------- FIND BY ID ----------------

    @Test
    void findById_shouldReturnOptional() {
        Order order1 = new Order();
        order1.setId(1L);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order1));

        Optional<Order> result = orderService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    // ---------------- PLACE ORDER ----------------

    @Test
    void place_shouldSetPendingStatusAndPlacedAt() {
        Order order = new Order();

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.place(order);

        assertEquals(Order.OrderStatus.PENDING, result.getStatus());
        assertNotNull(result.getPlacedAt());
    }

    // ---------------- SAVE ----------------

    @Test
    void save_shouldPersistOrder() {
        Order order = new Order();
        order.setId(5L);

        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.save(order);

        assertEquals(5L, result.getId());
    }

    // ---------------- DELETE ----------------

    @Test
    void delete_shouldCallRepositoryDelete() {
        Long id = 10L;

        doNothing().when(orderRepository).deleteById(id);

        orderService.delete(id);

        verify(orderRepository, times(1)).deleteById(id);
    }

    // ---------------- EXECUTE ORDER ----------------

    @Test
    void execute_shouldSetExecutedStatusAndExecutedAt() {
        Order order = new Order();
        order.setId(20L);
        order.setStatus(Order.OrderStatus.PENDING);

        when(orderRepository.findById(20L))
                .thenReturn(Optional.of(order));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.execute(20L);

        assertEquals(Order.OrderStatus.EXECUTED, result.getStatus());
        assertNotNull(result.getExecutedAt());
    }

    @Test
    void execute_shouldThrowException_whenOrderNotFound() {
        when(orderRepository.findById(99L))
                .thenReturn(Optional.empty());

        NoSuchElementException exception =
                assertThrows(NoSuchElementException.class,
                        () -> orderService.execute(99L));

        assertTrue(exception.getMessage().contains("Order not found"));
    }
}
