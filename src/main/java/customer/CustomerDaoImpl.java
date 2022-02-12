package customer;

import car.Car;
import configuration.DBManager;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    private final DBManager dbManager;

    public CustomerDaoImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Customer> getAllCustomers() {
        try {
            List<Customer> customers = new LinkedList<>();
            ResultSet result = dbManager
                    .executeQuery("SELECT * FROM CUSTOMER ORDER BY ID");
            while (result.next()) {
                customers.add(new Customer(
                        result.getInt("ID"),
                        result.getString("NAME"),
                        result.getInt("RENTED_CAR_ID")));
            }
            return customers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        dbManager.execute(
                "INSERT INTO CUSTOMER(NAME) VALUES ('" + customer.getName() + "');");
    }

    @Override
    public void rentCar(int CustomerID, int CarID) {
        dbManager.execute(
                "UPDATE CUSTOMER SET RENTED_CAR_ID = " + CarID + "WHERE ID = " + CustomerID + ";");
    }

    @Override
    public void returnCar(int CustomerID) {
        dbManager.execute(
                "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE CUSTOMER.ID = " + CustomerID);
    }

    @Override
    public List<Car> getRentedCars(int CustomerID) {
        try {
            List<Car> rented = new LinkedList<>();
            ResultSet resultSet = dbManager
                    .executeQuery(
                            "SELECT CAR.NAME AS NAME, COMPANY.NAME AS CNAME FROM CUSTOMER JOIN CAR ON " +
                                    "CUSTOMER.RENTED_CAR_ID = CAR.ID JOIN COMPANY ON CAR.COMPANY_ID = " +
                                    "COMPANY.ID WHERE CUSTOMER.ID = " + CustomerID);


            while (resultSet.next()) {
                rented.add(new Car(
                        resultSet.getString("NAME"),
                        resultSet.getString("CNAME")));
            }

            return rented;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
