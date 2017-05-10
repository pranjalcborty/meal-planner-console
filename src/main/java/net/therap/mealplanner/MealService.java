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
    private static final String MSG7 = "Desired slot to assign item...";
    private static final String MSG8 = "Enter slot id";
    private static final String MSG9 = "Currently available items...";
    private static final String MSG10 = "Enter desired food ids, separated by space";
    private static final String SPACE = " ";
    private static final String TAB = "\t ";
    private static final String DAY = "Day: ";
    private static final String SLOT = " Slot: ";
    private static final String COMMA = ", ";

    private MealDao mealDao;

    public MealService() {
        mealDao = new MealDao();
    }

    public void welcomeMessage() {
        System.out.println(MSG1 + MSG2 + MSG3 + MSG4 + MSG5);
    }

    public void showCurrentMealPlan(Connection connect, boolean flag) throws SQLException {
        printMeal(mealDao.currentMeals(connect), flag);
    }

    public void createCustomPlan(Connection connect) throws SQLException {
        System.out.println(MSG7);
        showCurrentMealPlan(connect, false);
        System.out.println(MSG8);
        int slot = Integer.parseInt(new Scanner(System.in).nextLine());

        System.out.println(MSG9);
        viewItems(connect);
        System.out.println(MSG10);

        String[] tokens = (new Scanner(System.in).nextLine()).split(SPACE);
        for (String token : tokens) {
            mealDao.addItemToMeal(connect, Integer.parseInt(token), slot);
        }
    }

    public void addNewItem(Connection connect) throws SQLException {
        System.out.println(MSG6);
        String itemName = new Scanner(System.in).nextLine();
        mealDao.addItem(itemName, connect);
    }

    public void viewItems(Connection connect) throws SQLException {
        List<Item> items = mealDao.generateItems(connect);
        printItem(items, false);
    }

    public void printItem(List<Item> list, boolean flag) {
        if (flag) {
            for (Item item : list) {
                System.out.print(item.getName()+COMMA);
            }
        }
        else{
            for (Item item : list) {
                System.out.println(item.getId() + TAB + item.getName());
            }
        }
    }

    public void printMeal(List<Meal> list, boolean flag) {
        if (!flag) {
            for (Meal item : list) {
                System.out.println(item.getId() + TAB + DAY + item.getDay().name() + SLOT + item.getSlot().name());
            }
        } else {
            for (Meal item : list) {
                if (item.getItems().size() !=0) {
                    System.out.println(DAY + item.getDay().name() + SLOT + item.getSlot().name());
                    printItem(item.getItems(), true);
                    System.out.println();
                }
            }
        }
    }
}
