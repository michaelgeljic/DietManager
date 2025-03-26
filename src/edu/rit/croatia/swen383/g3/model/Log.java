package edu.rit.croatia.swen383.g3.model;

import java.time.LocalDate;

/**
 * Represents a log entry for a specific food consumed on a given date.
 * Stores the food, the number of servings, and provides total nutrient
 * calculations.
 */
public class Log {
    private LocalDate date;
    private Food food;
    private double servings;

    /**
     * Constructs a new Log entry with the specified date, food, and servings.
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
     * Gets the date of this log entry.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the food item in this log entry.
     *
     * @return the food
     */
    public Food getFood() {
        return food;
    }

    /**
     * Gets the number of servings recorded in this log.
     *
     * @return servings consumed
     */
    public double getServings() {
        return servings;
    }

    /**
     * Calculates the total calories consumed based on the servings.
     *
     * @return total calories
     */
    public double getTotalCalories() {
        return servings * food.getCalories();
    }

    /**
     * Calculates the total fat consumed based on the servings.
     *
     * @return total fat in grams
     */
    public double getTotalFat() {
        return servings * food.getFat();
    }

    /**
     * Calculates the total carbohydrates consumed based on the servings.
     *
     * @return total carbs in grams
     */
    public double getTotalCarbs() {
        return servings * food.getCarbs();
    }

    /**
     * Calculates the total protein consumed based on the servings.
     *
     * @return total protein in grams
     */
    public double getTotalProtein() {
        return servings * food.getProtein();
    }

    /**
     * Returns a string representation of the log entry including the date,
     * servings, food name, and total nutrients.
     *
     * @return formatted string summary of the log entry
     */
    @Override
    public String toString() {
        return date + ": " + servings + " x " + food.getName() +
                " (" + getTotalCalories() + " cal, " +
                getTotalFat() + "g fat, " +
                getTotalCarbs() + "g carbs, " +
                getTotalProtein() + "g protein)";
    }
}