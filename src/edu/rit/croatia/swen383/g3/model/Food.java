package edu.rit.croatia.swen383.g3.model;

/**
 * Abstract base class represening a food item
 * this class is a part of the composite design pattern
 * Both basic foods nd composite recipes extend this class and provide
 * nutritional information
 */
public abstract class Food {
    /**
     * Gets the name of the food
     * @return the name of the food item
     */
    public abstract String getName();

    /**
     * gets the total calories for the food.
     * @return the calorie count
     */
    public abstract double getCalories();

    /**
     * gets the total fat content of the food
     * @return fat in grams
     */
    public abstract double getFat();

    /**
     * gets the total carbohydrate content of the food
     * @return carbs in grams
     */
    public abstract double getCarbs();

    /**
     * gets the total protein content of the food
     * @return protein in grams
     */
    public abstract double getProtein();

    /**
     * 
     * Adds a food item as a component of this food
     * this method is overridden by composite classes (recipe)
     * for basic foods, this operation is not supported
     * @param food the food to add
     */
    public void add(Food food) {
        throw new UnsupportedOperationException("Cannot add to a basic food");
    }
}
