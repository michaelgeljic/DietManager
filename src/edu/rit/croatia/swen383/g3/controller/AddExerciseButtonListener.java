package edu.rit.croatia.swen383.g3.controller;
import edu.rit.croatia.swen383.g3.model.Exercise;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddExerciseButtonListener implements ActionListener {
    private final Controller controller;

    public AddExerciseButtonListener(Controller controller){
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Use the combined prompt
        Exercise newExercise = controller.getView().promptForExerciseCombined();
        if (newExercise == null) {
            return; // cancelled or invalid input
            
        }

        // check for duplicate
        boolean exists = controller.getExercises().getAllExercises().stream().anyMatch(ex -> ex.getName().equalsIgnoreCase(newExercise.getName()));

        if (exists) {
            controller.getView().showMessage("Exercise already exists. Cannot add duplicate.");
            return; //stop if duplicate
            
        }

        // adding new exercises
        controller.getExercises().addExercise(newExercise);
        controller.getExercises().saveToFile("assets/data/exercise.csv");
        controller.getView().updateExerciseList(controller.getExercises().getAllExercises());
        controller.getView().showMessage("New exercise added: " + newExercise.getName());
        
    }
    
}
