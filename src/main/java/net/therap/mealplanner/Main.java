package net.therap.mealplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
/**
 * @author pranjal.chakraborty
 * @since 5/8/17
 */
public class Main {
    public static void main (String [] args) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver found");
        Connection connect = DriverManager
                .getConnection("jdbc:mysql://localhost/mealplanner?"
                        + "user=pranjal&password=password");
        System.out.println("Connected");

        System.out.println("______Day______");
        writeResultSetDay(connect.createStatement().executeQuery("select * from mealplanner.day"));

        System.out.println("______Items______");
        writeResultSetItems(connect.createStatement().executeQuery("select * from mealplanner.items"));
        connect.close();
    }

    private static void writeResultSetDay(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        System.out.println("dayCount\tdayName");

        while (resultSet.next()) {
            int countDay = resultSet.getInt("countDay");
            String dayName = resultSet.getString("dayName");
            System.out.print(countDay+"\t\t\t");
            System.out.println(dayName);
        }
    }

    private static void writeResultSetItems(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        System.out.println("itemId\titemName");

        while (resultSet.next()) {
            int itemId = resultSet.getInt("id");
            String itemName = resultSet.getString("itemName");
            System.out.print(itemId+"\t\t");
            System.out.println(itemName);
        }
    }
}
