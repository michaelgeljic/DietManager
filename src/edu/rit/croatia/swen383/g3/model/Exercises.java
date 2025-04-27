package edu.rit.croatia.swen383.g3.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Exercises {
    private List<Exercise> exercises;

    public Exercises() {
        this.exercises = new ArrayList<>();
    }

    public void addExercise(Exercise ex) {
        // Ensure no duplicates by name (case-insensitive)
        for (Exercise e : exercises) {
            if (e.getName().equalsIgnoreCase(ex.getName())) {
                return; // Do not add duplicate
            }
        }
        exercises.add(ex);
    }

    public List<Exercise> getAllExercises() {
        return exercises;
    }

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

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Exercise e : exercises) {
                writer.println("e," + e.getName() + "," + e.getCaloriesPerKgPerHour());
            }
        } catch (IOException e) {
            System.out.println("Error saving exercises: " + e.getMessage());
        }
    }
    public Exercise findExerciseByName(String name) {
        for (Exercise e : exercises) {
            if (e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

}
