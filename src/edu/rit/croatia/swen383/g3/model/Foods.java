package edu.rit.croatia.swen383.g3.model;

import java.util.ArrayList;
import java.util.List;

import edu.rit.croatia.swen383.g3.util.FileHandler;

/**
 * Represents a collection of food items, including both basic foods and recipes.
 * Acts as a storage and lookup class for all loaded foods in the application.
 * Also handles persistence to and from the foods.csv file.
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
     * Automatically saves the updated food list to file.
     *
     * @param food the food item to add
     */
    public void addFood(Food food) {
        foodList.add(food);
        saveFoodsToFile("./assets/data/foods.csv");
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

    /**
     * Saves the current food list to the specified CSV file using FileHandler.
     *
     * @param filename the file path to save the food list to
     */
    private void saveFoodsToFile(String filename) {
        new FileHandler().writeFoods(foodList, filename);
    }

    /**
     * Loads foods from the given CSV file using a FileHandler and adds them to the collection.
     * Existing foods are not cleared before loading.
     *
     * @param filename the path to the foods.csv file
     * @param handler  the FileHandler used for reading the file
     */
    public void loadFromFile(String filename, FileHandler handler) {
        List<Food> loadedFoods = handler.readFoods(filename);
        for (Food food : loadedFoods) {
            addFood(food);
        }
    }
}
