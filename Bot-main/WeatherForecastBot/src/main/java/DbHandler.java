import org.sqlite.JDBC;

import java.sql.*;
import java.util.Properties;

public class dbHandler {

    Properties properties = new Properties();

    private Connection connection;

    public DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        connection = DriverManager.getConnection(properties.getProperty("db.path"));
    }

    public void saveTemperature(Temperature temperature){
        try(Statement statement = this.connection.createStatement()) {
            statement.execute("INSERT INTO temperature(min, max, date) " +
                    "VALUES(" + temperature.getMin() + ", " + temperature.getMax() + ", 'test')");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public Temperature getTemperature(){
        Temperature temperature = null;

        try(Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT min, max, date FROM temperature"); //строка

            while (resultSet.next()) {
                temperature = new Temperature(
                        resultSet.getDouble("min"),
                        resultSet.getDouble("max")
                );
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return temperature;
    }

}

