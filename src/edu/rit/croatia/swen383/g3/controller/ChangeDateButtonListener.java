package edu.rit.croatia.swen383.g3.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 * Handles the logic for changing the selected date when the user clicks the
 * "Change Date" button in the view. Prompts the user to enter a new date,
 * updates the controller and view accordingly.
 */
public class ChangeDateButtonListener implements ActionListener {
    private final Controller controller;

    /**
     * Constructs a ChangeDateButtonListener with a reference to the controller.
     *
     * @param controller the main controller managing the application state
     */
    public ChangeDateButtonListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Called when the "Change Date" button is clicked.
     * Prompts the user for a date, updates the selected date in the controller,
     * and refreshes the logs and statistics for that date in the view.
     *
     * @param e the action event triggered by the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String input = JOptionPane.showInputDialog(
            null,
            "Enter date (YYYY-MM-DD):",
            controller.getCurrentDate().toString()
        );

        if (input == null || input.trim().isEmpty()) {
            controller.getView().showMessage("No date entered.");
            return;
        }

        try {
            LocalDate date = LocalDate.parse(input.trim());
            controller.setCurrentDate(date);
            controller.getView().updateCurrentDate(date);

            var logs = controller.getLogs().getLogForDate(date);
            String text = logs.stream()
                    .map(log -> log.toString())
                    .collect(java.util.stream.Collectors.joining("\n"));
            controller.getView().updateLogList(text);

            double calories = controller.getLogs().getTotalCaloriesForDate(date);
            double fat = controller.getLogs().getTotalFatForDate(date);
            double carbs = controller.getLogs().getTotalCarbsForDate(date);
            double protein = controller.getLogs().getTotalProteinForDate(date);
            controller.getView().updateStats(calories, fat, carbs, protein);

        } catch (Exception ex) {
            controller.getView().showMessage("Invalid date format. Use YYYY-MM-DD.");
        }
    }
}
