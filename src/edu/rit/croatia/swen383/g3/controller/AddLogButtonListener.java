package edu.rit.croatia.swen383.g3.controller;

import edu.rit.croatia.swen383.g3.model.Food;
import edu.rit.croatia.swen383.g3.model.Log;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * Handles the action of logging a food entry when the
 * "Add Log Entry" button is clicked in the View.
 *
 * Retrieves the selected food, prompts for servings,
 * creates a Log entry, updates the view and log.csv.
 */
public class AddLogButtonListener implements ActionListener {
    private final Controller controller;

    /**
     * Constructs the listener with a reference to the controller.
     *
     * @param controller the Controller instance to access model and view
     */
    public AddLogButtonListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Called when the "Add Log Entry" button is clicked.
     * Validates selection, prompts for servings, creates a log,
     * updates the UI and writes the log entry to the file.
     *
     * @param e the event triggered by the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String foodName = controller.getView().getSelectedFoodName();
        if (foodName == null) {
            controller.getView().showMessage("Please select a food from the list.");
            return;
        }

        Food selected = controller.getFoods().findFoodByName(foodName);
        if (selected == null) {
            controller.getView().showMessage("Food not found.");
            return;
        }

        String servingsStr = controller.getView().promptForServings();
        if (servingsStr == null) return;

        try {
            double servings = Double.parseDouble(servingsStr);
            Log log = new Log(LocalDate.now(), selected, servings);
            controller.getLogs().addLog(log);

            StringBuilder sb = new StringBuilder();
            for (Log l : controller.getLogs().getLogForDate(LocalDate.now())) {
                sb.append(l.toString()).append("\n");
            }

            controller.getView().updateLogList(sb.toString());
            controller.getView().updateCalories(controller.getLogs().getTotalCaloriesForDate(LocalDate.now()));
            controller.getFileHandler().writeLogs(controller.getLogs().getAllLogs(), "./src/edu/rit/croatia/swen383/g3/data/log.csv");

        } catch (NumberFormatException ex) {
            controller.getView().showMessage("Invalid servings amount.");
        }
    }
}