package org.carsharing.customer;

public class Customer {
    private int id;
    private String name;
    private Integer rentedCarId;

    public Customer(int id, String name, Integer carId) {
        this.id = id;
        this.name = name;
        if(carId == null) {
            this.rentedCarId = 0;
        } else {
            this.rentedCarId = carId;
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(int rentedCarId) {
        this.rentedCarId = rentedCarId;
    }
}
