package edu.rit.croatia.swen383.g3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of food items, including both basic foods and recipes.
 * Acts as a storage and lookup class for all loaded foods in the application.
 */
public class Foods {
    private List<Food> foodList;

    /**
     * Constructs an empty Foods collection.
     */
    public Foods() {
        foodList = new ArrayList<>();
    }

    /**
     * Adds a new food item (BasicFood or Recipe) to the collection.
     *
     * @param food the food item to add
     */
    public void addFood(Food food) {
        foodList.add(food);
    }

    /**
     * Returns a list of all food items in the collection.
     *
     * @return the list of foods
     */
    public List<Food> getAllFoods() {
        return foodList;
    }

    /**
     * Finds a food item in the collection by its name.
     *
     * @param name the name of the food to find
     * @return the matching food object, or null if not found
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

