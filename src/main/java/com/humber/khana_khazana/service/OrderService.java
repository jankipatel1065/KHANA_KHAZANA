package com.humber.khana_khazana.service;

import com.humber.khana_khazana.models.Item;
import com.humber.khana_khazana.models.Order;
import com.humber.khana_khazana.repositories.ItemRepo;
import com.humber.khana_khazana.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private  OrderRepo orderRepo;
    private ItemRepo itemRepo;
    @Autowired
   public OrderService(OrderRepo orderRepo,
                       ItemRepo itemRepo)
    {
        this.orderRepo=orderRepo;
        this.itemRepo=itemRepo;
    }
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepo.findAll();
    }
    public void addOrders(Order p)
    {
        this.orderRepo.save(p);
    }

    public Long getOrderById(Long orderId) {
         orderRepo.findById(orderId);
        return orderId;
    }

    public Order getById(Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }
    public void addItemToOrder(Item item) {
        Order existingOrder = getById(item.getOrder().getOrderId());
        item.setOrder(existingOrder);

        // Save the item to the database
        itemRepo.save(item);

        // Update the order to include the new item
        existingOrder.getItems().add(item);
    }


    public void updateOrderTotalPrice(Order order) {
        Order persistedOrder = getById(order.getOrderId());

        // Calculate the total price based on the items
        double totalPrice = persistedOrder.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // Update the order's total price
        persistedOrder.setTotalPrice(totalPrice);

        // Save the updated order to the database
        orderRepo.save(persistedOrder);
    }
}
