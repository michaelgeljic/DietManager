package edu.rit.croatia.swen383.g3.controller;
import edu.rit.croatia.swen383.g3.model.Food;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles the action of loading foods from the foods.csv file
 * when the "Load Data" button is clicked in the View.
 *
 * Reads food data, updates the Foods model, and refreshes the GUI display.
 */
public class LoadButtonListener implements ActionListener {
    private final Controller controller;

    /**
     * Constructs the listener with a reference to the controller.
     *
     * @param controller the Controller instance to access model and view
     */
    public LoadButtonListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Triggered when the "Load Data" button is clicked.
     * Loads food entries from the CSV file into the Foods model,
     * resets today's log view, and updates the GUI.
     *
     * @param e the event triggered by the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        List<Food> loadedFoods = controller.getFileHandler().readFoods("./src/edu/rit/croatia/swen383/g3/data/foods.csv");
        for (Food food : loadedFoods) {
            controller.getFoods().addFood(food);
        }

        controller.getView().updateLogList("");
        controller.getView().updateCalories(0);
        controller.getView().updateWeight(0.0); // Default weight

        List<String> foodNames = controller.getFoods().getAllFoods().stream()
                .map(Food::getName)
                .collect(Collectors.toList());

        controller.getView().updateFoodList(foodNames);
        controller.getView().showMessage("Foods loaded successfully.");
    }
}
