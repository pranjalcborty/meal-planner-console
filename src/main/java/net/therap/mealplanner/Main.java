package net.therap.mealplanner;

import net.therap.mealplanner.helper.Helper;
import net.therap.mealplanner.helper.MealService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        MealService service = new MealService();
        Connection connect = Helper.connect();

        while (true) {
            service.welcomeMessage();
            int input = Integer.parseInt(new Scanner(System.in).nextLine());

            switch (Helper.getOption(input)) {
                case VIEW_PLAN:
                    service.showCurrentMealPlan(connect);
                    break;
                case VIEW_ITEMS:
                    service.viewItems(connect);
                    break;
                case ADD_PLAN:
                    service.createCustomPlan(connect);
                    break;
                case ADD_ITEM:
                    service.addNewItem(connect);
                    break;
                case INVALID:
                    service.invalidMessage();
                    break;
            }
        }
    }
}
