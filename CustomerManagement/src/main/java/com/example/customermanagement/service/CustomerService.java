package com.example.customermanagement.service;

import com.example.customermanagement.entity.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final ObjectMapper objectMapper;
    private final AtomicLong idGenerator = new AtomicLong(1);
    private static final String FILE_PATH = "customers.json";

    private List<Customer> loadCustomers() {
        try {
            File file = new ClassPathResource(FILE_PATH).getFile();
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<Customer>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load customers", e);
        }
    }

    private void saveCustomers(List<Customer> customers) {
        try {
            File file = new ClassPathResource(FILE_PATH).getFile();
            objectMapper.writeValue(file, customers);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save customers", e);
        }
    }

    public Customer createCustomer(Customer customer) {
        List<Customer> customers = loadCustomers();
        customer.setId(idGenerator.getAndIncrement());
        customers.add(customer);
        saveCustomers(customers);
        return customer;
    }

    public Optional<Customer> getCustomerById(Long id) {
        return loadCustomers().stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    public Customer getCustomerByEmail(String email) {
        return loadCustomers().stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return loadCustomers();
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        List<Customer> customers = loadCustomers();
        Customer customer = customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPassword(customerDetails.getPassword());
        saveCustomers(customers);
        return customer;
    }

    public void deleteCustomer(Long id) {
        List<Customer> customers = loadCustomers();
        Customer customer = customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setActive(false);
        saveCustomers(customers);
    }
}