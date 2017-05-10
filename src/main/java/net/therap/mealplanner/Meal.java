package net.therap.mealplanner;

import java.util.List;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class Meal {
    List<Item> items;
    private int id;
    private Day day;
    private Slot slot;

    public Meal(int id, Day day, Slot slot, List<Item> items) {
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

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
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
