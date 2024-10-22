package com.orderservice.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Product-Service")
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    Object getProductById(@PathVariable int id);
}
