package net.therap.mealplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class ConnectionService {
    private static final String DATABASE_AUTHENTICATION = "jdbc:mysql://localhost/mealplanner?"
            + "user=pranjal&password=password";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_AUTHENTICATION);
    }
}
