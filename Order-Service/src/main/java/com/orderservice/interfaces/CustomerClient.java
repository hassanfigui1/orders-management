package com.orderservice.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Customer-Service")
public interface CustomerClient {

    @GetMapping("/api/customers")
    List<Object> getAllCustomers();

    @GetMapping("/api/customers/{id}")
    Object getCustomerById(@PathVariable int id);
}
