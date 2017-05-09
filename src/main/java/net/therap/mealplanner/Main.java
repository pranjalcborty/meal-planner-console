package net.therap.mealplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

/**
 * @author pranjal.chakraborty
 * @since 5/8/17
 */
public class Main {

    private static final String COMMA = ", ";

    public static void main (String [] args) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver found");
        Connection connect = DriverManager
                .getConnection("jdbc:mysql://localhost/mealplanner?"
                        + "user=pranjal&password=password");
        System.out.println("Connected");

        System.out.println("______Meal planner______");
        System.out.println("1: see the current meal plan");
        System.out.println("2: create your own");
        int inputFromUser = Integer.parseInt(new Scanner(System.in).nextLine());

        switch(inputFromUser){
            case 1: writeResultSetMenu(connect.createStatement().executeQuery(
                    "select a.dayName, d.itemName, e.itemName, f.itemName, g.itemName, h.itemName, i.itemName " +
                    "from day a,breakfast b, lunch l, meal c, items d, items e, items f, items g, items h, items i " +
                    "where a.countDay=c.dayId and b.item1 = d.id and b.item2 = e.id and b.item3 = f.id and" +
                    " l.item1 = g.id and l.item2 = h.id and l.item3 = i.id"));
                break;
            case 2: writeResultSetItems(connect.createStatement().executeQuery("select * from mealplanner.items"));
                break;
            default: connect.close();

        }
    }

    private static void writeResultSetMenu(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set

        while (resultSet.next()) {
            String day = resultSet.getString(1);

            String breakfastItem1 = resultSet.getString(2);
            String breakfastItem2 = resultSet.getString(3);
            String breakfastItem3 = resultSet.getString(4);

            String lunchItem1 = resultSet.getString(5);
            String lunchItem2 = resultSet.getString(6);
            String lunchItem3 = resultSet.getString(7);

            System.out.println("Day: "+ day);
            System.out.println("Breakfast: "+ breakfastItem1 + COMMA + breakfastItem2 + COMMA + breakfastItem3);
            System.out.println("Lunch: "+ lunchItem1 + COMMA + lunchItem2 + COMMA + lunchItem3);
        }
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
