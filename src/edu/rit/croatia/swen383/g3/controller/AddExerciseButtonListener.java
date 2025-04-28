package edu.rit.croatia.swen383.g3.controller;

import edu.rit.croatia.swen383.g3.model.Exercise;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles the add exercise button action.
 * Allows the user to add a new exercise, ensuring no duplicates are added.
 * Updates the exercise list, saves the data, and refreshes the view.
 */
public class AddExerciseButtonListener implements ActionListener {
    private final Controller controller;

    /**
     * Constructs the listener with a reference to the controller.
     *
     * @param controller the Controller coordinating between model and view
     */
    public AddExerciseButtonListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Invoked when the add exercise button is clicked.
     * Prompts the user to enter exercise details, checks for duplicates, adds the
     * new exercise,
     * saves the updated list, and refreshes the exercise list in the view.
     *
     * @param e the action event triggered by clicking the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Use the combined prompt
        Exercise newExercise = controller.getView().promptForExerciseCombined();
        if (newExercise == null) {
            return; // cancelled or invalid input

        }

        // check for duplicate
        boolean exists = controller.getExercises().getAllExercises().stream()
                .anyMatch(ex -> ex.getName().equalsIgnoreCase(newExercise.getName()));

        if (exists) {
            controller.getView().showMessage("Exercise already exists. Cannot add duplicate.");
            return; // stop if duplicate

        }

        // adding new exercises
        controller.getExercises().addExercise(newExercise);
        controller.getExercises().saveToFile("assets/data/exercise.csv");
        controller.getView().updateExerciseList(controller.getExercises().getAllExercises());
        controller.getView().showMessage("New exercise added: " + newExercise.getName());

    }

}
