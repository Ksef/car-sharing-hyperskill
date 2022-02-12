package roles;

import car.Car;
import car.CarDao;
import car.CarDaoImpl;
import company.Company;
import company.CompanyDao;
import company.CompanyDaoImpl;
import configuration.DBManager;

import java.util.List;
import java.util.Scanner;

public class ManagerRole {

    private final DBManager dbManager;
    private final Scanner scanner;
    private final CompanyDao companyDao;
    private boolean repeatCars;

    public ManagerRole(DBManager dbManager) {
        this.dbManager = dbManager;
        scanner = new Scanner(System.in);
        companyDao = new CompanyDaoImpl(dbManager.getDatabase());
    }

    public void managerRoleMenu() {
        boolean repeat = true;
        while (repeat) {
            System.out.println("""
                    1. Company list
                    2. Create a company
                    0. Back""");
            String menuItem = scanner.nextLine();
            switch (menuItem) {
                case "1" -> displayCompanyListOrMessage();
                case "2" -> createNewCompany();
                default -> repeat = false;
            }
        }
    }

    private void displayCompanyListOrMessage() {
        List<Company> companyList = companyDao.getAllCompanies();
        if (companyList.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            chooseCompanyFromList(companyList);
        }
    }

    private void chooseCompanyFromList(List<Company> companyList) {
        int orderCompanyNumber = 0;
        System.out.println("Choose company: ");
        for (Company company : companyList) {
            System.out.println(++orderCompanyNumber + ". " + company.getName());
        }
        System.out.println("0. Back\n");
        displayChosenCompanyCars(companyList);
    }

    private void displayChosenCompanyCars(List<Company> companyList) {
        int menuCompanyItem = scanner.nextInt();
        scanner.nextLine();
        if (menuCompanyItem != 0) {
            displayChosenCompanyCarsMenu(menuCompanyItem, companyList);
        }
    }

    private void displayChosenCompanyCarsMenu(int menuCompanyItem, List<Company> companyList) {
        repeatCars = true;
        System.out.println("""
                    1. Car list
                    2. Create a car
                    0. Back""");
        while (repeatCars) {
            carsMenu(menuCompanyItem, companyList);
        }
    }

    private void carsMenu(int menuCompanyItem, List<Company> companyList) {
        String operationNumber = scanner.nextLine();
        CarDao carDao = new CarDaoImpl(dbManager.getDatabase());
        switch (operationNumber) {
            case "1" -> displayCarListOrMessage(carDao, companyList, menuCompanyItem);
            case "2" -> createNewCarInChosenCompany(carDao, companyList, menuCompanyItem);
            default -> repeatCars = false;
        }
    }

    private void displayCarListOrMessage(CarDao carDao, List<Company> companyList, int companyNum) {
        List<Car> carList = carDao.getAllCars(companyList.get(companyNum - 1).getID());
        if (carList == null || carList.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            displayCarList(carList);
        }
    }

    private void displayCarList(List<Car> carList) {
        System.out.println("Cars list:");
        for (Car car : carList) {
            System.out.println("- " + car.getName());
        }
        System.out.println("0. Back to manager menu");
    }

    private void createNewCarInChosenCompany(CarDao carDao, List<Company> companyList, int companyNum) {
        System.out.println("Enter the car name: ");
        String carName = scanner.nextLine();
        carDao.addCar(new Car(carName), companyList.get(companyNum - 1).getID());
        System.out.println("Press the enter to add car: " + carName);
    }

    private void createNewCompany() {
        System.out.print("Enter the company name: ");
        String name = scanner.nextLine();
        companyDao.addCompany(new Company(name));
    }
}
