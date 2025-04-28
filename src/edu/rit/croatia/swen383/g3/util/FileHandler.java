package edu.rit.croatia.swen383.g3.util;

import edu.rit.croatia.swen383.g3.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
/**
 * The FileHandler class handles reading and writing data for the Diet Manager application.
 * It supports exercises, foods (basic and recipes), logs (food entries, exercise entries, weight, and calorie goals).
 * Data is stored in CSV files for persistence.
 */
public class FileHandler {
    /**
     * Reads exercises from a CSV file and returns a list of Exercise objects.
     * Only lines starting with "e," are processed.
     *
     * CSV format: e,exerciseName,caloriesPerKgPerHour
     *
     * @param filename the path to the exercise CSV file
     * @return a list of Exercise objects
     */

    // Reading exercises from exercise.csv
    public List<Exercise> readExercises(String filename) {
        List<Exercise> exercises = new ArrayList<>();
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
            System.out.println("Error reading exercises: " + e.getMessage());
        }
        return exercises;
    }

    /**
     * Writes a list of Exercise objects to a CSV file.
     *
     * CSV format: e,exerciseName,caloriesPerKgPerHour
     *
     * @param exercises the list of exercises to write
     * @param filename  the path to the exercise CSV file
     */
    public void writeExercises(List<Exercise> exercises, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Exercise e : exercises) {
                writer.println("e," + e.getName() + "," + e.getCaloriesPerKgPerHour());
            }
        } catch (IOException e) {
            System.out.println("Error writing exercises: " + e.getMessage());
        }
    }

   /**
     * Reads logs from a CSV file and populates the Logs object with food logs, exercise logs, weight, and calorie goals.
     * It matches food and exercise names to existing objects from the Foods and Exercises lists.
     *
     * CSV formats:
     * - Food log:       yyyy,mm,dd,f,foodName,servings
     * - Exercise log:   yyyy,mm,dd,e,exerciseName,minutes
     * - Weight log:     yyyy,mm,dd,w,weight
     * - Calorie goal:   yyyy,mm,dd,c,calorieGoal
     *
     * @param filename  the path to the log CSV file
     * @param foods     the list of available Food objects for matching food logs
     * @param exercises the Exercises object containing available Exercise objects
     * @param logs      the Logs object to populate
     */
    public void readLogs(String filename, List<Food> foods, Exercises exercises, Logs logs) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);
                    LocalDate date = LocalDate.of(year, month, day);

                    switch (parts[3]) {
                        case "f": { // Food entry
                            String foodName = parts[4];
                            double servings = Double.parseDouble(parts[5]);
                            Food food = foods.stream()
                                    .filter(f -> f.getName().equalsIgnoreCase(foodName))
                                    .findFirst().orElse(null);
                            if (food != null) {
                                logs.addLog(new Log(date, food, servings));
                            }
                            break;
                        }
                        case "e": { // Exercise entry
                            String exerciseName = parts[4];
                            double minutes = Double.parseDouble(parts[5]);
                            Exercise exercise = exercises.getAllExercises().stream()
                                    .filter(e -> e.getName().equalsIgnoreCase(exerciseName))
                                    .findFirst().orElse(null);
                            if (exercise != null) {
                                logs.addExerciseLog(date, new ExerciseEntry(exercise, minutes));
                            }
                            break;
                        }
                        case "w": { // Weight entry
                            double weight = Double.parseDouble(parts[4]);
                            logs.setWeightForDate(date, weight);
                            break;
                        }
                        case "c": { // Calorie goal entry
                            double calorieGoal = Double.parseDouble(parts[4]);
                            logs.setCalorieGoalForDate(date, calorieGoal);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading logs: " + e.getMessage());
        }
    }

    /**
     * Writes all logs (food logs, exercise logs, weight, and calorie goals) from the Logs object to a CSV file.
     *
     * CSV formats:
     * - Food log:       yyyy,mm,dd,f,foodName,servings
     * - Exercise log:   yyyy,mm,dd,e,exerciseName,minutes
     * - Weight log:     yyyy,mm,dd,w,weight
     * - Calorie goal:   yyyy,mm,dd,c,calorieGoal
     *
     * @param filename the path to the log CSV file
     * @param logs     the Logs object containing log entries to write
     */
    public void writeLogs(String filename, Logs logs) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            // Write food logs
            for (Log log : logs.getAllLogs()) {
                String year = String.valueOf(log.getDate().getYear());
                String month = String.format("%02d", log.getDate().getMonthValue());
                String day = String.format("%02d", log.getDate().getDayOfMonth());
                writer.println(year + "," + month + "," + day + ",f," +
                        log.getFood().getName() + "," +
                        log.getServings());
            }

            // Write exercise logs
            for (LocalDate date : logs.getAllExerciseLogDates()) {
                String year = String.valueOf(date.getYear());
                String month = String.format("%02d", date.getMonthValue());
                String day = String.format("%02d", date.getDayOfMonth());
                for (ExerciseEntry entry : logs.getExercisesForDate(date)) {
                    writer.println(year + "," + month + "," + day + ",e," +
                            entry.getExercise().getName() + "," +
                            entry.getMinutes());
                }
            }

            // Write weights
            for (LocalDate date : logs.getAllWeightDates()) {
                String year = String.valueOf(date.getYear());
                String month = String.format("%02d", date.getMonthValue());
                String day = String.format("%02d", date.getDayOfMonth());
                writer.println(year + "," + month + "," + day + ",w," +
                        logs.getWeightForExactDate(date));
            }

            // Write calorie goals
            for (LocalDate date : logs.getAllCalorieGoalDates()) {
                String year = String.valueOf(date.getYear());
                String month = String.format("%02d", date.getMonthValue());
                String day = String.format("%02d", date.getDayOfMonth());
                writer.println(year + "," + month + "," + day + ",c," +
                        logs.getCalorieGoalForExactDate(date));
            }

        } catch (IOException e) {
            System.out.println("Error writing logs: " + e.getMessage());
        }
    }

    /**
     * Reads food data from a CSV file and returns a list of Food objects.
     * Performs a two-pass read:
     * 1. Loads all basic foods.
     * 2. Then loads recipes, using already-loaded foods as ingredients.
     *
     * CSV Format:
     * - Basic food: b,name,calories,fat,carbs,protein
     * - Recipe:     r,name,ingredient1,amount1,ingredient2,amount2,...
     *
     * @param filename the path to the foods.csv file
     * @return a list of all loaded foods (basic and recipes)
     */
    public List<Food> readFoods(String filename) {
        List<Food> loadedFoods = new ArrayList<>();
        List<String> recipeLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            // FIRST PASS: Load all BasicFoods
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("b,")) {
                    String[] parts = line.split(",");
                    if (parts.length == 6) {
                        String name = parts[1];
                        double cal = Double.parseDouble(parts[2]);
                        double fat = Double.parseDouble(parts[3]);
                        double carb = Double.parseDouble(parts[4]);
                        double protein = Double.parseDouble(parts[5]);
                        loadedFoods.add(new BasicFood(name, cal, fat, carb, protein));
                    }
                } else if (line.startsWith("r,")) {
                    recipeLines.add(line); // Store for second pass
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading foods.csv: " + e.getMessage());
        }

        // SECOND PASS: Process recipes using already loaded ingredients
        for (String line : recipeLines) {
            String[] parts = line.split(",");
            if (parts.length >= 4 && parts.length % 2 == 0) {
                String recipeName = parts[1];
                Recipe recipe = new Recipe(recipeName);

                for (int i = 2; i < parts.length; i += 2) {
                    String ingredientName = parts[i];
                    double amount = Double.parseDouble(parts[i + 1]);

                    for (Food f : loadedFoods) {
                        if (f.getName().equalsIgnoreCase(ingredientName)) {
                            recipe.add(f, amount);
                            break;
                        }
                    }
                }

                loadedFoods.add(recipe);
            }
        }

        return loadedFoods;
    }

    /**
     * Writes a list of Food objects (basic and recipes) to a CSV file.
     *
     * @param foods    the list of foods to save
     * @param filename the path to the foods.csv file
     */
    public void writeFoods(List<Food> foods, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Food food : foods) {
                if (food instanceof BasicFood) {
                    writer.println("b," + food.getName() + "," +
                            food.getCalories() + "," +
                            food.getFat() + "," +
                            food.getCarbs() + "," +
                            food.getProtein());
                } else if (food instanceof Recipe recipe) {
                    StringBuilder sb = new StringBuilder("r," + recipe.getName());
                    for (Map.Entry<Food, Double> entry : recipe.getIngredientsWithServings()) {
                        sb.append(",").append(entry.getKey().getName()).append(",").append(entry.getValue());
                    }
                    writer.println(sb);
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to foods.csv: " + e.getMessage());
        }
    }

}
