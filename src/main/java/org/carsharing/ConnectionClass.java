package org.carsharing;

import org.carsharing.car.Car;
import org.carsharing.company.Company;
import org.carsharing.customer.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionClass {

    private static ConnectionClass uniqueInstance = new ConnectionClass();
    private ConnectionClass() {}
    public static ConnectionClass getInstance() {
        return uniqueInstance;
    }
    static final String JDBC_DRIVER = "org.h2.Driver";
    //static final String DB_URL = "jdbc:h2:./src/main/java/org.carsharing/db/carsharing";
   // static final String DB_URL = "jdbc:h2:~/test", "sa","";
    Connection conn = null;
    Statement stmt = null;

    public void getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            this.conn = DriverManager.getConnection("jdbc:h2:~/test", "sa","");
            this.conn.setAutoCommit(true);
            this.stmt = conn.createStatement();

        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public void executeStatement(String statement) {
        try {
            this.stmt.execute(statement);
        } catch (SQLException se) {
            System.out.printf("Wrong SQL statement");
        }
    }

    public void createTable() {
        if(conn != null && stmt != null) {
            executeStatement("CREATE TABLE IF NOT EXISTS COMPANY (ID INTEGER PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR UNIQUE NOT NULL);");
        }
    }

    public ArrayList<Company> getCompanies() {
        ArrayList<Company> companies = new ArrayList<>();
        try {
            ResultSet r1 = conn.createStatement().executeQuery("SELECT ID, NAME FROM COMPANY");
            while(r1.next()) {
                companies.add(new Company(r1.getString("name"), r1.getInt("id")));
            }
        } catch (SQLException ignore) {
        }
        return companies;
    }

    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            ResultSet r1 = conn.createStatement().executeQuery("SELECT ID, NAME, COMPANY_ID FROM CAR");
            while(r1.next()) {
                cars.add(new Car(r1.getInt("id"), r1.getString("name"), r1.getInt("company_id")));
            }
        } catch (SQLException ignore) {

        }
        return  cars;
    }

    public void addCompany(String name) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO COMPANY (name) VALUES (?)");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCar(String name, int companyId) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO CAR (name, company_id) VALUES (?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, companyId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCustomer(String name) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO CUSTOMER (name) VALUES (?)");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCustomerCar(String name, int carId) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE CUSTOMER \n" +
                    "SET RENTED_CAR_ID = ? \n" +
                    "WHERE NAME = ?;");
            preparedStatement.setInt(1, carId);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getAllRentedCarsId() {
        ArrayList<Integer> rentedCars = new ArrayList<>();
        try {
            ResultSet r1 = conn.createStatement().executeQuery("SELECT RENTED_CAR_ID FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL;");
            while(r1.next()) {
                rentedCars.add(r1.getInt("rented_car_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error during import");
        }
        return rentedCars;
    }
    public void removeCustomerCar(String name) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE CUSTOMER \n" +
                    "SET RENTED_CAR_ID = NULL \n" +
                    "WHERE NAME = ?;");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            ResultSet r1 = conn.createStatement().executeQuery("SELECT ID, NAME, RENTED_CAR_ID FROM CUSTOMER");
            while(r1.next()) {
                customers.add(new Customer(r1.getInt("id"), r1.getString("name"), r1.getInt("rented_car_id")));
            }
        } catch (SQLException e) {
            System.out.println("Error during import");
        }
        return  customers;
    }
    public void dropTable(String tableName) {
        if(conn != null && stmt != null) {
            executeStatement("DROP TABLE " + tableName + ";");
        }
    }

    public void addCarTable() {
        if(conn != null && stmt != null) {
            executeStatement("CREATE TABLE IF NOT EXISTS CAR (ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR UNIQUE NOT NULL, " +
                    "COMPANY_ID INT NOT NULL, " +
                    "CONSTRAINT fk_company FOREIGN KEY (COMPANY_ID) " +
                    "REFERENCES COMPANY(ID));");
        }
    }

    public void addCustomerTable() {
        if(conn != null && stmt != null) {
            executeStatement("CREATE TABLE IF NOT EXISTS CUSTOMER (ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR UNIQUE NOT NULL, " +
                    "RENTED_CAR_ID INT, " +
                    "CONSTRAINT fk_car FOREIGN KEY (RENTED_CAR_ID) " +
                    "REFERENCES CAR(ID));");
        }
    }

    public void closeConnection() {
        try {
            stmt.close();
            conn.close();
        } catch (Exception se) {
            try {
                if (conn != null) conn.close();
            } catch (SQLException see) {
                se.printStackTrace();
            }
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
