package roles;

import car.Car;
import car.CarDao;
import car.CarDaoImpl;
import company.Company;
import company.CompanyDao;
import company.CompanyDaoImpl;
import configuration.DBManager;
import customer.Customer;
import customer.CustomerDao;
import customer.CustomerDaoImpl;

import java.util.List;
import java.util.Scanner;

public class CustomerRole {

    Scanner scanner;
    CustomerDao customerDao;
    DBManager dbManager;
    boolean repeatCustomer;

    public CustomerRole(DBManager dbManager) {
        scanner = new Scanner(System.in);
        customerDao = new CustomerDaoImpl(dbManager.getDatabase());
        this.dbManager = dbManager;
    }

    public void customerRoleMenu() {
        List<Customer> customerList = customerDao.getAllCustomers();
        if (customerList.isEmpty()) {
            System.out.println("The customer list is empty!");
        } else {
            displayCustomers(customerList);
        }
    }

    private void displayCustomers(List<Customer> customerList) {
        int orderCustomerNumber = 0;
        System.out.println("Choose a customer: ");
        for (Customer customer : customerList) {
            System.out.println(++orderCustomerNumber + ". " + customer.getName());
        }
        System.out.println("0. Main menu");
        int menuItem = scanner.nextInt();
        scanner.nextLine();
        if (menuItem != 0) {
            displayChosenCustomer(customerList, menuItem);
        }
    }

    private void displayChosenCustomer(List<Customer> customerList, int menuItem) {
        repeatCustomer = true;
        while (repeatCustomer) {
            customersCarMenu(customerList, menuItem);
        }
    }

    private void customersCarMenu(List<Customer> customerList, int menuItem) {
        System.out.println("""
                            1. Rent a car
                            2. Return a rented car
                            3. My rented car
                            0. Main Menu""");
        String operationNumber = scanner.nextLine();
        switch (operationNumber) {
            case "1" -> suitableForRentCars(customerList, menuItem);
            case "2" -> returnRentedCar(customerList, menuItem);
            case "3" -> displayCustomersRentedCar(customerList, menuItem);
            default -> repeatCustomer = false;
        }
    }

    private void suitableForRentCars(List<Customer> customerList, int customerNumber) {
        List<Car> rentCars = customerDao.getRentedCars(customerList.get(customerNumber - 1).getID());
        if (rentCars.isEmpty()) {
            displayCompanyList(customerList, customerNumber);
        } else {
            System.out.println("You've already rented a car!");
        }
    }

    private void displayCompanyList(List<Customer> customerList, int customerNumber) {
        CompanyDao companyDaoMethods = new CompanyDaoImpl(dbManager.getDatabase());
        List<Company> companyList = companyDaoMethods.getAllCompanies();
        if (companyList.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            chooseCarSharingCompany(companyList, customerList, customerNumber);
        }
    }

    private void chooseCarSharingCompany(List<Company> companyList, List<Customer> customerList, int customerNumber) {
        int orderCompanyNumber = 0;
        System.out.println("Choose company: ");
        for (Company company : companyList) {
            System.out.println(++orderCompanyNumber + ". " + company.getName());
        }
        System.out.println("0. Main menu\n");
        displayCompanyCarList(companyList, customerList, customerNumber);
    }

    private void displayCompanyCarList(List<Company> companyList, List<Customer> customerList, int customerNumber) {
        int companyNum = scanner.nextInt();
        scanner.nextLine();

        CarDao carDaoCust = new CarDaoImpl(dbManager.getDatabase());
        List<Car> cars = carDaoCust.getAllNotRentedCars(companyList.get(companyNum - 1).getID());
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            chooseCar(cars, customerList, customerNumber);
        }
    }

    private void chooseCar(List<Car> cars, List<Customer> customerList, int customerNumber) {
        int orderCarNumber = 0;
        System.out.println("Choose a car:");
        for (Car car : cars) {
            System.out.println(++orderCarNumber + ". " + car.getName());
        }
        System.out.println("0. Main menu\n");
        int carNumber = scanner.nextInt();
        scanner.nextLine();
        if (carNumber == 0) {
            repeatCustomer = false;
        } else {
            customerDao.rentCar(customerList.get(customerNumber - 1).getID(), cars.get(carNumber - 1).getID());
            System.out.println("You rented '" + cars.get(carNumber - 1).getName() + "'");
        }
    }

    private void returnRentedCar(List<Customer> customerList, int customerNumber) {
        List<Car> rented = customerDao.getRentedCars(customerList.get(customerNumber - 1).getID());
        if (rented.isEmpty()) {
            System.out.println("You didn't rent a car!");
        } else {
            customerDao.returnCar(customerList.get(customerNumber - 1).getID());
            System.out.println("You've returned a rented car!");
        }
    }

    private void displayCustomersRentedCar(List<Customer> customerList, int customerNumber) {
        System.out.println("My rented car:");
        List<Car> rentedCars = customerDao.getRentedCars(customerList.get(customerNumber - 1).getID());
        if (rentedCars.isEmpty()) {
            System.out.println("You didn't rent a car!");
        } else {
            displayRentedCarsList(rentedCars);
        }
    }

     private void displayRentedCarsList(List<Car> rentedCars) {
         for (Car car : rentedCars) {
             System.out.println(car.getName() + "\nCOMPANY:\n" + car.getCompanyName());
         }
     }
}
