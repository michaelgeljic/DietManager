package edu.rit.croatia.swen383.g3.model;

/**
 * Represents an exercise log entry that records an exercise performed and the
 * duration in minutes.
 */
public class ExerciseEntry {
    private Exercise exercise;
    private double minutes;

    /**
     * Constructs an ExerciseEntry with the specified exercise and duration.
     *
     * @param exercise the Exercise performed
     * @param minutes  the duration of the exercise in minutes
     */
    public ExerciseEntry(Exercise exercise, double minutes) {
        this.exercise = exercise;
        this.minutes = minutes;
    }

    /**
     * Returns the Exercise associated with this entry.
     *
     * @return the Exercise object
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Returns the duration of the exercise in minutes.
     *
     * @return the duration in minutes
     */
    public double getMinutes() {
        return minutes;
    }

    /**
     * Calculates the calories burned for this exercise entry based on the user's
     * weight.
     *
     * @param weight the user's weight in kilograms
     * @return the total calories burned
     */
    public double calculateCaloriesBurned(double weight) {
        return exercise.getCaloriesPerKgPerHour() * weight * (minutes / 60.0);
    }

    /**
     * Returns a string representation of the exercise entry, including the exercise
     * name and duration.
     *
     * @return a string in the format "ExerciseName: minutes min"
     */
    @Override
    public String toString() {
        return exercise.getName() + ": " + minutes + " min";
    }
}
