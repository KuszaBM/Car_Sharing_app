package org.carsharing;

import org.carsharing.interfaces.MainInterface;
import org.carsharing.interfaces.UserInterface;

public class CarSharingApp {


    public void run() {

        ConnectionClass dbConnector = ConnectionClass.getInstance();
        UserInterface main = new MainInterface();

        //dbConnector.dropTable("CUSTOMER");
        //dbConnector.dropTable("CAR");
        //dbConnector.dropTable("COMPANY");
        dbConnector.getConnection();
        dbConnector.createTable();
        dbConnector.addCarTable();
        dbConnector.addCustomerTable();
        main.printAndRun();
        dbConnector.closeConnection();

    }
}
