package edu.rit.croatia.swen383.g3.controller;

import edu.rit.croatia.swen383.g3.controller.Controller;
import edu.rit.croatia.swen383.g3.model.Exercise;
import edu.rit.croatia.swen383.g3.model.Exercises;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Handles the edit exercise button action.
 * Opens a dialog for selecting an exercise and updating its calories burned per
 * kilogram per hour.
 * Updates the exercise data and saves changes to the CSV file.
 */

public class EditExerciseButtonListener implements ActionListener {
    private final Controller controller;

    /**
     * Constructs the listener with a reference to the controller.
     *
     * @param controller the Controller coordinating between model and view
     */
    public EditExerciseButtonListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Invoked when the edit exercise button is clicked.
     * Opens a dialog allowing the user to select an exercise and enter a new
     * calorie value.
     * Updates the selected exercise and saves the changes.
     *
     * @param e the action event triggered by clicking the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Exercises exercises = controller.getExercises();

        List<Exercise> allExercises = exercises.getAllExercises();
        if (allExercises.isEmpty()) {
            controller.getView().showMessage("No exercises available to edit.");
            return;
        }

        // Dropdown for exercise selection
        JComboBox<String> exerciseDropdown = new JComboBox<>();
        for (Exercise ex : allExercises) {
            exerciseDropdown.addItem(ex.getName());
        }

        // Field for new calories input
        JTextField caloriesField = new JTextField(5);

        // Combine components
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Select Exercise:"));
        panel.add(exerciseDropdown);
        panel.add(new JLabel("New Calories per kg per hour:"));
        panel.add(caloriesField);

        // Show dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Exercise",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String selectedName = (String) exerciseDropdown.getSelectedItem();
            String calStr = caloriesField.getText();

            try {
                double newCalories = Double.parseDouble(calStr);
                Exercise selectedExercise = exercises.findExerciseByName(selectedName);
                if (selectedExercise != null) {
                    selectedExercise.setCaloriesPerKgPerHour(newCalories);
                    exercises.saveToFile("assets/data/exercise.csv");
                    controller.getView().showMessage("Exercise updated successfully.");
                    controller.getView().updateExerciseList(exercises.getAllExercises());
                }
            } catch (NumberFormatException ex) {
                controller.getView().showMessage("Invalid input. Please enter a valid number.");
            }
        }
    }
}
