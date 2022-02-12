package company;

import configuration.DBManager;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

    private final DBManager dbManager;

    public CompanyDaoImpl (DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Company> getAllCompanies(){
        try {
            List<Company> companies = new LinkedList<>();

            ResultSet result = dbManager
                    .executeQuery("SELECT * FROM COMPANY ORDER BY ID");

            while(result.next()){
                companies.add(new Company(result.getInt("ID"),result.getString("NAME")));
            }

            return companies;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public void addCompany(Company company){
        dbManager.execute("INSERT INTO COMPANY(NAME) VALUES ('" + company.getName() +"');");
    }
}
