package com.orderservice.Controllers;

import com.orderservice.Models.Order;
import com.orderservice.Models.orderitem.OrderItem;
import com.orderservice.Repositories.OrderRepo;
import com.orderservice.Services.OrderService;
import com.orderservice.interfaces.CustomerClient;
import com.orderservice.interfaces.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductClient productClient;
    private final CustomerClient customerClient;

    @Autowired
    public OrderController(OrderService orderService, ProductClient productClient, CustomerClient customerClient) {
        this.orderService = orderService;
        this.productClient = productClient;
        this.customerClient = customerClient;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            // Validate the customer
            if (!isValidCustomer(order.getCustomerId())) {
                return ResponseEntity.badRequest().build();
            }

            // Validate the product IDs in the order items
            for (OrderItem item : order.getOrderItems()) {
                if (!isValidProduct(item.getProductId())) {
                    System.out.println(" ERRRRRRRRRRRRRRRRRRRRRRRRRRRRROR");
                    return ResponseEntity.badRequest().build();
                }
            }

            // Save the order if all validations pass
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception e) {
            System.out.println("Error creating order : ************* :  "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean isValidCustomer(int customerId) {
        Object customer = customerClient.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer with ID " + customerId + " not found.");
        }
        return customer != null;
    }

    private boolean isValidProduct(int productId) {
        Object product = productClient.getProductById(productId);
        if (product == null) {
            System.out.println("Product with ID " + productId + " not found.");
        }
        return product != null;
    }
}
