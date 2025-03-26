package edu.rit.croatia.swen383.g3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of food items.
 * Provides methods to add, retrieve, and search foods.
 */
public class Foods {
    private List<Food> foodList;

    /**
     * Constructs a new Foods collection with an empty list.
     */
    public Foods() {
        foodList = new ArrayList<>();
    }

    /**
     * Adds a new Food object to the collection.
     *
     * @param food the Food to be added
     */
    public void addFood(Food food) {
        foodList.add(food);
    }

    /**
     * Returns a list of all Food objects in the collection.
     *
     * @return a list of all foods
     */
    public List<Food> getAllFoods() {
        return foodList;
    }

    /**
     * Finds a Food by its name (case-insensitive).
     *
     * @param name the name of the food to search for
     * @return the Food object if found, or null if not found
     */
    public Food findFoodByName(String name) {
        for (Food f : foodList) {
            if (f.getName().equalsIgnoreCase(name)) {
                return f;
            }
        }
        return null;
    }
}

