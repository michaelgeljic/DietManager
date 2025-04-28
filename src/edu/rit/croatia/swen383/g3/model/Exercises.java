package edu.rit.croatia.swen383.g3.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of Exercise objects.
 * Provides functionality to add exercises, retrieve them, and load/save
 * exercises from/to a CSV file.
 */
public class Exercises {
    private List<Exercise> exercises;

    /**
     * Constructs an empty Exercises list.
     */
    public Exercises() {
        this.exercises = new ArrayList<>();
    }

    /**
     * Adds an exercise to the list if it does not already exist (case-insensitive
     * name check).
     *
     * @param ex the Exercise to add
     */
    public void addExercise(Exercise ex) {
        // Ensure no duplicates by name (case-insensitive)
        for (Exercise e : exercises) {
            if (e.getName().equalsIgnoreCase(ex.getName())) {
                return; // Do not add duplicate
            }
        }
        exercises.add(ex);
    }

    /**
     * Returns the list of all stored Exercise objects.
     *
     * @return a list of exercises
     */
    public List<Exercise> getAllExercises() {
        return exercises;
    }

    /**
     * Loads exercises from a CSV file into the exercises list.
     * Clears any existing exercises before loading.
     *
     * CSV format: e,exerciseName,caloriesPerKgPerHour
     *
     * @param filename the path to the exercise CSV file
     */
    public void loadFromFile(String filename) {
        exercises.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("e,")) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String name = parts[1];
                        double cal = Double.parseDouble(parts[2]);
                        exercises.add(new Exercise(name, cal));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading exercises: " + e.getMessage());
        }
    }

    /**
     * Saves the current list of exercises to a CSV file.
     *
     * CSV format: e,exerciseName,caloriesPerKgPerHour
     *
     * @param filename the path to the exercise CSV file
     */
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Exercise e : exercises) {
                writer.println("e," + e.getName() + "," + e.getCaloriesPerKgPerHour());
            }
        } catch (IOException e) {
            System.out.println("Error saving exercises: " + e.getMessage());
        }
    }

    /**
     * Finds an exercise by its name (case-insensitive).
     *
     * @param name the name of the exercise to search for
     * @return the Exercise object if found, or null if not found
     */
    public Exercise findExerciseByName(String name) {
        for (Exercise e : exercises) {
            if (e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

}
