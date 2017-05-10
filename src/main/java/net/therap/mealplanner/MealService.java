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
    private static final String DAY = "Day: ";
    private static final String SLOT = " Slot: ";

    private MealDao mealDao;

    public MealService(){
        mealDao = new MealDao();
    }

    public void welcomeMessage(){
        System.out.println(MSG1 + MSG2 + MSG3 + MSG4 + MSG5);
    }

    public void showCurrentMealPlan(Connection connect) throws SQLException{
        printMeal(mealDao.currentMeals(connect));
    }

    public void createCustomPlan(Connection connect) throws SQLException{
        System.out.println("Desired slot to assign item...");
        showCurrentMealPlan(connect);


    }

    public void addNewItem(Connection connect) throws SQLException{
        System.out.println(MSG6);
        String itemName = new Scanner(System.in).nextLine();
        mealDao.addItem(itemName, connect);
    }

    public void viewItems(Connection connect) throws SQLException{
        List<Item> items = mealDao.generateItems(connect);
        printItem(items);
    }

    public void printItem(List<Item> list){
        for(Item item: list){
            System.out.println(item.getName());
        }
    }

    public void printMeal(List<Meal> list){
        for(Meal item: list){
            System.out.println(item.getId() + DAY + item.getDay().name() + SLOT + item.getSlot().name());
            printItem(item.getItems());
        }
    }
}
