package net.therap.mealplanner.dao;

import net.therap.mealplanner.enums.Day;
import net.therap.mealplanner.domains.Item;
import net.therap.mealplanner.domains.Meal;
import net.therap.mealplanner.enums.Type;

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

    private static final String VIEW_MEALS_STRING = "SELECT * FROM meals";
    private static final String GET_ITEMID_STRING = "SELECT item_id FROM meal_item WHERE meal_id = ?";
    private static final String GET_ITEMNAME_STRING = "SELECT item_name FROM items WHERE item_id = ?";
    private static final String ADD_ITEM_TO_MEAL = "INSERT IGNORE INTO meal_item(meal_id, item_id) VALUES (?, ?)";

    public List<Meal> currentMeals(Connection connect) throws SQLException {
        List<Meal> meals = new ArrayList<>();
        ResultSet resultSet = connect.prepareStatement(VIEW_MEALS_STRING).executeQuery();

        while (resultSet.next()) {
            int daySlot = resultSet.getInt("meal_id");
            List<Item> items = new ArrayList<>();

            PreparedStatement preparedStatement = connect.prepareStatement(GET_ITEMID_STRING);
            preparedStatement.setInt(1, daySlot);
            ResultSet resultSet1 = preparedStatement.executeQuery();

            while (resultSet1.next()) {
                PreparedStatement preparedStatement1 = connect.prepareStatement(GET_ITEMNAME_STRING);
                preparedStatement1.setInt(1, resultSet1.getInt("item_id"));
                ResultSet resultSet2 = preparedStatement1.executeQuery();

                if (resultSet2.next()) {
                    items.add(new Item(resultSet1.getInt("item_id"), resultSet2.getString("item_name")));
                }
            }

            meals.add(new Meal(resultSet.getInt("meal_id"),
                    Day.valueOf(resultSet.getString("day")),
                    Type.valueOf(resultSet.getString("meal_type")), items));
        }

        return meals;
    }

    public void addItemToMeal(Connection connect, int itemId, int mealId) throws SQLException {
        PreparedStatement preparedStatement = connect.prepareStatement(ADD_ITEM_TO_MEAL);
        preparedStatement.setInt(1, mealId);
        preparedStatement.setInt(2, itemId);
        preparedStatement.executeUpdate();
    }
}
