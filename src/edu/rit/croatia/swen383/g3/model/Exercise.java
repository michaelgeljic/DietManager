package edu.rit.croatia.swen383.g3.model;

/**
 * Represents an exercise with its name and the number of calories burned per
 * kilogram of body weight per hour.
 */
public class Exercise {
    private String name;
    private double caloriesPerKgPerHour;

    /**
     * Constructs a new Exercise with the specified name and calories burned per
     * kilogram per hour.
     *
     * @param name                 the name of the exercise
     * @param caloriesPerKgPerHour the calories burned per kilogram of body weight
     *                             per hour
     */
    public Exercise(String name, double caloriesPerKgPerHour) {
        this.name = name;
        this.caloriesPerKgPerHour = caloriesPerKgPerHour;
    }

    /**
     * Returns the name of the exercise.
     *
     * @return the exercise name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the calories burned per kilogram of body weight per hour for this
     * exercise.
     *
     * @return the calories burned per kg per hour
     */
    public double getCaloriesPerKgPerHour() {
        return caloriesPerKgPerHour;
    }

    /**
     * Sets the calories burned per kilogram of body weight per hour for this
     * exercise.
     *
     * @param caloriesPerKgPerHour the calories burned per kg per hour to set
     */
    public void setCaloriesPerKgPerHour(double caloriesPerKgPerHour) {
        this.caloriesPerKgPerHour = caloriesPerKgPerHour;
    }

    /**
     * Returns a string representation of the exercise, including its name and
     * calorie burn rate.
     *
     * @return a string in the format "ExerciseName (caloriesPerKgPerHour
     *         cal/kg/hr)"
     */
    @Override
    public String toString() {
        return name + " (" + caloriesPerKgPerHour + " cal/kg/hr)";
    }
}
