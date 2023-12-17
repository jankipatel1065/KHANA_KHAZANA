package com.humber.khana_khazana.controllers;

import com.humber.khana_khazana.models.Item;
import com.humber.khana_khazana.models.Order;
import com.humber.khana_khazana.models.Product;
import com.humber.khana_khazana.service.ItemService;
import com.humber.khana_khazana.service.OrderService;
import com.humber.khana_khazana.service.ProductsService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/order")
public class OrderController {

    // orderservice
    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductsService productService;

    // get orders list
    @GetMapping("/orders")
    public String showAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);

        // Iterate over each order and fetch items
        for (Order order : orders) {
            List<Item> items = itemService.getItemsByOrderId(order.getOrderId());
            model.addAttribute("items_" + order.getOrderId(), items);
        }

        return "orderReport"; // This assumes you have a Thymeleaf template in
                              // src/main/resources/templates/orders/list.html
    }

    @GetMapping("/create")
    public String showOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "createOrder";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order, Model model) {
        // Save order details to the database
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(0); // You might calculate the total price based on the items
        orderService.addOrders(order);

        // Redirect to the page to add items to the order
        return "redirect:/order/addItems/" + order.getOrderId();
    }

    @GetMapping("/addItems/{orderId}")
    public String showAddItemsForm(@PathVariable Long orderId, Model model) {
        Order order = orderService.getById(orderId);
        List<Product> productList = productService.getAllProductsByStatus();

        // Create a new item and set the order
        Item item = new Item();
        item.setOrder(order);

        model.addAttribute("order", order);
        model.addAttribute("productList", productList);
        model.addAttribute("item", item);

        return "addItems";
    }

    @GetMapping("/viewOrder/{orderId}")
    public String viewOrder(@PathVariable Long orderId, Model model) {
        // Retrieve the order by orderId
        Order order = orderService.getById(orderId);

        // Add the order to the model
        model.addAttribute("order", order);

        // Return the viewOrder.html Thymeleaf template
        return "viewOrder";
    }

    @PostMapping("/addItems")
    public String addItemToOrder(@ModelAttribute Item item) {
        // Retrieve the order and product from the database
        Order order = orderService.getById(item.getOrder().getOrderId());
        Product product = productService.productByid(item.getProduct().getId());

        // Create and add the item to the order
        item.setOrder(order);
        item.setProduct(product);
        order.getItems().add(item);

        itemService.addItems(item);
        // Update the total price of the order (you may need to implement this logic)
        orderService.updateOrderTotalPrice(order);
        orderService.addOrders(order);

        // Redirect to the same page to continue adding items
        return "redirect:/order/addItems/" + order.getOrderId();
    }

    @GetMapping("/deleteItem/{orderId}/{itemId}")
    public String deleteItemFromOrder(@PathVariable Long orderId, @PathVariable Long itemId) {
        // Retrieve the order from the database
        Order order = orderService.getById(orderId);

        // Remove the item from the order based on the itemId
        order.getItems().removeIf(item -> Objects.equals(item.getItemId(), itemId));

        // Update the total price of the order (you may need to implement this logic)
        orderService.updateOrderTotalPrice(order);

        // Redirect to the same page to continue managing items
        return "redirect:/order/addItems/" + orderId;
    }

    @GetMapping("/generateReport/{orderId}")
    public String generateOrderReport(@PathVariable Long orderId, Model model) {
        // Logic to generate the order report
        Order order = orderService.getById(orderId);
        model.addAttribute("order", order);

        return "orderReport";
    }
}
