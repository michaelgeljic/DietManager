package edu.rit.croatia.swen383.g3.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.rit.croatia.swen383.g3.util.FileHandler;

/**
 * Represents a collection of log entries for food consumed over time.
 * Provides methods to add logs, retrieve logs for a specific date,
 * calculate daily nutrient totals, and save logs to a CSV file.
 */
public class Logs {
    private List<Log> logEntries;

    /**
     * Constructs a new Logs object with an empty list of log entries.
     */
    public Logs() {
        logEntries = new ArrayList<>();
    }

    /**
     * Adds a new log entry to the list.
     * The listener or controller is responsible for view updates.
     *
     * @param log the log entry to add
     */
    public void addLog(Log log) {
        logEntries.add(log);
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
     * Calculates the total calories consumed on a specific date.
     *
     * @param date the date to calculate total calories for
     * @return the total calories consumed on that date
     */
    public double getTotalCaloriesForDate(LocalDate date) {
        double total = 0;
        for (Log l : getLogForDate(date)) {
            total += l.getTotalCalories();
        }
        return total;
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
     * Saves all current log entries to the specified CSV file using the given
     * FileHandler.
     *
     * @param filename the file path to save to
     * @param handler  the FileHandler to use
     */
    public void saveLogsToFile(String filename, FileHandler handler) {
        handler.writeLogs(logEntries, filename);
    }

    /**
     * Retrieves all log entries.
     *
     * @return the full list of logs
     */
    public List<Log> getAllLogs() {
        return logEntries;
    }

    /**
     * Adds a list of log entries to the existing list.
     * 
     * @param logsToAdd the list of logs to add
     */
    public void addAllLogs(List<Log> logsToAdd) {
        logEntries.addAll(logsToAdd);
    }
}
