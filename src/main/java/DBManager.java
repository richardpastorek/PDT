import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Richard on 21. 10. 2016.
 */
public class  DBManager {

    public static Connection getConnection() throws SQLException {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/projekt", "postgres",
                    "pdt");
        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;

        }

        System.out.println("Connected to database");
        return connection;
    }



}
