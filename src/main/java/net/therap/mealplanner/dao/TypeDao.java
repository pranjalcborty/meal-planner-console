package net.therap.mealplanner.dao;

import net.therap.mealplanner.enums.Type;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author pranjal.chakraborty
 * @since 5/11/17
 */
public class TypeDao {
    public static Type getType(ResultSet resultSet) throws SQLException {
        return Type.valueOf(resultSet.getString("meal_type"));
    }
}
