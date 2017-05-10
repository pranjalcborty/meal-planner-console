package net.therap.mealplanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class MealDao {
    private static final String ADD_ITEM_STRING = "INSERT INTO items(itemid, itemname) VALUES (default, ?)";
    private static final String VIEW_ITEMS_STRING = "SELECT * FROM items";
    private static final String VIEW_MEALS_STRING = "SELECT * FROM meals";
    private static final String GET_ITEMID_STRING = "SELECT itemid FROM mealitem WHERE mealid = ?";
    private static final String GET_ITEMNAME_STRING = "SELECT itemname FROM items WHERE itemid = ?";

    private static final String ADD_MEALS_STRING = "INSERT INTO meals(mealid, mealtype, day) VALUES (default, ?, ?)";

    public void addItem(String itemName, Connection connect) throws SQLException{
        PreparedStatement preparedStatement = connect.prepareStatement(ADD_ITEM_STRING);
        preparedStatement.setString(1, itemName);
        preparedStatement.executeUpdate();
    }

    public List<Item> generateItems(Connection connect) throws SQLException{
        List<Item> items = new ArrayList<>();
        ResultSet resultSet = connect.prepareStatement(VIEW_ITEMS_STRING).executeQuery();

        while(resultSet.next()){
            items.add(new Item(resultSet.getInt("itemid"),resultSet.getString("itemname")));
        }

        return items;
    }

    public List<Meal> currentMeals(Connection connect) throws SQLException{
        List<Meal> meals = new ArrayList<>();
        ResultSet resultSet = connect.prepareStatement(VIEW_MEALS_STRING).executeQuery();

        while (resultSet.next()){
            int daySlot = resultSet.getInt("mealid");
            List<Item> items = new ArrayList<>();

            PreparedStatement preparedStatement = connect.prepareStatement(GET_ITEMID_STRING);
            preparedStatement.setInt(1, daySlot);
            ResultSet resultSet1 = preparedStatement.executeQuery();

            while (resultSet1.next()){
                PreparedStatement preparedStatement1 = connect.prepareStatement(GET_ITEMNAME_STRING);
                preparedStatement.setInt(1, resultSet1.getInt("itemid"));
                ResultSet resultSet2 = preparedStatement.executeQuery();

                if (resultSet2.next()){
                    items.add(new Item(resultSet1.getInt("itemid"),resultSet2.getString("itemname")));
                }
            }

            meals.add(new Meal(resultSet.getInt("mealid"),
                    Day.valueOf(resultSet.getString("day")),
                    Slot.valueOf(resultSet.getString("mealtype")),items));
        }

        return meals;
    }
}
