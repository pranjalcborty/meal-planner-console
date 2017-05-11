package net.therap.mealplanner.helper;

import net.therap.mealplanner.dao.ItemDao;
import net.therap.mealplanner.dao.MealDao;
import net.therap.mealplanner.domains.Item;
import net.therap.mealplanner.domains.Meal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class MealHelper {

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
    private ItemDao itemDao;

    public MealHelper() {
        mealDao = new MealDao();
        itemDao = new ItemDao();
    }

    public void welcomeMessage() {
        System.out.println(MSG1 + MSG2 + MSG3 + MSG4 + MSG5);
    }

    public void showMealPlans(Connection connect) throws SQLException {
        printMeal(mealDao.currentMeals(connect));
    }

    public void addPlan(Connection connect) throws SQLException {
        System.out.println(MSG7);
        printMealWithHeader(mealDao.currentMeals(connect));
        System.out.println(MSG8);
        int slot = Integer.parseInt(new Scanner(System.in).nextLine());

        System.out.println(MSG9);
        showItems(connect);
        System.out.println(MSG10);

        String[] tokens = (new Scanner(System.in).nextLine()).split(SPACE);
        for (String token : tokens) {
            mealDao.addItemToMeal(connect, Integer.parseInt(token), slot);
        }
    }

    public void addItem(Connection connect) throws SQLException {
        System.out.println(MSG6);
        String itemName = new Scanner(System.in).nextLine();
        itemDao.addItem(itemName, connect);
    }

    public void showItems(Connection connect) throws SQLException {
        List<Item> items = itemDao.generateItems(connect);
        printItem(items, false);
    }

    public void printItem(List<Item> list, boolean showHeader) {
        if (showHeader) {
            System.out.print("Items: ");

            for (Item item : list) {
                System.out.print(item.getName() + COMMA);
            }
        } else {
            for (Item item : list) {
                System.out.println(item.getId() + TAB + item.getName());
            }
        }
    }

    public void printMeal(List<Meal> list) {
        for (Meal item : list) {
            if (item.getItems().size() != 0) {
                System.out.print(DAY + item.getDay().name() + SLOT + item.getSlot().name() + TAB);
                printItem(item.getItems(), true);
                System.out.println();
            }
        }
    }

    public void printMealWithHeader(List<Meal> list) {
        for (Meal item : list) {
            System.out.println(item.getId() + TAB + DAY + item.getDay().name() + SLOT + item.getSlot().name());
        }
    }

    public void invalidMessage() {
        System.out.println("Invalid input. Please try again.");
    }
}
