package net.therap.mealplanner.dao;

import net.therap.mealplanner.enums.Day;
import net.therap.mealplanner.helper.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author pranjal.chakraborty
 * @since 5/11/17
 */
public class DayDao {
    private static final String FIND_DAY = "SELECT * FROM meals WHERE meal_id = ?";

    public static Day getDay(Connection connect, int daySlot) throws SQLException {

        PreparedStatement preparedStatement = connect.prepareStatement(FIND_DAY);
        preparedStatement.setInt(1, daySlot);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            return Day.valueOf(resultSet.getString("day"));
        }

        return null;
    }
}
