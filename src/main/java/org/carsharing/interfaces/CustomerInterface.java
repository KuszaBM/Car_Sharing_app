package org.carsharing.interfaces;

import org.carsharing.ConnectionClass;
import org.carsharing.car.Car;
import org.carsharing.car.CarDaoImplementation;
import org.carsharing.company.CompanyDaoImplementation;
import org.carsharing.customer.Customer;
import org.carsharing.customer.CustomerDaoImplementation;

import java.util.ArrayList;
import java.util.List;

public class CustomerInterface extends UserInterface {

    private ConnectionClass dbConnector;
    private CustomerDaoImplementation CustomerDao;
    private CompanyDaoImplementation companyDao;
    private CarDaoImplementation carDao;

    private Customer customer;

    public CustomerInterface(Customer customer) {
        super();
        this.customer = customer;
        this.interfaceOptions.put(1, "Rent a car");
        this.interfaceOptions.put(2, "Return a rented car");
        this.interfaceOptions.put(3, "My rented car");
        this.interfaceOptions.put(0, "Back");
        this.CustomerDao = new CustomerDaoImplementation();
        this.companyDao = new CompanyDaoImplementation();
        this.carDao = new CarDaoImplementation();
        this.dbConnector = ConnectionClass.getInstance();
    }

    public void rentCar() {
        CustomerDao.updateDao();
        companyDao.updateDao();
        this.customer = CustomerDao.getCustomerById(customer.getId());
        if (customer.getRentedCarId() != 0) {
            System.out.println("You've already rented a car!");
        } else {
            int userChoose = listCompanies();
            if (userChoose != 0) {
                ArrayList<Car> availableCars = (ArrayList<Car>) listAvailableCars(userChoose);
                if(availableCars.size() == 0) {
                    System.out.println("No available cars in the " + companyDao.getCompany(userChoose-1).getName());
                } else {
                    System.out.println("Choose a Car:");
                    for (int i = 0; i < availableCars.size(); i++) {
                        System.out.printf("%d. %s\n", i + 1, availableCars.get(i).getName());
                    }
                    System.out.println("0. Back");
                    int userCarChoose = -1;

                    while(userCarChoose == -1) {
                        userCarChoose = chooseCarInputCheck(availableCars, Integer.parseInt(inputRequest()));
                    }
                    if(userCarChoose != 0 && userCarChoose != -1) {
                        dbConnector.addCustomerCar(customer.getName(), availableCars.get(userCarChoose - 1).getId());
                        System.out.printf("You rented '%s'\n", availableCars.get(userCarChoose - 1).getName());
                    }
                }
            }
        }
    }

    public void returnRentedCar() {
        CustomerDao.updateDao();
        companyDao.updateDao();
        this.customer = CustomerDao.getCustomerById(customer.getId());
        if(customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
        } else {
            dbConnector.removeCustomerCar(customer.getName());
            System.out.println("You've returned a rented car!");
        }
    }

    public void showRentedCar() {
        CustomerDao.updateDao();
        companyDao.updateDao();
        this.customer = CustomerDao.getCustomerById(customer.getId());
        if(customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
        } else {
            System.out.printf("Your rented car:\n" +
                    "%s\n" +
                    "Company:\n" +
                    "%s\n",
                    carDao.getById(customer.getRentedCarId()).getName(),
                    companyDao.getCompanyById(carDao.getById(customer.getRentedCarId()).getCompanyId()).getName());
        }
    }

    public int listCompanies() {

        int companyId = 0;

        System.out.println("Choose a company:");

        companyDao.updateDao();

        if(companyDao.getAllCompanies().isEmpty() || companyDao.getAllCompanies() == null) {
            System.out.println("The company list is empty!");
        } else {
            for(int i = 0; i < companyDao.getAllCompanies().size(); i++) {
                System.out.printf("%d. %s\n", i + 1, companyDao.getCompany(i).getName());
            }
            System.out.println("0. Back");

            int userChoose = -1;

            while(userChoose == -1) {
                userChoose = chooseCompanyInputCheck(Integer.parseInt(inputRequest()));
            }

            if(userChoose != 0 && userChoose != -1) {
                companyId = companyDao.getCompany(userChoose - 1).getId();
            }
        }
        return companyId;
    }

    public List<Car> listAvailableCars(int companyId) {
        CustomerDao.updateDao();
        carDao.updateDao();
        companyDao.updateDao();
        ArrayList<Car> companyCars = (ArrayList<Car>) carDao.getCompanyCars(companyId);
        ArrayList<Integer> rentedCars = (ArrayList<Integer>) dbConnector.getAllRentedCarsId();
        ArrayList<Car> availableCars = new ArrayList<>();
        for(int i = 0; i < companyCars.size(); i++) {
            boolean isAvailable = true;
            for(int j = 0; j < rentedCars.size(); j++) {
                if (companyCars.get(i).getId() == rentedCars.get(j)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableCars.add(companyCars.get(i));
            }
        }
        return availableCars;
    }

    public int chooseCarInputCheck(List<Car> cars, int input) {
        if(input > cars.size() || input < 0) {
            return -1;
        } else {
            return input;
        }
    }

    public int chooseCompanyInputCheck(int input) {
        if(input > companyDao.getAllCompanies().size() || input < 0) {
            return -1;
        } else {
            return input;
        }
    }
    @Override
    public void execute(int input) {
        switch (input) {
            case 1: {
                rentCar();
                break;
            }
            case 2: {
                returnRentedCar();
                break;
            }
            case 3: {
                showRentedCar();
                break;
            }
            case 0: {
                break;
            }
            default: {
                break;
            }
        }
    }
}
