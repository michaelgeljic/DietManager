package edu.rit.croatia.swen383.g3.util;

import edu.rit.croatia.swen383.g3.model.*;
import edu.rit.croatia.swen383.g3.model.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;;

/**
 * Handles reading and writing of food and log data to and from CSV files.
 * Supports both basic foods and composite recipes using the Composite Design Pattern.
 */
public class FileHandler {

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

    /**
     * Writes a list of Log entries to a CSV file.
     *
     * CSV Format: year,month,day,f,foodName,servings
     *
     * @param logs     the list of logs to save
     * @param filename the path to the log.csv file
     */
    public void writeLogs(List<Log> logs, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Log log : logs) {
                writer.println(log.getDate().getYear() + "," +
                        log.getDate().getMonthValue() + "," +
                        log.getDate().getDayOfMonth() + ",f," +
                        log.getFood().getName() + "," +
                        log.getServings());
            }
        } catch (IOException e) {
            System.out.println("Error writing to log.csv: " + e.getMessage());
        }
    }

    /**
     * Placeholder for reading logs from CSV. Not yet implemented.
     *
     * @param filename        the path to the log.csv file
     * @param availableFoods  list of loaded foods to match log entries
     * @return an empty list (for now)
     */
    public List<Log> readLogs(String filename, List<Food> availableFoods) {
        return new ArrayList<>();
    }
}
