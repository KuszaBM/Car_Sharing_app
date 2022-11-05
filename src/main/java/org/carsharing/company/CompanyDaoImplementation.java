package org.carsharing.company;

import org.carsharing.ConnectionClass;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImplementation implements CompanyDao {

    private List<Company> companyList;
    private ConnectionClass DBController;

    public CompanyDaoImplementation() {
        this.companyList = new ArrayList<>();
        this.DBController = ConnectionClass.getInstance();
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyList;
    }

    @Override
    public Company getCompany(int rollNo) {
        return companyList.get(rollNo);
    }

    public Company getCompanyById(int id) {
        for (int i = 0; i < companyList.size(); i++) {
            if(companyList.get(i).getId() == id) {
                return companyList.get(i);
            }
        }
        return null;
    }

    @Override
    public void updateCompany() {

    }

    @Override
    public void deleteCompany() {

    }
    @Override
    public void addCompany(Company company) {
        companyList.add(company);
    }

    public void clearDao() {companyList.clear(); }
    public void updateDao() {
        if (DBController.getCompanies() != null)
        {
            this.companyList = DBController.getCompanies();
        }
    }
}

