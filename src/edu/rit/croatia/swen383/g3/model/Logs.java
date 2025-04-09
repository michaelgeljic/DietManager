package edu.rit.croatia.swen383.g3.model;

import edu.rit.croatia.swen383.g3.util.FileHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of log entries for food consumed over time.
 * Provides methods to add logs, retrieve logs for a specific date,
 * calculate daily nutrient totals, and save logs to a CSV file.
 */
public class Logs {
    private List<Log> logEntries;
    private final FileHandler fileHandler;

    /**
     * Constructs a Logs model using the provided FileHandler.
     *
     * @param fileHandler the FileHandler to use for reading/writing log data
     */
    public Logs(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.logEntries = new ArrayList<>();
    }

    /**
     * Adds a new log entry to the list.
     *
     * @param log the log entry to add
     */
    public void addLog(Log log) {
        logEntries.add(log);
    }

    /**
     * Adds multiple log entries to the list.
     *
     * @param logsToAdd the list of logs to add
     */
    public void addAllLogs(List<Log> logsToAdd) {
        logEntries.addAll(logsToAdd);
    }

    /**
     * Retrieves all log entries for a specific date.
     *
     * @param date the date to filter logs by
     * @return a list of log entries on the specified date
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
     * Saves all current log entries to the specified CSV file.
     *
     * @param filename the file path to save to
     */
    public void saveLogsToFile(String filename) {
        fileHandler.writeLogs(logEntries, filename);
    }

    /**
     * Reads log entries from file using available food items.
     *
     * @param filename       the log CSV file
     * @param availableFoods the list of known foods to match
     * @return list of parsed logs
     */
    public List<Log> readLogs(String filename, List<Food> availableFoods) {
        return fileHandler.readLogs(filename, availableFoods);
    }

    /**
     * Calculates the total calories consumed on a specific date.
     *
     * @param date the date to calculate total calories for
     * @return the total calories consumed on that date
     */
    public double getTotalCaloriesForDate(LocalDate date) {
        return getLogForDate(date).stream()
                .mapToDouble(Log::getTotalCalories)
                .sum();
    }

    /**
     * Calculates the total fat consumed on a specific date.
     *
     * @param date the date to calculate total fat for
     * @return the total fat consumed on that date
     */
    public double getTotalFatForDate(LocalDate date) {
        return getLogForDate(date).stream()
                .mapToDouble(Log::getTotalFat)
                .sum();
    }

    /**
     * Calculates the total carbohydrates consumed on a specific date.
     *
     * @param date the date to calculate total carbs for
     * @return the total carbs consumed on that date
     */
    public double getTotalCarbsForDate(LocalDate date) {
        return getLogForDate(date).stream()
                .mapToDouble(Log::getTotalCarbs)
                .sum();
    }

    /**
     * Calculates the total protein consumed on a specific date.
     *
     * @param date the date to calculate total protein for
     * @return the total protein consumed on that date
     */
    public double getTotalProteinForDate(LocalDate date) {
        return getLogForDate(date).stream()
                .mapToDouble(Log::getTotalProtein)
                .sum();
    }

    /**
     * Retrieves all logs (not filtered).
     *
     * @return list of all logs
     */
    public List<Log> getAllLogs() {
        return logEntries;
    }
}