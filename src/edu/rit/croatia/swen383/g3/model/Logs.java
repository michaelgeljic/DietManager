package edu.rit.croatia.swen383.g3.model;

import edu.rit.croatia.swen383.g3.util.FileHandler;

import java.time.LocalDate;
import java.util.*;

/**
 * Manages food logs, exercise logs, weight logs, and calorie goal logs.
 */
public class Logs {
    private List<Log> logEntries;
    private final FileHandler fileHandler;

    private Map<LocalDate, List<ExerciseEntry>> exerciseLogs = new HashMap<>();
    private Map<LocalDate, Double> weightLogs = new HashMap<>();
    private Map<LocalDate, Double> calorieGoalLogs = new HashMap<>();

    /**
     * Constructor for Logs.
     * @param fileHandler Handler for reading and writing files.
     */
    public Logs(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.logEntries = new ArrayList<>();
    }

    /**
     * Adds a food log entry.
     * @param log The food log entry to add.
     */
    public void addLog(Log log) {
        logEntries.add(log);
    }

    /**
     * Adds an exercise log entry for a specific date.
     * @param date The date of the exercise.
     * @param entry The exercise entry.
     */
    public void addExerciseLog(LocalDate date, ExerciseEntry entry) {
        exerciseLogs.computeIfAbsent(date, d -> new ArrayList<>()).add(entry);
    }

    /**
     * Retrieves all food logs for a specific date.
     * @param date The date to retrieve logs for.
     * @return List of food logs for the date.
     */
    public List<Log> getLogForDate(LocalDate date) {
        List<Log> result = new ArrayList<>();
        for (Log l : logEntries) {
            if (l.getDate().equals(date)) {
                result.add(l);
            }
        }
        return result;
    }

    /**
     * Retrieves all exercise entries for a specific date.
     * @param date The date to retrieve exercises for.
     * @return List of exercise entries.
     */
    public List<ExerciseEntry> getExercisesForDate(LocalDate date) {
        return exerciseLogs.getOrDefault(date, new ArrayList<>());
    }

    /**
     * Calculates total calories consumed for a specific date.
     * @param date The date to calculate for.
     * @return Total calories consumed.
     */
    public double getTotalCaloriesForDate(LocalDate date) {
        return getLogForDate(date).stream()
                .mapToDouble(Log::getTotalCalories)
                .sum();
    }

    /**
     * Calculates total calories burned for a specific date.
     * @param date The date to calculate for.
     * @param weight The weight of the user.
     * @return Total calories burned.
     */
    public double getTotalCaloriesBurnedForDate(LocalDate date, double weight) {
        return getExercisesForDate(date).stream()
                .mapToDouble(e -> e.calculateCaloriesBurned(weight))
                .sum();
    }

    /**
     * Sets weight for a specific date.
     * @param date The date.
     * @param weight The weight to set.
     */
    public void setWeightForDate(LocalDate date, double weight) {
        weightLogs.put(date, weight);
    }

    /**
     * Retrieves the exact weight recorded for a specific date.
     * @param date The date.
     * @return The weight or null if not found.
     */
    public Double getWeightForExactDate(LocalDate date) {
        return weightLogs.get(date);
    }

    /**
     * Sets a calorie goal for a specific date.
     * @param date The date.
     * @param goal The calorie goal.
     */
    public void setCalorieGoalForDate(LocalDate date, double goal) {
        calorieGoalLogs.put(date, goal);
    }

    /**
     * Retrieves the exact calorie goal recorded for a specific date.
     * @param date The date.
     * @return The calorie goal or null if not found.
     */
    public Double getCalorieGoalForExactDate(LocalDate date) {
        return calorieGoalLogs.get(date);
    }

    /**
     * Retrieves all dates with exercise logs.
     * @return Set of dates.
     */
    public Set<LocalDate> getAllExerciseLogDates() {
        return exerciseLogs.keySet();
    }

    /**
     * Retrieves all dates with weight logs.
     * @return Set of dates.
     */
    public Set<LocalDate> getAllWeightDates() {
        return weightLogs.keySet();
    }

    /**
     * Retrieves all dates with calorie goal logs.
     * @return Set of dates.
     */
    public Set<LocalDate> getAllCalorieGoalDates() {
        return calorieGoalLogs.keySet();
    }

    /**
     * Retrieves all food logs.
     * @return List of all food logs.
     */
    public List<Log> getAllLogs() {
        return logEntries;
    }

    /**
     * Saves all logs to a file.
     * @param filename The filename to save to.
     */
    public void saveLogsToFile(String filename) {
        fileHandler.writeLogs(filename, this);
    }

    /**
     * Loads logs from a file.
     * @param filename The filename to read from.
     * @param availableFoods List of available foods.
     * @param exercises Exercise entries.
     */
    public void readLogsFromFile(String filename, List<Food> availableFoods, Exercises exercises) {
        fileHandler.readLogs(filename, availableFoods, exercises, this);
    }

    /**
     * Retrieves weight for a specific date, with fallback to previous dates.
     * @param date The date.
     * @return Weight value or default if none found.
     */
    public double getWeightForDate(LocalDate date) {
        if (weightLogs.containsKey(date)) {
            return weightLogs.get(date);
        }

        LocalDate closest = weightLogs.keySet().stream()
                .filter(d -> d.isBefore(date))
                .max(LocalDate::compareTo)
                .orElse(null);

        return closest != null ? weightLogs.get(closest) : 68.0;
    }

    /**
     * Retrieves calorie goal for a specific date, with fallback to previous dates.
     * @param date The date.
     * @return Calorie goal value or default if none found.
     */
    public double getCalorieGoalForDate(LocalDate date) {
        if (calorieGoalLogs.containsKey(date)) {
            return calorieGoalLogs.get(date);
        }

        LocalDate closest = calorieGoalLogs.keySet().stream()
                .filter(d -> d.isBefore(date))
                .max(LocalDate::compareTo)
                .orElse(null);

        return closest != null ? calorieGoalLogs.get(closest) : 2000.0;
    }

    /**
     * Calculates total fat consumed for a specific date.
     * @param date The date.
     * @return Total fat consumed.
     */
    public double getTotalFatForDate(LocalDate date) {
        return getLogForDate(date).stream()
                .mapToDouble(Log::getTotalFat)
                .sum();
    }

    /**
     * Calculates total carbohydrates consumed for a specific date.
     * @param date The date.
     * @return Total carbohydrates consumed.
     */
    public double getTotalCarbsForDate(LocalDate date) {
        return getLogForDate(date).stream()
                .mapToDouble(Log::getTotalCarbs)
                .sum();
    }

    /**
     * Calculates total protein consumed for a specific date.
     * @param date The date.
     * @return Total protein consumed.
     */
    public double getTotalProteinForDate(LocalDate date) {
        return getLogForDate(date).stream()
                .mapToDouble(Log::getTotalProtein)
                .sum();
    }
}