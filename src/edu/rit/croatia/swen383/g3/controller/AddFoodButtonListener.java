package edu.rit.croatia.swen383.g3.controller;

import edu.rit.croatia.swen383.g3.model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The {@code AddFoodButtonListener} class handles the logic for adding a new food item
 * when the user clicks the "Add New Food" button in the user interface.
 * It prompts the user to choose between creating a {@code BasicFood} or a {@code Recipe},
 * gathers the necessary input through the view, adds the food to the model,
 * and updates the view. Saving to file is handled internally by the model.
 */
public class AddFoodButtonListener implements ActionListener {
    private final Controller controller;

    /**
     * Constructs an {@code AddFoodButtonListener} with a reference to the main controller.
     *
     * @param controller the main controller coordinating the model and view
     */
    public AddFoodButtonListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Invoked when the "Add New Food" button is clicked.
     * Prompts the user to select a food type, gathers input,
     * adds the food to the model, and updates the view accordingly.
     *
     * @param e the action event triggered by the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] options = { "Basic Food", "Recipe" };
        int choice = controller.getView().promptForType("What type of food would you like to add?", options);

        Food newFood = null;

        if (choice == 0) {
            newFood = controller.getView().promptForBasicFood();
        } else if (choice == 1) {
            newFood = controller.getView().promptForRecipe(controller.getFoods().getAllFoods());
        }

        if (newFood != null) {
            controller.getFoods().addFood(newFood);  // handles saving internally
            controller.getView().updateFoodList(controller.getFoods().getAllFoods());
            controller.getView().showMessage("New food added: " + newFood.getName());
        }
    }
}
