package net.therap.mealplanner.domains;

import net.therap.mealplanner.enums.Day;
import net.therap.mealplanner.enums.Type;

import java.util.List;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class Meal {
    List<Item> items;
    private int id;
    private Day day;
    private Type slot;

    public Meal(int id, Day day, Type slot, List<Item> items) {
        setId(id);
        setDay(day);
        setItems(items);
        setSlot(slot);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Type getSlot() {
        return slot;
    }

    public void setSlot(Type slot) {
        this.slot = slot;
    }

    public Day getDay() {

        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
