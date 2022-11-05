package org.carsharing.interfaces;

import org.carsharing.ConnectionClass;
import org.carsharing.company.CompanyDaoImplementation;


public class ManagerInterface extends UserInterface {

     private ConnectionClass dbConnector;
     private CompanyDaoImplementation companyDao;

    public ManagerInterface() {
        super();
        this.interfaceOptions.put(1, "Company list");
        this.interfaceOptions.put(2, "Create a company");
        this.interfaceOptions.put(0, "Back");
        this.dbConnector = ConnectionClass.getInstance();
        this.companyDao = new CompanyDaoImplementation();
    }

    @Override
    public void printAndRun() {
        super.printAndRun();
    }

    @Override
    public void execute(int input) {
        switch (input) {
            case 1: {
                listCompanies();
                break;
            }
            case 2: {
                createCompany();
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

    public void listCompanies() {
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

           if(userChoose != 0) {
               CompanyListInterface companyListInterface = new CompanyListInterface(companyDao.getCompany(userChoose - 1));
               companyListInterface.printAndRun();
           }
       }
    }

    public void chooseCompany() {
    }

    public int chooseCompanyInputCheck(int input) {
        if(input > companyDao.getAllCompanies().size() || input < 0) {
            return -1;
        } else {
            return input;
        }
    }
    public void createCompany() {
        String name = null;
        System.out.println("Enter the company name:");
        name = inputRequest();
        dbConnector.addCompany(name);
        System.out.println("The company was created!");
    }
}
