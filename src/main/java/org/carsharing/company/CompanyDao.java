package org.carsharing.company;

import org.carsharing.company.Company;

import java.util.List;

public interface CompanyDao {
    public List<Company> getAllCompanies();
    public Company getCompany(int rollNo);
    public void updateCompany();
    public void deleteCompany();
    public void addCompany(Company company);
}
