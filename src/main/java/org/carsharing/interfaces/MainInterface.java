package org.carsharing.interfaces;

import org.carsharing.ConnectionClass;
import org.carsharing.customer.Customer;
import org.carsharing.customer.CustomerDaoImplementation;

import java.util.List;

public class MainInterface extends UserInterface {

    private ConnectionClass dbConnector;
    private CustomerDaoImplementation customerDao;
    public MainInterface() {
        super();
        this.dbConnector = ConnectionClass.getInstance();
        this.customerDao = new CustomerDaoImplementation();
        this.interfaceOptions.put(1, "Log in as a manager");
        this.interfaceOptions.put(2, "Log in as a customer");
        this.interfaceOptions.put(3, "Create a customer");
        this.interfaceOptions.put(0, "Exit");
    }


    public void logToManagerManu() {
        ManagerInterface managerInterface = new ManagerInterface();
        managerInterface.printAndRun();
    }

    public void exit() {
    }
    public void addCustomer() {
        System.out.println("Enter the customer name:");
        String newCustomerName = inputRequest();
        dbConnector.addCustomer(newCustomerName);

    }

    public void logToCustomerManu() {
        customerDao.updateDao();
        List<Customer> customers = customerDao.getAllCustomer();
        if(customers.isEmpty()) {
            System.out.println("The customer list is empty!");
        } else {
            System.out.println("Customer list:");
            for(int i = 0; i < customers.size(); i++) {
                System.out.println(customers.get(i).getId() + ". " + customers.get(i).getName());
            }
            System.out.println("0. Back");
            int userChoose = -1;

            while(userChoose == -1) {
                userChoose = chooseCustomerInputCheck(Integer.parseInt(inputRequest()));
            }

            if(userChoose != 0) {
                CustomerInterface customerInterface = new CustomerInterface(customerDao.getCustomers(userChoose - 1));
                customerInterface.printAndRun();
            }
        }
    }

    public int chooseCustomerInputCheck(int input) {
        if(input > customerDao.getAllCustomer().size() || input < 0) {
            return -1;
        } else {
            return input;
        }
    }
    @Override
    public void execute(int input) {
        switch (input) {
            case 1: {
                logToManagerManu();
                break;
            }
            case 2: {
                logToCustomerManu();
                break;
            }
            case 3: {
                addCustomer();
                break;
            }
            case 0: {
                exit();
            }
            default: {
                break;
            }
        }
    }
}
