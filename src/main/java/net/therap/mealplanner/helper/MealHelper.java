package net.therap.mealplanner.helper;

import net.therap.mealplanner.dao.ItemDao;
import net.therap.mealplanner.dao.MealDao;
import net.therap.mealplanner.domains.Item;
import net.therap.mealplanner.domains.Meal;

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

    public void showMealPlans() throws SQLException {
        printMeal(mealDao.getMeals());
    }

    public void addPlan() throws SQLException {
        System.out.println(MSG7);
        printMealWithHeader(mealDao.getMeals());
        System.out.println(MSG8);
        int slot = Integer.parseInt(new Scanner(System.in).nextLine());

        System.out.println(MSG9);
        showMenuItems();
        System.out.println(MSG10);

        String[] tokens = (new Scanner(System.in).nextLine()).split(SPACE);
        for (String token : tokens) {
            mealDao.addItemToMeal(Integer.parseInt(token), slot);
        }
    }

    public void addItem() throws SQLException {
        System.out.println(MSG6);
        String itemName = new Scanner(System.in).nextLine();
        itemDao.addItem(itemName);
    }

    public void showItems() throws SQLException {
        List<Item> items = itemDao.generateItems();

        for (Item item : items) {
            System.out.println(item.getName());
        }
    }

    public void showMenuItems() throws SQLException{
        List<Item> items = itemDao.generateItems();

        for (Item item : items) {
            System.out.println(item.getId() + TAB + item.getName());
        }
    }

    public void showMealItems(List<Item> items) throws SQLException{
        System.out.print("Items: ");
        for (Item item : items) {
            System.out.print(item.getName() + COMMA);
        }
    }


    public void printMeal(List<Meal> list) throws SQLException{
        for (Meal item : list) {
            if (item.getItems().size() != 0) {
                System.out.print(DAY + item.getDay().name() + SLOT + item.getSlot().name() + TAB);
                showMealItems(item.getItems());
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
