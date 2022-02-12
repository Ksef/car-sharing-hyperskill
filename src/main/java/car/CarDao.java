package car;

import java.util.List;

public interface CarDao {

    List<Car> getAllCars(int owner);

    List<Car> getAllNotRentedCars(int owner);

    void addCar(Car car, int owner);
}
