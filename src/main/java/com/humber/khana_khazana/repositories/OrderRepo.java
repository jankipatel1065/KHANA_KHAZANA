package com.humber.khana_khazana.repositories;

import com.humber.khana_khazana.models.Item;
import com.humber.khana_khazana.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByOrderDateBetween(LocalDateTime orderDate, LocalDateTime orderDate2);
    long countByOrderDateBetween(LocalDateTime orderDate, LocalDateTime orderDate2);

    List<Order> findOrdersByItemsContaining(Item item);
}
