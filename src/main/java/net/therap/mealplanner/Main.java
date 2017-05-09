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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver found");
        Connection connect = DriverManager
                .getConnection("jdbc:mysql://localhost/mealplanner?"
                        + "user=pranjal&password=password");
        System.out.println("Connected");

        while(true) {

            System.out.println("______Meal planner______");
            System.out.println("1: see the current meal plan");
            System.out.println("2: create your own");
            int inputFromUser = Integer.parseInt(new Scanner(System.in).nextLine());

            switch (inputFromUser) {
                case 1:
                    writeResultSetMenu(connect.createStatement().executeQuery(
                            "select a.dayName, d.itemName, e.itemName, f.itemName, g.itemName, h.itemName, i.itemName " +
                                    "from day a,breakfast b, lunch l, meal c, items d, items e, items f, items g, items h, items i " +
                                    "where a.countDay=c.dayId and b.item1 = d.id and b.item2 = e.id and b.item3 = f.id and" +
                                    " l.item1 = g.id and l.item2 = h.id and l.item3 = i.id and c.breakfastid = b.id and c.lunchid = l.id"));
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

            System.out.println("Day: " + day);
            System.out.println("Breakfast: " + breakfastItem1 + COMMA + breakfastItem2 + COMMA + breakfastItem3);
            System.out.println("Lunch: " + lunchItem1 + COMMA + lunchItem2 + COMMA + lunchItem3);
        }
    }

    private static void writeResultSetDay(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int countDay = resultSet.getInt("countDay");
            String dayName = resultSet.getString("dayName");
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

            ResultSet resultSet1 = connect.createStatement().executeQuery("select itemName from items where id=" + item1 + " or id=" + item2 + " or id=" + item3);

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

        ResultSet resultSet = connect.createStatement().executeQuery("select dayName from mealplanner.day where countDay=" + dayId);

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

                    ResultSet resultSet1 = connect.createStatement().executeQuery("select * from mealplanner.breakfast");

                    if (resultSet1.next()) {
                        System.out.println("You can choose from existing sets...");
                        writeResultBreakfastLunch(resultSet1, connect);
                        System.out.println("Enter [y/n]");
                        String opinion = new Scanner(System.in).nextLine();

                        if (opinion.equals("y")) {
                            System.out.println("Enter the id of the set");
                            ResultSet resultSet2 = connect.createStatement().executeQuery("select * from meal where dayid=" + dayId);
                            int id = Integer.parseInt(new Scanner(System.in).nextLine());

                            if (resultSet2.next()) {
                                connect.createStatement().executeUpdate("update meal set breakfastid = " + id + " where dayid=" + dayId);
                            } else {
                                connect.createStatement().executeUpdate("insert into meal(id, dayid, breakfastid) values(default, " + dayId + ", " + id + ")");
                            }

                            flag = true;
                        }
                    }

                    if (!flag) {
                        System.out.println("Choose id of 3 items, separate the ids with <space>");
                        writeResultSetItems(connect.createStatement().executeQuery("select * from mealplanner.items"));
                        String[] itemIds = new Scanner(System.in).nextLine().split(" ");

                        connect.createStatement().executeUpdate("insert into " + slotName +
                                "(id, item1, item2, item3) " + "values " + "(default, " + itemIds[0] + ", " + itemIds[1] + ", " + itemIds[2] + ")");

                        ResultSet resultSet2 = connect.createStatement().executeQuery("select max(id) from breakfast");
                        if(resultSet2.next()) {
                            int id = resultSet2.getInt(1);
                            connect.createStatement().executeUpdate("insert into meal(id, dayid, breakfastid) values(default, " + dayId + ", " + id + ")");
                            System.out.println("Success");
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
                            ResultSet resultSet2 = connect.createStatement().executeQuery("select * from meal where dayid=" + dayId);
                            int id = Integer.parseInt(new Scanner(System.in).nextLine());

                            if (resultSet2.next()) {
                                connect.createStatement().executeUpdate("update meal set lunchid = " + id + " where dayid=" + dayId);
                            } else {
                                connect.createStatement().executeUpdate("insert into meal(id, dayid, lunchid) values(default, " + dayId + ", " + id + ")");
                            }

                            flag = true;
                        }
                    }

                    if (!flag) {
                        System.out.println("Choose id of 3 items, separate the ids with <space>");
                        writeResultSetItems(connect.createStatement().executeQuery("select * from mealplanner.items"));
                        String[] itemIds = new Scanner(System.in).nextLine().split(" ");

                        connect.createStatement().executeUpdate("insert into " + slotName +
                                "(id, item1, item2, item3) " + "values " + "(default, " + itemIds[0] + ", " + itemIds[1] + ", " + itemIds[2] + ")");

                        ResultSet resultSet2 = connect.createStatement().executeQuery("select max(id) from lunch");
                        if(resultSet2.next()) {
                            int id = resultSet2.getInt(1);
                            connect.createStatement().executeUpdate("insert into meal(id, dayid, lunchid) values(default, " + dayId + ", " + id + ")");
                        }
                    }

                    break;
                default:
                    connect.close();
            }
        }
    }
}
