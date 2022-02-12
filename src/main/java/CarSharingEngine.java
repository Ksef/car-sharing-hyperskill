import configuration.DBManager;
import customer.Customer;
import customer.CustomerDao;
import customer.CustomerDaoImpl;
import roles.CustomerRole;
import roles.ManagerRole;

import java.util.Scanner;

public class CarSharingEngine {

    int menuItem;
    boolean repeatMenu;
    String dbFileName;
    DBManager dbManager;
    CustomerDao customerDao;
    Scanner scanner;
    ManagerRole managerRole;
    CustomerRole customerRole;

    public CarSharingEngine(String[] args) {
        argumentsManager(args);
        dbManager = new DBManager(dbFileName);
        scanner = new Scanner(System.in);
        managerRole = new ManagerRole(dbManager);
        customerRole = new CustomerRole(dbManager);
        customerDao = new CustomerDaoImpl(dbManager.getDatabase());
    }

    public void argumentsManager(String[] args) {
        try {
            if (args.length == 2 && args[0].equals("-databaseFileName")) {
                dbFileName = args[1];
            } else if (args.length == 1 && args[0].equals("-databaseFileName")) {
                dbFileName = "cars";
                System.out.println("""
                        You didn't specify a second argument.
                        Now DB name has default - 'cars'.""");
            } else {
                System.err.println("""
                        APPLICATION CLOSED!
                        You used invalid arguments.
                        Please use '-databaseFileName cars'""");
                System.exit(0);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void applicationMenu() {
        String mainMenu = """
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit""";
        repeatMenu = true;
        while (repeatMenu) {
            System.out.println(mainMenu);
            menuItem = scanner.nextInt();
            scanner.nextLine();
            switch (menuItem) {
                case 1 -> managerRole.managerRoleMenu();
                case 2 -> customerRole.customerRoleMenu();
                case 3 -> createNewCustomer();
                default -> repeatMenu = false;
            }
        }
    }

    private void createNewCustomer() {
        System.out.print("Enter the customer name: ");
        String name = scanner.nextLine();
        customerDao.addCustomer(new Customer(name));
    }
}
