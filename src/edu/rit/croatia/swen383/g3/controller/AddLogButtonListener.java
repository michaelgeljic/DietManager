package edu.rit.croatia.swen383.g3.controller;

import edu.rit.croatia.swen383.g3.model.Food;
import edu.rit.croatia.swen383.g3.model.Log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles the logic for logging food entries when the user clicks the
 * "Add Log Entry" button in the view. Prompts the user to select foods and
 * servings, creates log entries for the selected date, and updates the view.
 */
public class AddLogButtonListener implements ActionListener {
    private final Controller controller;

    /**
     * Constructs an AddLogButtonListener with a reference to the controller.
     *
     * @param controller the main controller managing the application state
     */
    public AddLogButtonListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Called when the "Add Log Entry" button is clicked.
     * Retrieves selected food items and servings from the view,
     * adds logs for the selected date, and refreshes the view.
     *
     * @param e the action event triggered by the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // ask user whether to log food or exercise
        String[] options = {"Food", "Exercise" };
        int choice = controller.getView().promptForType("What would you like to log?", options);

        LocalDate selectedDate = controller.getCurrentDate();
        //log food
        if (choice == 0) {
            Object[] foodResult = controller.getView().promptForFoodAndServings(controller.getFoods().getAllFoods());
            if (foodResult == null) return;

            Food selectedFood = (Food) foodResult[0];
            double servings = (double) foodResult[1];

            controller.getLogs().addLog(new Log(selectedDate, selectedFood, servings));

            // log exercise
        } else if (choice == 1) {
            Object[] exerciseResult = controller.getView().promptForExerciseAndMinutes(controller.getExercises().getAllExercises());
            if (exerciseResult == null) return;

            Exercise selectedExercise = (Exercise) exerciseResult[0];
            double minutes = (double) exerciseResult[1];
            
            controller.getLogs().addExerciseLog(selectedDate, new ExerciseEntry(selectedExercise, minutes));
        }
        //refresh logs and stats
        controller.refreshLogsAndStats();
        controller.getLogs().saveLogsToFile("assets/data/log.csv");
    }
}
