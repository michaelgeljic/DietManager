package edu.rit.croatia.swen383.g3.model;

public class ExerciseEntry {
    private Exercise exercise;
    private double minutes;

    public ExerciseEntry(Exercise exercise, double minutes) {
        this.exercise = exercise;
        this.minutes = minutes;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public double getMinutes() {
        return minutes;
    }

    // Calculate calories burned based on weight (in kg)
    public double calculateCaloriesBurned(double weight) {
        return exercise.getCaloriesPerKgPerHour() * weight * (minutes / 60.0);
    }

    @Override
    public String toString() {
        return exercise.getName() + ": " + minutes + " min";
    }
}
