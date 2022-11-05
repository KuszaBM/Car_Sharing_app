package org.carsharing.customer;

import org.carsharing.ConnectionClass;
import org.carsharing.car.Car;

import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImplementation implements CustomerDao{
    private List<Customer> customerList;
    private ConnectionClass DBController;

    public CustomerDaoImplementation() {
        this.customerList = new ArrayList<>();
        this.DBController = ConnectionClass.getInstance();
    }
    public void updateDao() {
        this.customerList = DBController.getCustomers();
    }

    public Customer getCustomerById(int id) {
        for(int i = 0; i < customerList.size(); i++) {
            if(customerList.get(i).getId() == id) {
                return customerList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerList;
    }

    @Override
    public Customer getCustomers(int rollNo) {
        return customerList.get(rollNo);
    }

    @Override
    public void updateCustomer() {

    }

    @Override
    public void deleteCustomer() {

    }

    @Override
    public void addCustomer(Customer customer) {

    }
}
