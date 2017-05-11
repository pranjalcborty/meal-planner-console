package net.therap.mealplanner;

import net.therap.mealplanner.helper.ConnectionService;
import net.therap.mealplanner.helper.MealService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class MealController {

    public static void main(String[] args) throws SQLException {
        MealService service = new MealService();
        Connection connect = ConnectionService.connect();

        while (true) {
            service.welcomeMessage();
            int input = Integer.parseInt(new Scanner(System.in).nextLine());

            switch (input) {
                case 1:
                    service.showCurrentMealPlan(connect);
                    break;
                case 2:
                    service.viewItems(connect);
                    break;
                case 3:
                    service.createCustomPlan(connect);
                    break;
                case 4:
                    service.addNewItem(connect);
                    break;
                default:
                    connect.close();
                    break;
            }
        }
    }
}
