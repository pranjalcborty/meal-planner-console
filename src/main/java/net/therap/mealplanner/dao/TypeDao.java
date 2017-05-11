package net.therap.mealplanner.dao;

import net.therap.mealplanner.enums.Type;
import net.therap.mealplanner.helper.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author pranjal.chakraborty
 * @since 5/11/17
 */
public class TypeDao {
    private static final String FIND_DAY = "SELECT * FROM meals WHERE meal_id = ?";

    public static Type getType(int daySlot) throws SQLException {

        Connection connect = Helper.connect();
        PreparedStatement preparedStatement = connect.prepareStatement(FIND_DAY);
        preparedStatement.setInt(1, daySlot);
        ResultSet resultSet = preparedStatement.executeQuery();

        Type type = null;
        if (resultSet.next()) {
            type = Type.valueOf(resultSet.getString("meal_type"));
        }

        connect.close();
        return type;
    }
}
