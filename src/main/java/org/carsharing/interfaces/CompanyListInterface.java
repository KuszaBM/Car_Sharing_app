package org.carsharing.interfaces;

import org.carsharing.ConnectionClass;
import org.carsharing.car.Car;
import org.carsharing.car.CarDaoImplementation;
import org.carsharing.company.Company;
import java.util.List;

public class CompanyListInterface extends UserInterface {

    ConnectionClass dbConnector;
    Company company;
    CarDaoImplementation carDaoImplementation;

    public CompanyListInterface(Company company) {
        super();
        this.interfaceOptions.put(1, "Car list");
        this.interfaceOptions.put(2, "Create a car");
        this.interfaceOptions.put(0, "Back");
        this.dbConnector = ConnectionClass.getInstance();
        this.company = company;
        this.carDaoImplementation = new CarDaoImplementation();
    }

    @Override
    public void printAndRun() {
        System.out.println("'" + company.getName() + "' company");
        super.printAndRun();
    }

    public void listCars() {

        this.carDaoImplementation.updateDao();

        List<Car> companyCars = carDaoImplementation.getCompanyCars(company.getId());

        if(companyCars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Car list:");
            for(int i = 0; i < companyCars.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, companyCars.get(i).getName());
            }
        }

    }

    public void createCar() {
        String name = null;
        System.out.println("Enter the car name:");
        name = inputRequest();
        dbConnector.addCar(name, company.getId());
        System.out.println("The car was created!");
    }

    @Override
    public void execute(int input) {
        switch (input) {
            case 1: {
                listCars();
                break;
            }
            case 2: {
                createCar();
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
