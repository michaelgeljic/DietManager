package edu.rit.croatia.swen383.g3.model;

/**
 * Represents a basic food item with its nutritional values.
 * Each food has a name, calories, fat, carbs, and protein per serving.
 */
public class Food {
    private String name;
    private double calories;
    private double fat;
    private double carbs;
    private double protein;

    /**
     * Constructs a new Food object with the specified name and nutritional values.
     *
     * @param name     the name of the food
     * @param calories the number of calories per serving
     * @param fat      the amount of fat (in grams) per serving
     * @param carbs    the amount of carbohydrates (in grams) per serving
     * @param protein  the amount of protein (in grams) per serving
     */
    public Food(String name, double calories, double fat, double carbs, double protein) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    /**
     * Gets the name of the food.
     *
     * @return the food's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number of calories in the food.
     *
     * @return the calories per serving
     */
    public double getCalories() {
        return calories;
    }

    /**
     * Gets the amount of fat in the food.
     *
     * @return the fat in grams per serving
     */
    public double getFat() {
        return fat;
    }

    /**
     * Gets the amount of carbohydrates in the food.
     *
     * @return the carbs in grams per serving
     */
    public double getCarbs() {
        return carbs;
    }

    /**
     * Gets the amount of protein in the food.
     *
     * @return the protein in grams per serving
     */
    public double getProtein() {
        return protein;
    }

    /**
     * Returns a string representation of the food with its name and calories.
     *
     * @return a summary string for the food
     */
    @Override
    public String toString() {
        return name + ": " + calories + " cal";
    }
}
