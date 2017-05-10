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

    public void addItem(String itemName, Connection connect) throws SQLException{
        PreparedStatement addStatement = connect.prepareStatement(ADD_ITEM_STRING);
        addStatement.setString(1, itemName);
        addStatement.executeUpdate();
    }

    public List<Item> generateItems(Connection connect) throws SQLException{
        List<Item> items = new ArrayList<>();
        ResultSet resultSet = connect.prepareStatement(VIEW_ITEMS_STRING).executeQuery();

        while(resultSet.next()){
            items.add(new Item(resultSet.getInt("itemid"),resultSet.getString("itemname")));
        }

        return items;
    }
}
