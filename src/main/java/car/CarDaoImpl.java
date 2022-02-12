package car;

import configuration.DBManager;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    DBManager dbManager;

    public CarDaoImpl (DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Car> getAllCars(int owner) {
        try {
            List<Car> cars = new LinkedList<>();

            ResultSet result = dbManager
                    .executeQuery("SELECT * FROM CAR WHERE COMPANY_ID = " + owner + " ORDER BY ID");

            while(result.next()){
                cars.add(new Car(result.getInt("ID"),result.getString("NAME")));
            }

            return cars;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Car> getAllNotRentedCars(int owner) {
        try {
            List<Car> cars = new LinkedList<>();

            ResultSet result = dbManager
                    .executeQuery("SELECT CAR.* FROM CAR WHERE CAR.COMPANY_ID = " + owner + "AND CAR.ID NOT IN " +
                            " (SELECT CUSTOMER.RENTED_CAR_ID FROM CUSTOMER WHERE CUSTOMER.RENTED_CAR_ID IS NOT NULL) ORDER BY CAR.ID");

            while(result.next()){
                cars.add(new Car(result.getInt("ID"),result.getString("NAME")));
            }

            return cars;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void addCar(Car car, int owner) {
        dbManager.execute("INSERT INTO CAR(NAME,COMPANY_ID) VALUES ('" + car.getName() +"','" + owner-- + "');");
    }
}
