package net.therap.mealplanner.dao;

import net.therap.mealplanner.domains.Item;
import net.therap.mealplanner.domains.Meal;
import net.therap.mealplanner.helper.Helper;

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
    private static final String ADD_ITEM_TO_MEAL = "INSERT IGNORE INTO meal_item(meal_id, item_id) VALUES (?, ?)";

    public List<Meal> getMeals() throws SQLException {
        Connection connect = Helper.connect();

        List<Meal> meals = new ArrayList<>();
        ResultSet resultSet = connect.prepareStatement(VIEW_MEALS_STRING).executeQuery();

        while (resultSet.next()) {
            int daySlot = resultSet.getInt("meal_id");
            List<Item> items = ItemDao.getItems(daySlot);

            meals.add(new Meal(resultSet.getInt("meal_id"),
                    DayDao.getDay(daySlot), TypeDao.getType(daySlot), items));
        }

        connect.close();
        return meals;
    }

    public void addItemToMeal(int itemId, int mealId) throws SQLException {
        Connection connect = Helper.connect();

        PreparedStatement preparedStatement = connect.prepareStatement(ADD_ITEM_TO_MEAL);
        preparedStatement.setInt(1, mealId);
        preparedStatement.setInt(2, itemId);
        preparedStatement.executeUpdate();

        connect.close();
    }
}
