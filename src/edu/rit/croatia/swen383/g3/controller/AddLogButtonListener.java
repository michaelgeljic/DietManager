package edu.rit.croatia.swen383.g3.controller;

import edu.rit.croatia.swen383.g3.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
/**
 * Handles the add log button action.
 * Allows the user to log either a food entry or an exercise entry for the selected date.
 * Updates the logs, saves the data, and refreshes the view.
 */
public class AddLogButtonListener implements ActionListener {
    private final Controller controller;
/**
     * Constructs the listener with a reference to the controller.
     *
     * @param controller the Controller coordinating between model and view
     */
    public AddLogButtonListener(Controller controller) {
        this.controller = controller;
    }
/**
     * Invoked when the add log button is clicked.
     * Prompts the user to choose between logging food or exercise, collects input, updates the logs,
     * saves the updated logs to the CSV file, and refreshes the view.
     *
     * @param e the action event triggered by clicking the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Ask user whether to log Food or Exercise
        String[] options = { "Food", "Exercise" };
        int choice = controller.getView().promptForType("What would you like to log?", options);

        LocalDate selectedDate = controller.getCurrentDate();

        if (choice == 0) { // Log Food
            Object[] foodResult = controller.getView().promptForFoodAndServings(controller.getFoods().getAllFoods());
            if (foodResult == null) return;

            Food selectedFood = (Food) foodResult[0];
            double servings = (double) foodResult[1];

            controller.getLogs().addLog(new Log(selectedDate, selectedFood, servings));

        } else if (choice == 1) { // Log Exercise
            Object[] exerciseResult = controller.getView().promptForExerciseAndMinutes(controller.getExercises().getAllExercises());
            if (exerciseResult == null) return;

            Exercise selectedExercise = (Exercise) exerciseResult[0];
            double minutes = (double) exerciseResult[1];

            controller.getLogs().addExerciseLog(selectedDate, new ExerciseEntry(selectedExercise, minutes));
        }

        // Refresh logs and stats
        controller.refreshLogsAndStats();
        controller.getLogs().saveLogsToFile("assets/data/log.csv");
    }
}
