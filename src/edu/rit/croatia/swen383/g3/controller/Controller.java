package edu.rit.croatia.swen383.g3.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import edu.rit.croatia.swen383.g3.model.*;
import edu.rit.croatia.swen383.g3.view.View;

public class Controller {
    private final Foods foods;
    private final Logs logs;
    private final Exercises exercises;
    private final View view;
    private LocalDate currentDate = LocalDate.now();

    public Controller(Foods foods, Logs logs, Exercises exercises, View view) {
        this.foods = foods;
        this.logs = logs;
        this.exercises = exercises;
        this.view = view;

        // Load foods and exercises
        foods.loadFromFile("assets/data/foods.csv");
        exercises.loadFromFile("assets/data/exercise.csv");

        // Load logs
        logs.readLogsFromFile("assets/data/log.csv", foods.getAllFoods(), exercises);

        // Initialize the view
        view.updateFoodList(foods.getAllFoods());
        view.updateExerciseList(exercises.getAllExercises());
        view.updateLogList("");
        view.updateStats(0, 0, 0, 0);
        view.updateWeight(0.0);
        view.updateCalorieGoal(0.0);

        // Add listeners
        view.addChangeDateListener(new ChangeDateButtonListener(this));
        view.addLogListener(new AddLogButtonListener(this));
        view.addFoodListener(new AddFoodButtonListener(this));
        view.addExerciseListener(new AddExerciseButtonListener(this));
        view.getEditExerciseButton().addActionListener(new EditExerciseButtonListener(this));


        view.addSetGoalsListener(e -> {
            double[] goals = view.promptAndSetGoals();
            double weight = goals[0];
            double calorieGoal = goals[1];

            LocalDate date = getCurrentDate();
            logs.setWeightForDate(date, weight);
            logs.setCalorieGoalForDate(date, calorieGoal);
            logs.saveLogsToFile("assets/data/log.csv");

            refreshLogsAndStats();
        });

        view.addDeleteLogListener(new DeleteLogButtonListener(this));

        // Initial date setup
        view.updateCurrentDate(currentDate);
        refreshLogsAndStats();
    }

    // Refresh logs, stats, weight, goals for the current date
    public void refreshLogsAndStats() {
        // Food logs
        List<Log> todayLogs = logs.getLogForDate(currentDate);
        StringBuilder logText = new StringBuilder();
        for (Log log : todayLogs) {
            logText.append(String.format("[%s] %s\n", currentDate, log.toString()));
        }
        view.updateLogList(logText.toString());


        // Exercise logs with detailed formatting
        List<ExerciseEntry> exerciseLogs = logs.getExercisesForDate(currentDate);
        StringBuilder exerciseText = new StringBuilder();
        double weight = logs.getWeightForDate(currentDate);  // Needed for calories burned

        for (ExerciseEntry entry : exerciseLogs) {
            double caloriesBurned = entry.calculateCaloriesBurned(weight);
            exerciseText.append(String.format("[%s] %s — %.0f min — %.0f cal burned\n",
                    currentDate, entry.getExercise().getName(), entry.getMinutes(), caloriesBurned));
        }

        view.updateExerciseLogList(exerciseText.toString());

        // Stats
        double calories = logs.getTotalCaloriesForDate(currentDate);
        double fat = logs.getTotalFatForDate(currentDate);
        double carbs = logs.getTotalCarbsForDate(currentDate);
        double protein = logs.getTotalProteinForDate(currentDate);

        double caloriesBurnedTotal = Math.round(logs.getTotalCaloriesBurnedForDate(currentDate, weight));
        double netCalories = calories - caloriesBurnedTotal;
        double calorieGoal = logs.getCalorieGoalForDate(currentDate);
        double goalDifference = calorieGoal - netCalories;

        view.updateStatsPanel(calorieGoal, calories, caloriesBurnedTotal, netCalories, goalDifference, fat, carbs, protein);

        // Weight and calorie goal display
        view.updateWeight(weight);
        view.updateCalorieGoal(calorieGoal);
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate date) {
        this.currentDate = date;
    }

    public Foods getFoods() {
        return foods;
    }

    public Exercises getExercises() {
        return exercises;
    }

    public Logs getLogs() {
        return logs;
    }

    public View getView() {
        return view;
    }
}
