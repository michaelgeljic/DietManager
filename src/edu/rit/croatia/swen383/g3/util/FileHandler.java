package edu.rit.croatia.swen383.g3.util;

import edu.rit.croatia.swen383.g3.model.Food;
import edu.rit.croatia.swen383.g3.model.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that handles reading from and writing to CSV files
 * for both food items and food log entries.
 */
public class FileHandler {

    /**
     * Reads a list of basic foods from a CSV file.
     * Only lines starting with "b," are processed.
     *
     * @param filename the path to the foods CSV file
     * @return a list of Food objects loaded from the file
     */
    public List<Food> readFoods(String filename) {
        List<Food> foods = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("b,")) {
                    String[] parts = line.split(",");
                    if (parts.length == 6) {
                        String name = parts[1];
                        double calories = Double.parseDouble(parts[2]);
                        double fat = Double.parseDouble(parts[3]);
                        double carbs = Double.parseDouble(parts[4]);
                        double protein = Double.parseDouble(parts[5]);
                        foods.add(new Food(name, calories, fat, carbs, protein));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading foods.csv: " + e.getMessage());
        }
        return foods;
    }

    /**
     * Writes a list of Food objects to a CSV file.
     * Each food entry is written in the "b,name,calories,fat,carbs,protein" format.
     *
     * @param foods    the list of foods to write
     * @param filename the path to the output CSV file
     */
    public void writeFoods(List<Food> foods, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Food food : foods) {
                writer.println("b," + food.getName() + "," +
                        food.getCalories() + "," +
                        food.getFat() + "," +
                        food.getCarbs() + "," +
                        food.getProtein());
            }
        } catch (IOException e) {
            System.out.println("Error writing to foods.csv: " + e.getMessage());
        }
    }

    /**
     * Placeholder method for reading log entries from a CSV file.
     * Not implemented yet — returns an empty list for now.
     *
     * @param filename        the path to the logs CSV file
     * @param availableFoods  the list of available foods (to match names to Food objects)
     * @return an empty list (to be implemented in a later activity)
     */
    public List<Log> readLogs(String filename, List<Food> availableFoods) {
        // TODO: IMPLEMENT IN LATER ACTIVITY
        System.out.println("Reading logs from " + filename);
        return new ArrayList<>();
    }

    /**
     * Writes a list of Log entries to a CSV file in the format:
     * yyyy,mm,dd,f,foodName,servings
     *
     * @param logs     the list of log entries to write
     * @param filename the path to the output CSV file
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
}
