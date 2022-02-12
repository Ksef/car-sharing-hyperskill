package company;

import java.util.List;

public interface CompanyDao {

    List<Company> getAllCompanies();

    void addCompany(Company company);
}