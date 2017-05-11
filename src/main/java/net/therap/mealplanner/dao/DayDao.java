package net.therap.mealplanner.dao;

import net.therap.mealplanner.enums.Day;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author pranjal.chakraborty
 * @since 5/11/17
 */
public class DayDao {
    public static Day getDay(ResultSet resultSet) throws SQLException {
        return Day.valueOf(resultSet.getString("day"));
    }
}
