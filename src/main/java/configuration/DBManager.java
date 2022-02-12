package configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private final String DB_URL;
    private Connection connection;
    private Statement statement;
    private static final String DRIVER_JDBC = "org.h2.Driver";

    private static final String createCompaniesQuery = "CREATE TABLE IF NOT EXISTS COMPANY (" +
            "ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY" +
            ", NAME VARCHAR(50) NOT NULL UNIQUE " +
            ");";

    private static final String createCarsQuery = "CREATE TABLE IF NOT EXISTS CAR (" +
            "ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY" +
            ", NAME VARCHAR(255) NOT NULL UNIQUE" +
            ", COMPANY_ID INTEGER NOT NULL" +
            ", FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
            ");";

    private static final String createCustomerQuery = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
            "ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY" +
            ", NAME VARCHAR(255) NOT NULL UNIQUE" +
            ", RENTED_CAR_ID INTEGER" +
            ", FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
            ");";

    public DBManager(String dbFileName){
        this.DB_URL = "jdbc:h2:./src/main/java/db/" + dbFileName;;
        try{
            registerConnection();
            if(connection != null && !connection.isClosed()){
                statement = connection.createStatement();
                statement.executeUpdate(createCompaniesQuery);
                statement.executeUpdate(createCarsQuery);
                statement.executeUpdate(createCustomerQuery);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void registerConnection(){
        try {
            Class.forName(DRIVER_JDBC);
            this.connection = DriverManager.getConnection(DB_URL);
            this.connection.setAutoCommit(true);
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet executeQuery(String query){
        ResultSet result = null;
        try {
            this.statement = connection.createStatement();
            result =  statement.executeQuery(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }

    public void execute(String query){
        try {
            this.statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public DBManager getDatabase(){
        return this;
    }
}
