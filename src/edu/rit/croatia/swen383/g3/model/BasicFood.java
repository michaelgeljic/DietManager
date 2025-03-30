package edu.rit.croatia.swen383.g3.model;


/**
 * Represents a single, non-composite food item with defined nutritional values.
 * This is a leaf node in the Composite Design Pattern and extends the abstract Food class.
 */
public class BasicFood extends Food {

    private String name;
    private double calories;
    private double fat;
    private double carbs;
    private double protein;


    /**
     * Constructs a BasicFood with its name and nutritional values.
     *
     * @param name     the name of the food
     * @param calories the calorie content
     * @param fat      the fat content in grams
     * @param carbs    the carbohydrate content in grams
     * @param protein  the protein content in grams
     */
    public BasicFood(String name, double calories, double fat, double carbs, double protein) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    /**
     * Returns the name of the food.
     *
     * @return the food name
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }


    /**
     * Returns the number of calories.
     *
     * @return the calorie content
     */
    @Override
    public double getCalories() {
        // TODO Auto-generated method stub
        return calories;
    }


    /**
     * Returns the amount of fat.
     *
     * @return the fat content in grams
     */
    @Override
    public double getFat() {
        // TODO Auto-generated method stub
        return fat;
    }


    /**
     * Returns the amount of carbohydrates.
     *
     * @return the carbohydrate content in grams
     */
    @Override
    public double getCarbs() {
        // TODO Auto-generated method stub
        return carbs;
    }

    /**
     * Returns the amount of protein.
     *
     * @return the protein content in grams
     */
    @Override
    public double getProtein() {
        // TODO Auto-generated method stub
        return protein;
    }


    /**
     * Returns a string representation of the food with name and calories.
     *
     * @return a formatted string like "Pizza Slice (298.0 cal)"
     */
    @Override
    public String toString(){
        return name + " ("+calories+" cal)";
        
    }

}
