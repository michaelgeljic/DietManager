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
        List<String> selectedNames = controller.getView().getSelectedFoodNames();
        if (selectedNames == null || selectedNames.isEmpty()) {
            controller.getView().showMessage("Please select at least one food.");
            return;
        }

        List<Food> selectedFoods = selectedNames.stream()
                .map(display -> display.split("—")[0].trim())
                .map(controller.getFoods()::findFoodByName)
                .filter(f -> f != null)
                .collect(Collectors.toList());

        if (selectedFoods.isEmpty()) {
            controller.getView().showMessage("No valid foods selected.");
            return;
        }

        Map<Food, Double> servingsMap = controller.getView().promptForServingsForMultipleFoods(selectedFoods);
        if (servingsMap == null || servingsMap.isEmpty()) {
            return;
        }

        LocalDate selectedDate = controller.getCurrentDate();

        for (Map.Entry<Food, Double> entry : servingsMap.entrySet()) {
            controller.getLogs().addLog(new Log(selectedDate, entry.getKey(), entry.getValue()));
        }

        List<Log> logsForDate = controller.getLogs().getLogForDate(selectedDate);
        String logText = logsForDate.stream()
                .map(Log::toString)
                .collect(Collectors.joining("\n"));
        controller.getView().updateLogList(logText);

        double calories = controller.getLogs().getTotalCaloriesForDate(selectedDate);
        double fat = controller.getLogs().getTotalFatForDate(selectedDate);
        double carbs = controller.getLogs().getTotalCarbsForDate(selectedDate);
        double protein = controller.getLogs().getTotalProteinForDate(selectedDate);
        controller.getView().updateStats(calories, fat, carbs, protein);
        controller.getLogs().saveLogsToFile("assets/data/log.csv", new edu.rit.croatia.swen383.g3.util.FileHandler());
    }
}
