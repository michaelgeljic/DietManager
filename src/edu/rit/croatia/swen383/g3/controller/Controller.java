package edu.rit.croatia.swen383.g3.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import edu.rit.croatia.swen383.g3.model.Food;
import edu.rit.croatia.swen383.g3.model.Foods;
import edu.rit.croatia.swen383.g3.model.Log;
import edu.rit.croatia.swen383.g3.model.Logs;
import edu.rit.croatia.swen383.g3.util.FileHandler;
import edu.rit.croatia.swen383.g3.view.View;

/**
 * The {@code Controller} class coordinates communication between the model and
 * the view
 * in the Diet Manager application. It sets up listeners and manages shared
 * application state
 * like the selected date.
 */
public class Controller {
    private final Foods foods;
    private final Logs logs;
    private final View view;
    private LocalDate currentDate = LocalDate.now();

    /**
     * Constructs the controller and wires up button listeners in the view.
     * It also loads initial food data from file and updates the view.
     *
     * @param foods the model containing food data
     * @param logs  the model containing log entries
     * @param view  the application's graphical user interface
     */
    public Controller(Foods foods, Logs logs, View view) {
        this.foods = foods;
        this.logs = logs;
        this.view = view;

        foods.loadFromFile("assets/data/foods.csv", new FileHandler());
        logs.addAllLogs(new FileHandler().readLogs("assets/data/log.csv", foods.getAllFoods()));

        view.updateFoodList(foods.getAllFoods());
        view.updateLogList("");
        view.updateStats(0, 0, 0, 0);
        view.updateWeight(0.0);
        view.addChangeDateListener(new ChangeDateButtonListener(this));
        view.updateCurrentDate(currentDate);
        List<Log> todayLogs = logs.getLogForDate(currentDate);
        String logText = todayLogs.stream().map(Log::toString).collect(Collectors.joining("\n"));
        view.updateLogList(logText);

        double calories = logs.getTotalCaloriesForDate(currentDate);
        double fat = logs.getTotalFatForDate(currentDate);
        double carbs = logs.getTotalCarbsForDate(currentDate);
        double protein = logs.getTotalProteinForDate(currentDate);
        view.updateStats(calories, fat, carbs, protein);
        view.addLogListener(new AddLogButtonListener(this));
        view.addFoodListener(new AddFoodButtonListener(this));
        view.addSetGoalsListener(e -> view.promptAndSetGoals());
    }

    /**
     * Gets the currently selected date.
     *
     * @return the current date
     */
    public LocalDate getCurrentDate() {
        return currentDate;
    }

    /**
     * Sets the currently selected date.
     *
     * @param date the new date to set
     */
    public void setCurrentDate(LocalDate date) {
        this.currentDate = date;
    }

    /**
     * Returns the Foods model instance.
     *
     * @return the Foods object
     */
    public Foods getFoods() {
        return foods;
    }

    /**
     * Returns the Logs model instance.
     *
     * @return the Logs object
     */
    public Logs getLogs() {
        return logs;
    }

    /**
     * Returns the View instance representing the GUI.
     *
     * @return the View object
     */
    public View getView() {
        return view;
    }
}
