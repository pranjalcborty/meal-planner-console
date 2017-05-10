package net.therap.mealplanner;

import java.util.List;

/**
 * @author pranjal.chakraborty
 * @since 5/10/17
 */
public class Item {
    private int id;
    private String name;

    public Item (int id, String name){
        setId(id);
        setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
