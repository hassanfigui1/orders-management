package com.orderservice.Services;

import com.orderservice.Models.Order;
import com.orderservice.Repositories.OrderRepo;
import com.orderservice.interfaces.CustomerClient;
import com.orderservice.interfaces.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CustomerClient customerClient;

    public Order createOrder(Order order) {
        order.setOrderDate(new Date());
        // Calculate total amount based on order items
        double totalAmount = order.getOrderItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(totalAmount);
        return orderRepo.save(order);
    }

    public Order getOrderById(int id) {
        return orderRepo.findById(id).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
