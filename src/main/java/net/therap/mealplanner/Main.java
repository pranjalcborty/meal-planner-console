package net.therap.mealplanner;

import net.therap.mealplanner.helper.Helper;
import net.therap.mealplanner.helper.MealHelper;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        MealHelper helper = new MealHelper();

        while (true) {
            helper.welcomeMessage();
            int input = Integer.parseInt(new Scanner(System.in).nextLine());

            switch (Helper.getOption(input)) {
                case VIEW_PLAN:
                    helper.showMealPlans();
                    break;
                case VIEW_ITEMS:
                    helper.showItems();
                    break;
                case ADD_PLAN:
                    helper.addPlan();
                    break;
                case ADD_ITEM:
                    helper.addItem();
                    break;
                case INVALID:
                    helper.invalidMessage();
                    break;
            }
        }
    }
}
