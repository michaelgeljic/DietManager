package edu.rit.croatia.swen383.g3.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of log entries for food consumed over time.
 * Provides methods to add logs, retrieve logs for a specific date, and
 * calculate daily totals.
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
     * Retrieves all log entries.
     *
     * @return the full list of logs
     */
    public List<Log> getAllLogs() {
        return logEntries;
    }
}
