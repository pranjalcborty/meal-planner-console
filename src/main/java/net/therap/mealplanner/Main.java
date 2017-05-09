package net.therap.mealplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author pranjal.chakraborty
 * @since 5/8/17
 */
public class Main {

    private static final String COMMA = ", ";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_AUTHENTICATION = "jdbc:mysql://localhost/mealplanner?"
            + "user=pranjal&password=password";
    private static final String CONNECTION_ESTABLISHED = "Connected";
    private static final String COUNTDAY_COLUMN = "countDay";
    private static final String DAYNAME_COLUMN = "dayName";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);

        Connection connect = DriverManager
                .getConnection(DATABASE_AUTHENTICATION);

        System.out.println(CONNECTION_ESTABLISHED);

        while(true) {
            System.out.println("************************");
            System.out.println("______Meal planner______");
            System.out.println("1: see the current meal plan");
            System.out.println("2: create your own");

            int inputFromUser = Integer.parseInt(new Scanner(System.in).nextLine());

            switch (inputFromUser) {
                case 1:
                    writeResultSetMenu(connect, connect.createStatement().executeQuery(
                            "select d.dayName, b.item1, b.item2, b.item3, l.item1, l.item2, l.item3 " +
                                    "from day d, meal m, breakfast b, lunch l " +
                                    "where m.dayid = d.countDay and m.breakfastid = b.id and m.lunchid = l.id"));
                    break;
                case 2:
                    customPlan(connect);
                    break;
                default:
                    connect.close();
                    break;

            }
        }
    }

    private static void writeResultSetMenu(Connection connect, ResultSet resultSet) throws SQLException {

        while (resultSet.next()) {
            String day = resultSet.getString(1);

            String breakfastItem1 = "";
            String breakfastItem2 = "";
            String breakfastItem3 = "";
            String lunchItem1 = "";
            String lunchItem2 = "";
            String lunchItem3 = "";

            ResultSet resultSet1 = (connect.createStatement().executeQuery("select itemName from items where id =" +
                    resultSet.getString(2)));
            if(resultSet1.next()) {
                breakfastItem1 = resultSet1.getString(1);
            }

            resultSet1 = (connect.createStatement().executeQuery("select itemName from items where id =" +
                    resultSet.getString(3)));
            if(resultSet1.next()) {
                breakfastItem2 = resultSet1.getString(1);
            }

            resultSet1 = (connect.createStatement().executeQuery("select itemName from items where id =" +
                    resultSet.getString(4)));
            if(resultSet1.next()) {
                breakfastItem3 = resultSet1.getString(1);
            }

            resultSet1 = (connect.createStatement().executeQuery("select itemName from items where id =" +
                    resultSet.getString(5)));
            if(resultSet1.next()) {
                lunchItem1 = resultSet1.getString(1);
            }

            resultSet1 = (connect.createStatement().executeQuery("select itemName from items where id =" +
                    resultSet.getString(6)));
            if(resultSet1.next()) {
                lunchItem2 = resultSet1.getString(1);
            }

            resultSet1 = (connect.createStatement().executeQuery("select itemName from items where id =" +
                    resultSet.getString(7)));
            if(resultSet1.next()) {
                lunchItem3 = resultSet1.getString(1);
            }

            System.out.println("Day: " + day);
            System.out.println("Breakfast: " + breakfastItem1 + COMMA + breakfastItem2 + COMMA + breakfastItem3);
            System.out.println("Lunch: " + lunchItem1 + COMMA + lunchItem2 + COMMA + lunchItem3);
        }
    }

    private static void writeResultSetDay(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int countDay = resultSet.getInt(COUNTDAY_COLUMN);
            String dayName = resultSet.getString(DAYNAME_COLUMN);
            System.out.print(countDay + "\t");
            System.out.println(dayName);
        }
    }

    private static void writeResultSetItems(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int itemId = resultSet.getInt("id");
            String itemName = resultSet.getString("itemName");
            System.out.print(itemId + "\t");
            System.out.println(itemName);
        }
    }

    private static void writeResultBreakfastLunch(ResultSet resultSet, Connection connect) throws SQLException {
        while (resultSet.next()) {
            int itemId = resultSet.getInt("id");
            String item1 = resultSet.getString("item1");
            String item2 = resultSet.getString("item2");
            String item3 = resultSet.getString("item3");
            System.out.print(itemId + "\t");

            ResultSet resultSet1 = connect.createStatement().executeQuery("select itemName from items where id=" +
                    item1 + " or id=" + item2 + " or id=" + item3);

            while (resultSet1.next()){
                System.out.print(resultSet1.getString(1)+", ");
            }
            System.out.println();
        }
    }

    private static void customPlan(Connection connect) throws SQLException {
        System.out.println("Enter the id of the day...");
        writeResultSetDay(connect.createStatement().executeQuery("select * from mealplanner.day"));
        int dayId = Integer.parseInt(new Scanner(System.in).nextLine());

        ResultSet resultSet = connect.createStatement().executeQuery("select dayName from mealplanner.day " +
                "where countDay=" + dayId);

        if (resultSet.next()) {
            System.out.println("The day you chose: " + resultSet.getString("dayName"));

            System.out.println("Breakfast or lunch?");
            System.out.println("1\tbreakfast");
            System.out.println("2\tlunch");
            int slot = Integer.parseInt(new Scanner(System.in).nextLine());

            boolean flag = false;
            switch (slot) {
                case 1:
                    String slotName = "breakfast";

                    ResultSet resultSet1 = connect.createStatement().executeQuery(
                            "select * from mealplanner.breakfast");

                    if (resultSet1.next()) {
                        System.out.println("You can choose from existing sets...");
                        writeResultBreakfastLunch(resultSet1, connect);
                        System.out.println("Enter [y/n]");
                        String opinion = new Scanner(System.in).nextLine();

                        if (opinion.equals("y")) {
                            System.out.println("Enter the id of the set");
                            ResultSet resultSet2 = connect.createStatement().executeQuery(
                                    "select * from meal where dayid=" + dayId);
                            int id = Integer.parseInt(new Scanner(System.in).nextLine());

                            if (resultSet2.next()) {
                                connect.createStatement().executeUpdate("update meal set breakfastid = " +
                                        id + " where dayid=" + dayId);
                            } else {
                                connect.createStatement().executeUpdate("insert into meal(id, dayid, breakfastid) " +
                                        "values(default, " + dayId + ", " + id + ")");
                            }

                            flag = true;
                        }
                    }

                    if (!flag) {
                        System.out.println("Choose id of 3 items, separate the ids with <space>");
                        writeResultSetItems(connect.createStatement().executeQuery("select * from mealplanner.items"));
                        String[] itemIds = new Scanner(System.in).nextLine().split(" ");

                        connect.createStatement().executeUpdate("insert into " + slotName +
                                "(id, item1, item2, item3) " + "values " + "(default, " + itemIds[0] + ", " +
                                itemIds[1] + ", " + itemIds[2] + ")");

                        ResultSet resultSetCheck = connect.createStatement().executeQuery("select * from meal " +
                                "where dayid=" + dayId);
                        ResultSet resultSet2 = connect.createStatement().executeQuery("select max(id) from breakfast");
                        if(resultSet2.next()) {
                            int id = resultSet2.getInt(1);

                            if(resultSetCheck.next()) {
                                connect.createStatement().executeUpdate("update meal set breakfastid = " +
                                        id + " where dayid=" + dayId);
                            }
                            else {
                                connect.createStatement().executeUpdate("insert into meal(id, dayid, breakfastid) " +
                                        "values(default, " + dayId + ", " + id + ")");
                            }
                        }
                    }

                    break;
                case 2:
                    slotName = "lunch";
                    resultSet1 = connect.createStatement().executeQuery("select * from mealplanner.lunch");

                    if (resultSet1.next()) {
                        System.out.println("You can choose from existing sets...");
                        writeResultBreakfastLunch(resultSet1, connect);
                        System.out.println("Enter [y/n]");
                        String opinion = new Scanner(System.in).nextLine();

                        if (opinion.equals("y")) {
                            System.out.println("Enter the id of the set");
                            ResultSet resultSet2 = connect.createStatement().executeQuery("select * from meal " +
                                    "where dayid=" + dayId);
                            int id = Integer.parseInt(new Scanner(System.in).nextLine());

                            if (resultSet2.next()) {
                                connect.createStatement().executeUpdate("update meal set lunchid = " + id +
                                        " where dayid=" + dayId);
                            } else {
                                connect.createStatement().executeUpdate("insert into meal(id, dayid, lunchid) " +
                                        "values(default, " + dayId + ", " + id + ")");
                            }

                            flag = true;
                        }
                    }

                    if (!flag) {
                        System.out.println("Choose id of 3 items, separate the ids with <space>");
                        writeResultSetItems(connect.createStatement().executeQuery("select * from mealplanner.items"));
                        String[] itemIds = new Scanner(System.in).nextLine().split(" ");

                        connect.createStatement().executeUpdate("insert into " + slotName +
                                "(id, item1, item2, item3) " + "values " + "(default, " + itemIds[0] + ", " +
                                itemIds[1] + ", " + itemIds[2] + ")");



                        ResultSet resultSetCheck = connect.createStatement().executeQuery("select * from meal " +
                                "where dayid=" + dayId);
                        ResultSet resultSet2 = connect.createStatement().executeQuery("select max(id) from lunch");

                        if(resultSet2.next()) {
                            int id = resultSet2.getInt(1);

                            if (resultSetCheck.next()) {
                                connect.createStatement().executeUpdate("update meal set lunchid = " + id +
                                        " where dayid=" + dayId);
                            }
                            else {
                                connect.createStatement().executeUpdate("insert into meal(id, dayid, lunchid) " +
                                        "values(default, " + dayId + ", " + id + ")");
                            }
                        }
                    }

                    break;
                default:
                    connect.close();
            }
        }
    }
}
