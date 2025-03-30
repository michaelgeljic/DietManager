package edu.rit.croatia.swen383.g3.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a composite food item made up of multiple ingredients, each with a serving amount.
 * This class implements the Composite Design Pattern and extends the abstract Food class.
 */
public class Recipe extends Food{
    private String name;
    private Map<Food, Double> ingredients;

    /**
     * Constructs a new recipe with the given name.
     *
     * @param name the name of the recipe
     */
    public Recipe(String name){
        this.name = name;
        this.ingredients = new HashMap<>();
    }
    /**
     * Adds an ingredient to the recipe with a default of 1.0 serving.
     * This overrides the abstract add method in the Food class.
     *
     * @param food the food item to add
     */
    @Override
    public void add(Food food){
        add(food, 1.0);
    }
    /**
     * Adds an ingredient to the recipe with a specified number of servings.
     *
     * @param food     the ingredient to add
     * @param servings the amount of the ingredient used in the recipe
     */
    public void add(Food food, double servings){
        ingredients.put(food,servings);
    }

    /**
     * Returns the name of the recipe.
     *
     * @return the recipe name
     */
    @Override
    public String getName() {
        return name;
    }
    /**
     * Calculates and returns the total calories of the recipe.
     *
     * @return total calories across all ingredients
     */
    @Override
    public double getCalories() {
        return ingredients.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getCalories() * entry.getValue()).sum();
    }
    /**
     * Calculates and returns the total fat content of the recipe.
     *
     * @return total fat in grams
     */
    @Override
    public double getFat() {
        return ingredients.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getFat() * entry.getValue())
                .sum();
    }
    /**
     * Calculates and returns the total carbohydrate content of the recipe.
     *
     * @return total carbohydrates in grams
     */
    @Override
    public double getCarbs() {
        return ingredients.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getCarbs() * entry.getValue())
                .sum();
    }
    /**
     * Calculates and returns the total protein content of the recipe.
     *
     * @return total protein in grams
     */
    @Override
    public double getProtein() {
        return ingredients.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getProtein() * entry.getValue())
                .sum();
    }
    /**
     * Returns the ingredients and their serving amounts used in this recipe.
     *
     * @return a set of map entries containing each food and its servings
     */
    public Set<Map.Entry<Food, Double>> getIngredientsWithServings() {
        return ingredients.entrySet();
    }
    /**
     * Returns a string representation of the recipe for display.
     *
     * @return the recipe name followed by "(Recipe)"
     */
    @Override
    public String toString() {
        return name + " (Recipe)";
    }
}
