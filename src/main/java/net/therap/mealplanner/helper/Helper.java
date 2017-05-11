package net.therap.mealplanner.helper;


import net.therap.mealplanner.enums.Option;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class Helper {

    private static final Map<Integer, Option> optionMap = new HashMap<>();
    private static final String DATABASE_AUTHENTICATION = "jdbc:mysql://localhost/meal_planner?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    static {
        optionMap.put(1, Option.VIEW_PLAN);
        optionMap.put(2, Option.VIEW_ITEMS);
        optionMap.put(3, Option.ADD_PLAN);
        optionMap.put(4, Option.ADD_ITEM);
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_AUTHENTICATION, USER, PASSWORD);
    }

    public static Option getOption(int input) {
        if (optionMap.containsKey(input)) {
            return optionMap.get(input);
        }

        return Option.INVALID;
    }
}
