import org.sqlite.JDBC;
import java.sql.*;

public class DbHandler {

    private static final String DB_PATH = "jdbc:sqlite:C:\\Users\\WDS108663\\Downloads\\Weatherforecastbot\\untitled5\\src\\main\\resources\\forecast.db";
    final Connection connection;

    public DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        connection = DriverManager.getConnection(DB_PATH);
    }

    public void addTemperature(Temperature temperature){
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
