package car;

public class Car {
    private int id;
    private String name;
    private String companyName;

    public Car(String name) {
        this.name = name;
    }

    public Car(String name, String companyName) {
        this.name = name;
        this.companyName = companyName;
    }

    public Car(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }
}
