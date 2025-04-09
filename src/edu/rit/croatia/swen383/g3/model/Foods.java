package edu.rit.croatia.swen383.g3.model;

import edu.rit.croatia.swen383.g3.util.FileHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of all foods (basic and recipes) in the application.
 * Responsible for loading, saving, and adding food items.
 */
public class Foods {
    private List<Food> foodList;
    private final FileHandler fileHandler;

    /**
     * Constructs a Foods model using the provided FileHandler for persistence.
     *
     * @param fileHandler the FileHandler to use for reading/writing food data
     */
    public Foods(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.foodList = new ArrayList<>();
    }

    /**
     * Loads food data from a file and populates the food list.
     *
     * @param filename the CSV file path
     */
    public void loadFromFile(String filename) {
        foodList = fileHandler.readFoods(filename);
    }

    /**
     * Saves current food list to file.
     *
     * @param filename the destination CSV file
     */
    public void saveToFile(String filename) {
        fileHandler.writeFoods(foodList, filename);
    }

    /**
     * Adds a new food item and immediately saves to file.
     *
     * @param food the food to add
     */
    public void addFood(Food food) {
        foodList.add(food);
        saveToFile("assets/data/foods.csv");
    }

    /**
     * Retrieves all loaded food items.
     *
     * @return list of all foods
     */
    public List<Food> getAllFoods() {
        return foodList;
    }

    /**
     * Finds a food item by name (case-insensitive).
     *
     * @param name the name to search for
     * @return the matching Food or null if not found
     */
    public Food findFoodByName(String name) {
        for (Food food : foodList) {
            if (food.getName().equalsIgnoreCase(name)) {
                return food;
            }
        }
        return null;
    }
}