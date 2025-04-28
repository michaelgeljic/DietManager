package edu.rit.croatia.swen383.g3.controller;

import edu.rit.croatia.swen383.g3.model.*;
import edu.rit.croatia.swen383.g3.view.View;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * Handles the delete log button action.
 * Allows the user to delete either a food log or an exercise log entry for the
 * current date.
 * After deletion, updates the logs, saves changes, and refreshes the view.
 */
public class DeleteLogButtonListener implements ActionListener {
    private final Controller controller;

    /**
     * Constructs the listener with a reference to the controller.
     *
     * @param controller the Controller coordinating between model and view
     */
    public DeleteLogButtonListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Invoked when the delete log button is clicked.
     * Checks if a food log or an exercise log entry is selected in the view.
     * Deletes the selected entry for the current date, saves the updated logs, and
     * refreshes the view.
     *
     * @param e the action event triggered by clicking the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        View view = controller.getView();
        LocalDate currentDate = controller.getCurrentDate();

        // check if a food log entry is selected
        String selectedFoodLog = view.getSelectedFoodLog();
        if (selectedFoodLog != null) {
            Log logToRemove = controller.getLogs().getLogForDate(currentDate).stream()
                    .filter(log -> selectedFoodLog.endsWith(log.toString())).findFirst().orElse(null);

            if (logToRemove != null) {
                controller.getLogs().getAllLogs().remove(logToRemove);
                controller.getLogs().saveLogsToFile("assets/data/log.csv");
                controller.refreshLogsAndStats();
                return;

            }
        }

        // check if an exercise log entry is selected
        String selectedExerciseLog = view.getSelectedExerciseLog();
        if (selectedExerciseLog != null) {
            ExerciseEntry entryToRemove = controller.getLogs().getExercisesForDate(currentDate).stream()
                    .filter(entry -> selectedExerciseLog.contains(entry.getExercise().getName())
                            && selectedExerciseLog.contains(String.format("%.0f min", entry.getMinutes())))
                    .findFirst().orElse(null);
            if (entryToRemove != null) {
                controller.getLogs().getExercisesForDate(currentDate).remove(entryToRemove);
                controller.getLogs().saveLogsToFile("assets/data/log.csv");
                controller.refreshLogsAndStats();

            }
        }
    }

}
