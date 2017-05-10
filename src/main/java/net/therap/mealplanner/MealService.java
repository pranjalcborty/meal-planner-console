package net.therap.mealplanner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class MealService {

    private static final String MSG1 = "*********Meal planner*********\n";
    private static final String MSG2 = "1\t See current meal plan\n";
    private static final String MSG3 = "2\t View current items\n";
    private static final String MSG4 = "3\t Create custom meal plan\n";
    private static final String MSG5 = "4\t Add item\n";
    private static final String MSG6 = "Insert item name";

    private MealDao mealDao;

    public MealService(){
        mealDao = new MealDao();
    }

    public void welcomeMessage(){
        System.out.println(MSG1 + MSG2 + MSG3 + MSG4 + MSG5);
    }

    public void showCurrentMealPlan(Connection connect){

    }

    public void createCustomPlan(Connection connect){

    }

    public void addNewItem(Connection connect) throws SQLException{
        System.out.println(MSG6);
        String itemName = new Scanner(System.in).nextLine();
        mealDao.addItem(itemName, connect);
    }

    public void viewItems(Connection connect) throws SQLException{
        List<Item> items = mealDao.generateItems(connect);

        for(Item item: items){
            System.out.println(item.getName());
        }
    }
}
