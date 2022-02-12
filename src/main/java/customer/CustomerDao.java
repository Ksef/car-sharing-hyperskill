package customer;

import car.Car;

import java.util.List;

public interface CustomerDao {

    List<Customer> getAllCustomers();

    void rentCar(int customerID, int carID);

    void returnCar(int CustomerID);

    List<Car> getRentedCars(int customerID);

    void addCustomer(Customer customer);
}
