package org.carsharing.customer;

import org.carsharing.car.Car;

import java.util.List;

public interface CustomerDao {
    public List<Customer> getAllCustomer();
    public Customer getCustomers(int rollNo);
    public void updateCustomer();
    public void deleteCustomer();
    public void addCustomer(Customer customer);
}
