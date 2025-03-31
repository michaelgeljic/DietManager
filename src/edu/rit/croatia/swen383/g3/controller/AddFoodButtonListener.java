package edu.rit.croatia.swen383.g3.controller;

import edu.rit.croatia.swen383.g3.model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Listener for handling the "Add New Food" button in the View.
 * Prompts the user to create either a BasicFood or a Recipe,
 * and updates the model, view, and file storage accordingly.
 */
public class AddFoodButtonListener implements ActionListener {
    private final Controller controller;

    /**
     * Constructs the listener with access to the application's controller.
     *
     * @param controller the main Controller coordinating model and view
     */
    public AddFoodButtonListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Called when the "Add New Food" button is clicked.
     * Opens a prompt for the user to choose between creating a basic food or a recipe,
     * collects input through the View, and updates the model and foods.csv file.
     *
     * @param e the action event triggered by the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // User decides if they’re adding a basic food or a recipe
        String[] options = {"Basic Food", "Recipe"};
        int choice = controller.getView().promptForType("What type of food would you like to add?", options);

        Food newFood = null;

        if (choice == 0) { // Basic Food
            newFood = controller.getView().promptForBasicFood();
        } else if (choice == 1) { // Recipe
            newFood = controller.getView().promptForRecipe(controller.getFoods().getAllFoods());
        }

        if (newFood != null) {
            controller.getFoods().addFood(newFood);
            controller.getFileHandler().writeFoods(controller.getFoods().getAllFoods(), "./src/edu/rit/croatia/swen383/g3/data/foods.csv");

            List<String> foodNames = controller.getFoods().getAllFoods().stream()
                    .map(Food::getName)
                    .collect(Collectors.toList());

            controller.getView().updateFoodList(foodNames);
            controller.getView().showMessage("New food added: " + newFood.getName());
        }
    }
}