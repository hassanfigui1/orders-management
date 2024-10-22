package com.customerservice.Services;

import com.customerservice.Models.Customer;
import com.customerservice.Repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo repo;

    public List<Customer> getCustomers() {
        return repo.findAll();
    }

    public Customer getCustomerById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Customer addCustomer(Customer customer) {
        return repo.save(customer);
    }

    public Customer updateCustomer(int id, Customer customer) {
        customer.setCustomerId(id);
        return repo.save(customer);
    }

    public void deleteCustomer(int customerId) {
        repo.deleteById(customerId);
    }
}
