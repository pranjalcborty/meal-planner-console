package net.therap.mealplanner.dao;

import net.therap.mealplanner.domains.Item;
import net.therap.mealplanner.helper.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pranjal.chakraborty
 * @since 5/11/17
 */
public class ItemDao {

    private static final String ADD_ITEM_STRING = "INSERT IGNORE INTO items(item_id, item_name) VALUES (default, ?)";
    private static final String VIEW_ITEMS_STRING = "SELECT * FROM items";
    private static final String GET_ITEMID_STRING = "SELECT item_id FROM meal_item WHERE meal_id = ?";
    private static final String GET_ITEMNAME_STRING = "SELECT item_name FROM items WHERE item_id = ?";

    public static List<Item> getItems(int daySlot) throws SQLException {
        Connection connect = Helper.connect();
        List<Item> items = new ArrayList<>();

        PreparedStatement preparedStatement = connect.prepareStatement(GET_ITEMID_STRING);
        preparedStatement.setInt(1, daySlot);
        ResultSet mealResultSet = preparedStatement.executeQuery();

        while (mealResultSet.next()) {
            PreparedStatement preparedStatement1 = connect.prepareStatement(GET_ITEMNAME_STRING);
            preparedStatement1.setInt(1, mealResultSet.getInt("item_id"));
            ResultSet resultSet2 = preparedStatement1.executeQuery();

            if (resultSet2.next()) {
                items.add(new Item(mealResultSet.getInt("item_id"), resultSet2.getString("item_name")));
            }
        }

        connect.close();
        return items;
    }

    public void addItem(String itemName) throws SQLException {
        Connection connect = Helper.connect();

        PreparedStatement preparedStatement = connect.prepareStatement(ADD_ITEM_STRING);
        preparedStatement.setString(1, itemName);
        preparedStatement.executeUpdate();

        connect.close();
    }

    public List<Item> generateItems() throws SQLException {
        Connection connect = Helper.connect();
        List<Item> items = new ArrayList<>();
        ResultSet resultSet = connect.prepareStatement(VIEW_ITEMS_STRING).executeQuery();

        while (resultSet.next()) {
            items.add(new Item(resultSet.getInt("item_id"), resultSet.getString("item_name")));
        }

        connect.close();
        return items;
    }
}
