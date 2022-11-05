package org.carsharing.car;

import java.util.List;

public interface CarDao {
    public List<Car> getAllCars();
    public Car getCar(int rollNo);
    public void updateCar();
    public void deleteCar();
    public void addCar(Car car);
}
