package edu.rit.croatia.swen383.g3.model;

import java.time.LocalDate;

/**
 * Represents a log entry for a specific food consumed on a given date.
 * Stores the food item, number of servings, and provides methods to calculate
 * total nutritional values based on servings.
 */
public class Log {
    private LocalDate date;
    private Food food;
    private double servings;

    /**
     * Constructs a new {@code Log} entry with the specified date, food, and number of servings.
     *
     * @param date     the date the food was consumed
     * @param food     the food item logged
     * @param servings the number of servings consumed
     */
    public Log(LocalDate date, Food food, double servings) {
        this.date = date;
        this.food = food;
        this.servings = servings;
    }

    /**
     * Returns the date of this log entry.
     *
     * @return the date of consumption
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the food item recorded in this log entry.
     *
     * @return the food item
     */
    public Food getFood() {
        return food;
    }

    /**
     * Returns the number of servings consumed for this food.
     *
     * @return the number of servings
     */
    public double getServings() {
        return servings;
    }

    /**
     * Calculates and returns the total calories consumed based on the servings.
     *
     * @return total calories consumed
     */
    public double getTotalCalories() {
        return servings * food.getCalories();
    }

    /**
     * Calculates and returns the total fat consumed based on the servings.
     *
     * @return total fat consumed (in grams)
     */
    public double getTotalFat() {
        return servings * food.getFat();
    }

    /**
     * Calculates and returns the total carbohydrates consumed based on the servings.
     *
     * @return total carbohydrates consumed (in grams)
     */
    public double getTotalCarbs() {
        return servings * food.getCarbs();
    }

    /**
     * Calculates and returns the total protein consumed based on the servings.
     *
     * @return total protein consumed (in grams)
     */
    public double getTotalProtein() {
        return servings * food.getProtein();
    }

    /**
     * Returns a formatted string representation of this log entry, including the number
     * of servings, food name, and total nutritional values.
     *
     * @return a formatted string summarizing the log
     */
    @Override
    public String toString() {
        return String.format("%.1f x %s (%.1f cal, %.1f fat, %.1f carbs, %.1f protein)",
                servings, food.getName(),
                Math.round(getTotalCalories() * 10.0) / 10.0,
                Math.round(getTotalFat() * 10.0) / 10.0,
                Math.round(getTotalCarbs() * 10.0) / 10.0,
                Math.round(getTotalProtein() * 10.0) / 10.0);
    }
}
