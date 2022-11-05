package org.carsharing.car;
import org.carsharing.ConnectionClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarDaoImplementation implements CarDao{
    private List<Car> carList;
    private ConnectionClass DBController;

    public CarDaoImplementation() {
        this.carList = new ArrayList<>();
        this.DBController = ConnectionClass.getInstance();
    }
    public void updateDao() {
        this.carList = this.DBController.getCars();
    }

    @Override
    public void addCar(Car car) {
        carList.add(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carList;
    }

    @Override
    public Car getCar(int rollNo) {
        return carList.get(rollNo);
    }

    @Override
    public void updateCar() {

    }

    @Override
    public void deleteCar() {

    }

    public List<Car> getCompanyCars(int companyId) {
        return this.carList.stream()
                .filter(n -> n.getCompanyId() == companyId)
                .collect(Collectors.toList());
    }

    public Car getById(int id) {
        updateDao();
        for(int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getId() == id) {
                return carList.get(i);
            }
        }
        return null;
    }

    public void clearDao() {carList.clear(); }


}
