package edu.rit.croatia.swen383.g3.view;

import edu.rit.croatia.swen383.g3.model.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class View extends JFrame {
    private int calorieGoal = 2000;
    private double currentWeight = 0.0;
    private JLabel currentDateLabel;

    private JLabel goalLabel;
    private JLabel calorieLabel;
    private JLabel remainingLabel;
    private JLabel fatLabel;
    private JLabel carbsLabel;
    private JLabel proteinLabel;
    private JLabel weightLabel;
    private JButton changeDateButton;

    private DefaultListModel<String> foodListModel;
    private JList<String> foodList;
    // private JTextArea logDisplayArea;

    private JButton addLogButton;
    private JButton addFoodButton;
    private JButton setGoalsButton;

    private ChartPanel pieChartPanel;
    private DefaultPieDataset pieDataset;

    private DefaultListModel<String> exerciseListModel;
    private JList<String> exerciseList;
    // private JTextArea exerciseLogTextArea;
    private JButton addExerciseButton;
    private JButton deleteLogButton;
    private DefaultListModel<String> foodLogListModel;
    private JList<String> foodLogList;

    private DefaultListModel<String> exerciseLogListModel;
    private JList<String> exerciseLogList;
    private JButton editExerciseButton;

    /**
     * The {@code View} class is responsible for creating and managing the graphical
     * user interface (GUI)
     * of the Diet Manager application. It displays available foods, logs, nutrient
     * statistics,
     * and provides interaction buttons such as "Add Log Entry", "Add New Food",
     * "Set Goals", and "Change Date".
     * <p>
     * The View communicates with the Controller through listeners and provides
     * prompts
     * for entering food and recipe data, as well as servings and user goals.
     */
    public View() {
        setTitle("Diet Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Foods
        foodListModel = new DefaultListModel<>();
        foodList = new JList<>(foodListModel);
        foodList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane foodScroll = new JScrollPane(foodList);
        foodScroll.setPreferredSize(new Dimension(400, 200));
        JPanel foodPanel = new JPanel(new BorderLayout());
        foodPanel.add(new JLabel("Available Foods (Click to Select)"), BorderLayout.NORTH);
        foodPanel.add(foodScroll, BorderLayout.CENTER);

        // Exercises
        exerciseListModel = new DefaultListModel<>();
        exerciseList = new JList<>(exerciseListModel);
        exerciseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane exerciseScroll = new JScrollPane(exerciseList);
        exerciseScroll.setPreferredSize(new Dimension(400, 150));
        JPanel exercisePanel = new JPanel(new BorderLayout());
        exercisePanel.add(new JLabel("Available Exercises (Click to Select)"), BorderLayout.NORTH);
        exercisePanel.add(exerciseScroll, BorderLayout.CENTER);

        // Combine Foods + Exercises
        JPanel leftListPanel = new JPanel(new BorderLayout());
        leftListPanel.add(foodPanel, BorderLayout.CENTER);
        leftListPanel.add(exercisePanel, BorderLayout.SOUTH);

        // Buttons
        addLogButton = new JButton("Log Food/Exercise");
        addFoodButton = new JButton("Add New Food/Recipe");
        addExerciseButton = new JButton("Add New Exercise");
        setGoalsButton = new JButton("Set Goals");
        changeDateButton = new JButton("Change Date");
        deleteLogButton = new JButton("Delete Log Entry");
        editExerciseButton = new JButton("Edit Exercise");

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new BoxLayout(leftButtonPanel, BoxLayout.Y_AXIS));

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.add(addFoodButton);
        topLeftPanel.add(addExerciseButton);
        topLeftPanel.add(addLogButton);
        leftButtonPanel.add(topLeftPanel);

        JPanel logFoodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logFoodPanel.add(deleteLogButton);
        logFoodPanel.add(editExerciseButton);
        leftButtonPanel.add(logFoodPanel);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(leftListPanel, BorderLayout.CENTER);
        leftPanel.add(leftButtonPanel, BorderLayout.SOUTH);

        // Stats and Pie Chart Panel
        goalLabel = new JLabel("Calorie Goal: " + calorieGoal);
        calorieLabel = new JLabel("Total Calories: 0");
        remainingLabel = new JLabel("Remaining: " + calorieGoal);
        fatLabel = new JLabel("Fat: 0g");
        carbsLabel = new JLabel("Carbs: 0g");
        proteinLabel = new JLabel("Protein: 0g");
        weightLabel = new JLabel("Weight: " + currentWeight + " kg");

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Daily Stats"));
        statsPanel.add(goalLabel);
        statsPanel.add(calorieLabel);
        statsPanel.add(remainingLabel);
        statsPanel.add(fatLabel);
        statsPanel.add(carbsLabel);
        statsPanel.add(proteinLabel);
        statsPanel.add(weightLabel);
        statsPanel.add(Box.createVerticalStrut(10));
        statsPanel.add(setGoalsButton);

        // Pie chart setup
        pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Fat", 0);
        pieDataset.setValue("Carbs", 0);
        pieDataset.setValue("Protein", 0);
        JFreeChart chart = ChartFactory.createPieChart("Nutrient Distribution", pieDataset, true, true, false);
        pieChartPanel = new ChartPanel(chart);
        pieChartPanel.setPreferredSize(new Dimension(300, 200));

        JPanel chartAndStatsPanel = new JPanel(new GridLayout(1, 2));
        chartAndStatsPanel.add(pieChartPanel);
        chartAndStatsPanel.add(statsPanel);

        // Change Date + Delete Log Buttons (side by side)
        JPanel dateAndDeletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dateAndDeletePanel.add(changeDateButton);
        dateAndDeletePanel.add(deleteLogButton);

        JPanel topRightPanel = new JPanel(new BorderLayout());
        topRightPanel.add(chartAndStatsPanel, BorderLayout.NORTH);
        topRightPanel.add(dateAndDeletePanel, BorderLayout.CENTER);

        // Food and Exercise Logs
        foodLogListModel = new DefaultListModel<>();
        foodLogList = new JList<>(foodLogListModel);
        foodLogList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        exerciseLogListModel = new DefaultListModel<>();
        exerciseLogList = new JList<>(exerciseLogListModel);
        exerciseLogList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel foodLogPanel = new JPanel(new BorderLayout());
        currentDateLabel = new JLabel("Date: " + LocalDate.now());
        foodLogPanel.add(currentDateLabel, BorderLayout.NORTH);
        foodLogPanel.add(new JScrollPane(foodLogList), BorderLayout.CENTER);

        JPanel exerciseLogPanel = new JPanel(new BorderLayout());
        exerciseLogPanel.add(new JLabel("Exercise Logs:"), BorderLayout.NORTH);
        exerciseLogPanel.add(new JScrollPane(exerciseLogList), BorderLayout.CENTER);

        JPanel logPanel = new JPanel(new GridLayout(2, 1));
        logPanel.add(foodLogPanel);
        logPanel.add(exerciseLogPanel);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(topRightPanel, BorderLayout.NORTH);
        rightPanel.add(logPanel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Updates the displayed nutrient statistics (calories, fat, carbs, protein)
     * and refreshes the pie chart to match the values.
     *
     * @param calories total calories consumed
     * @param fat      total fat consumed
     * @param carbs    total carbohydrates consumed
     * @param protein  total protein consumed
     */
    public void updateStats(double calories, double fat, double carbs, double protein) {
        calorieLabel.setText("Total Calories: " + calories);
        remainingLabel.setText("Remaining: " + (calorieGoal - calories));
        fatLabel.setText("Fat: " + fat + "g");
        carbsLabel.setText("Carbs: " + carbs + "g");
        proteinLabel.setText("Protein: " + protein + "g");

        pieDataset.setValue("Fat", fat);
        pieDataset.setValue("Carbs", carbs);
        pieDataset.setValue("Protein", protein);
    }

    /**
     * Updates the calorie goal and adjusts the "Remaining" label based on current
     * calorie intake.
     *
     * @param goal the new daily calorie goal
     */
    public void setCalorieGoal(int goal) {
        this.calorieGoal = goal;
        goalLabel.setText("Calorie Goal: " + goal);
        // Also update remaining label based on current calories shown
        String currentText = calorieLabel.getText().replaceAll("[^0-9.]", "");
        if (!currentText.isEmpty()) {
            double current = Double.parseDouble(currentText);
            remainingLabel.setText("Remaining: " + (calorieGoal - current));
        }
    }

    /**
     * Updates the label displaying the currently selected date.
     *
     * @param date the LocalDate to display
     */
    public void updateCurrentDate(LocalDate date) {
        currentDateLabel.setText("Date: " + date.toString());
    }

    /**
     * Updates the weight display.
     *
     * @param weight the user's current weight in kilograms
     */
    public void setWeight(double weight) {
        this.currentWeight = weight;
        weightLabel.setText("Weight: " + weight + " kg");
    }

    /**
     * Adds an ActionListener to the "Change Date" button.
     *
     * @param listener the ActionListener to handle date changes
     */
    public void addChangeDateListener(ActionListener listener) {
        changeDateButton.addActionListener(listener);
    }

    /**
     * Replaces the food list in the GUI with the given list of foods.
     *
     * @param foods the list of Food objects to display
     */
    public void updateFoodList(List<Food> foods) {
        foodListModel.clear();
        for (Food food : foods) {
            String display = formatFoodDetails(food);
            foodListModel.addElement(display);
        }
    }

    private String formatFoodDetails(Food food) {
        return String.format("%s — %.1f cal | %.1fg fat | %.1fg carbs | %.1fg protein",
                food.getName(),
                food.getCalories(),
                food.getFat(),
                food.getCarbs(),
                food.getProtein());
    }

    /**
     * Replaces the content of the log display area.
     *
     * @param content the text to display in the log section
     */
    public void updateLogList(String text) {
        foodLogListModel.clear();
        for (String line : text.split("\n")) {
            foodLogListModel.addElement(line);
        }
    }

    /**
     * Adds an ActionListener to the "Add Log Entry" button.
     *
     * @param listener the ActionListener to trigger logging
     */
    public void addLogListener(ActionListener listener) {
        addLogButton.addActionListener(listener);
    }

    /**
     * Adds an ActionListener to the "Add New Food/Recipe" button.
     *
     * @param listener the ActionListener to trigger food creation
     */
    public void addFoodListener(ActionListener listener) {
        addFoodButton.addActionListener(listener);
    }

    /**
     * Adds an ActionListener to the "Set Goals" button.
     *
     * @param listener the ActionListener to handle goal setting
     */
    public void addSetGoalsListener(ActionListener listener) {
        setGoalsButton.addActionListener(listener);
    }

    /**
     * Shows a message dialog with the given message.
     *
     * @param message the text to display in the dialog
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Prompts the user to select between available options for food type (e.g.,
     * Basic Food or Recipe).
     *
     * @param message the message to show
     * @param options the string options to choose from
     * @return the index of the selected option, or -1 if canceled
     */
    public int promptForType(String message, String[] options) {
        return JOptionPane.showOptionDialog(
                this,
                message,
                "Select Food Type",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    /**
     * Updates the weight label with the given value.
     *
     * @param weight the user's weight in kg
     */
    public void updateWeight(double weight) {
        setWeight(weight);
    }

    /**
     * Updates the calorie goal label with the given value.
     *
     * @param goal the daily calorie goal
     */
    public void updateCalorieGoal(int goal) {
        setCalorieGoal(goal);
    }

    /**
     * Prompts the user to input servings for one or more selected food items.
     * If multiple foods are selected, a custom input form is shown.
     *
     * @param selectedFoods a list of selected Food objects
     * @return a map of Food to servings entered by the user, or null if canceled
     */
    public Map<Food, Double> promptForServingsForMultipleFoods(List<Food> selectedFoods) {
        if (selectedFoods == null || selectedFoods.isEmpty()) {
            showMessage("No valid foods selected.");
            return null;
        }

        // Case: single food selected — use simple input dialog
        if (selectedFoods.size() == 1) {
            Food food = selectedFoods.get(0);
            String input = JOptionPane.showInputDialog(
                    this,
                    "Enter servings for: " + food.getName(),
                    "Enter Servings",
                    JOptionPane.PLAIN_MESSAGE);

            if (input == null)
                return null;

            try {
                double servings = Double.parseDouble(input.trim());
                if (servings > 0) {
                    Map<Food, Double> result = new HashMap<>();
                    result.put(food, servings);
                    return result;
                }
            } catch (NumberFormatException e) {
                showMessage("Invalid input. Please enter a valid number.");
            }

            return null;
        }

        // Case: multiple foods — use panel with fields
        Map<Food, JTextField> fieldMap = new HashMap<>();
        JPanel panel = new JPanel(new GridLayout(selectedFoods.size(), 2, 10, 10));

        for (Food food : selectedFoods) {
            panel.add(new JLabel(food.getName() + " servings:"));
            JTextField servingsField = new JTextField();
            panel.add(servingsField);
            fieldMap.put(food, servingsField);
        }

        panel.setPreferredSize(new Dimension(400, selectedFoods.size() * 40));

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Enter servings for each food",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION)
            return null;

        Map<Food, Double> servingsMap = new HashMap<>();
        for (Map.Entry<Food, JTextField> entry : fieldMap.entrySet()) {
            try {
                double servings = Double.parseDouble(entry.getValue().getText().trim());
                if (servings > 0) {
                    servingsMap.put(entry.getKey(), servings);
                }
            } catch (NumberFormatException e) {
                // skip invalid entries
            }
        }

        if (servingsMap.isEmpty()) {
            showMessage("No valid servings entered.");
            return null;
        }

        return servingsMap;
    }

    /**
     * Opens a dialog to let the user enter a new calorie goal and weight.
     * If valid inputs are entered, updates the view with the new values.
     */
    public double[] promptAndSetGoals() {
        JTextField weightField = new JTextField();
        JTextField calorieGoalField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Enter your weight (kg):"));
        panel.add(weightField);
        panel.add(Box.createVerticalStrut(10)); // Spacing
        panel.add(new JLabel("Enter your calorie goal:"));
        panel.add(calorieGoalField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Set Goals", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double weight = Double.parseDouble(weightField.getText().trim());
                double calorieGoal = Double.parseDouble(calorieGoalField.getText().trim());
                updateWeight(weight);
                updateCalorieGoal(calorieGoal);
                return new double[] { weight, calorieGoal };
            } catch (NumberFormatException e) {
                showMessage("Invalid input. Please enter valid numbers.");
            }
        }
        return null; // Return null if canceled or invalid
    }

    /**
     * Prompts the user to enter data for a BasicFood object.
     *
     * @return a new BasicFood if input is valid, or null if canceled/invalid
     */
    public BasicFood promptForBasicFood() {
        String[] labels = { "Name", "Calories", "Fat", "Carbs", "Protein" };
        Map<String, JTextField> fieldMap = new HashMap<>();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        for (String label : labels) {
            panel.add(new JLabel(label + ":"));
            JTextField field = new JTextField();
            panel.add(field);
            fieldMap.put(label, field);
        }

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Basic Food", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = fieldMap.get("Name").getText().trim();
                double cal = Double.parseDouble(fieldMap.get("Calories").getText().trim());
                double fat = Double.parseDouble(fieldMap.get("Fat").getText().trim());
                double carb = Double.parseDouble(fieldMap.get("Carbs").getText().trim());
                double pro = Double.parseDouble(fieldMap.get("Protein").getText().trim());

                return new BasicFood(name, cal, fat, carb, pro);
            } catch (NumberFormatException e) {
                showMessage("Invalid input. Please enter numbers for nutrition.");
            }
        }
        return null;
    }

    /**
     * Prompts the user to build a Recipe by selecting ingredients and assigning
     * servings.
     *
     * @param availableFoods list of available basic foods and recipes to choose
     *                       from
     * @return a completed Recipe or null if invalid or canceled
     */
    public Recipe promptForRecipe(List<Food> availableFoods) {
        // Main panel with layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Recipe name input
        panel.add(new JLabel("Recipe Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        panel.add(nameField, gbc);

        // Reset grid position
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        // Ingredient selection
        panel.add(new JLabel("Select Ingredients:"), gbc);
        gbc.gridy++;
        List<String> options = availableFoods.stream().map(Food::getName).toList();
        JList<String> ingredientList = new JList<>(options.toArray(new String[0]));
        ingredientList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scroll = new JScrollPane(ingredientList);
        scroll.setPreferredSize(new Dimension(250, 100));
        panel.add(scroll, gbc);

        int selection = JOptionPane.showConfirmDialog(this, panel, "Create Recipe", JOptionPane.OK_CANCEL_OPTION);
        if (selection != JOptionPane.OK_OPTION)
            return null;

        String recipeName = nameField.getText().trim();
        if (recipeName.isEmpty())
            return null;

        List<String> selectedIngredients = ingredientList.getSelectedValuesList();
        if (selectedIngredients.isEmpty()) {
            showMessage("Please select at least one ingredient.");
            return null;
        }

        // Now show another panel with serving inputs
        JPanel servingsPanel = new JPanel(new GridLayout(0, 2));
        Map<String, JTextField> fieldMap = new HashMap<>();
        for (String ingredient : selectedIngredients) {
            servingsPanel.add(new JLabel("Servings for " + ingredient + ":"));
            JTextField field = new JTextField();
            servingsPanel.add(field);
            fieldMap.put(ingredient, field);
        }

        int servingsConfirm = JOptionPane.showConfirmDialog(this, servingsPanel, "Enter Servings",
                JOptionPane.OK_CANCEL_OPTION);
        if (servingsConfirm != JOptionPane.OK_OPTION)
            return null;

        Recipe recipe = new Recipe(recipeName);
        Map<String, Food> foodMap = new HashMap<>();
        for (Food f : availableFoods)
            foodMap.put(f.getName(), f);

        for (String ingredient : selectedIngredients) {
            String input = fieldMap.get(ingredient).getText().trim();
            try {
                double servings = Double.parseDouble(input);
                if (servings > 0) {
                    recipe.add(foodMap.get(ingredient), servings);
                }
            } catch (NumberFormatException e) {
                showMessage("Invalid servings for " + ingredient);
            }
        }

        if (recipe.getIngredientsWithServings().isEmpty()) {
            showMessage("Recipe must have at least one valid ingredient.");
            return null;
        }

        return recipe;
    }

    public Exercise promptForExercise() {
        String name = JOptionPane.showInputDialog("Enter exercise name:");
        if (name == null || name.trim().isEmpty()) {
            showMessage("Exercise name cannot be empty.");
            return null;
        }

        try {
            String caloriesStr = JOptionPane.showInputDialog("Enter calories burned per kg per hour:");
            if (caloriesStr == null || caloriesStr.trim().isEmpty()) {
                showMessage("Calories per kg per hour cannot be empty.");
                return null;
            }
            double calories = Double.parseDouble(caloriesStr.trim());
            return new Exercise(name.trim(), calories);
        } catch (NumberFormatException e) {
            showMessage("Invalid calories input. Please enter a number.");
            return null;
        }
    }

    public void updateExerciseList(List<Exercise> exercises) {
        exerciseListModel.clear(); // Assuming you're using a DefaultListModel
        for (Exercise ex : exercises) {
            exerciseListModel.addElement(ex.getName() + " — " + ex.getCaloriesPerKgPerHour() + " cal/kg/hr");
        }
    }

    public void addExerciseListener(ActionListener listener) {
        addExerciseButton.addActionListener(listener);
    }

    public void updateExerciseLogList(String text) {
        exerciseLogListModel.clear();
        for (String line : text.split("\n")) {
            exerciseLogListModel.addElement(line);
        }
    }

    public void updateCalorieGoal(double goal) {
        goalLabel.setText("Calorie Goal: " + goal);
        remainingLabel.setText("Remaining: " + goal); // This gets updated properly in stats too
    }

    public void updateStatsPanel(double goal, double consumed, double burned, double net, double difference, double fat,
            double carbs, double protein) {
        goalLabel.setText("Calorie Goal: " + goal);
        calorieLabel.setText("Calories Consumed: " + consumed);
        fatLabel.setText("Calories Burned (Exercise): " + burned);
        proteinLabel.setText("Net Calories: " + net);
        carbsLabel.setText("Goal Difference: " + difference);
        pieDataset.setValue("Fat", fat); // Use the current fat, carbs, protein values
        pieDataset.setValue("Carbs", carbs);
        pieDataset.setValue("Protein", protein);

        // Optional: color feedback for goal difference
        if (difference < 0) {
            carbsLabel.setForeground(Color.RED); // Over goal
        } else {
            carbsLabel.setForeground(new Color(0, 128, 0)); // Under goal
        }
    }

    public void addDeleteLogListener(ActionListener listener) {
        deleteLogButton.addActionListener(listener);
    }

    // For Food log deletion
    public String getSelectedFoodLog() {
        return foodLogList.getSelectedValue();
    }

    public String getSelectedExerciseLog() {
        return exerciseLogList.getSelectedValue();
    }

    public double promptForWeight() {
        String input = JOptionPane.showInputDialog("Enter your weight (kg):");
        return Double.parseDouble(input.trim());
    }

    public double promptForCalorieGoal() {
        String input = JOptionPane.showInputDialog("Enter your calorie goal:");
        return Double.parseDouble(input.trim());
    }

    public Exercise promptForExerciseCombined() {
        JTextField nameField = new JTextField();
        JTextField caloriesField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Enter exercise name:"));
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(10)); // Spacing
        panel.add(new JLabel("Enter calories burned per kg per hour:"));
        panel.add(caloriesField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Exercise", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                double calories = Double.parseDouble(caloriesField.getText().trim());

                if (name.isEmpty()) {
                    showMessage("Exercise name cannot be empty.");
                    return null;
                }
                return new Exercise(name, calories);
            } catch (NumberFormatException e) {
                showMessage("Invalid input. Please enter valid numbers.");
            }
        }
        return null; // If canceled or invalid
    }

    // Dropdown for food selection
    public Food promptForFoodSelection(List<Food> foods) {
        Food[] foodArray = foods.toArray(new Food[0]);
        Food selected = (Food) JOptionPane.showInputDialog(this, "Select a food:", "Food Selection",
                JOptionPane.PLAIN_MESSAGE, null, foodArray, foodArray[0]);
        return selected;
    }

    // Dropdown for exercise selection
    public Exercise promptForExerciseSelection(List<Exercise> exercises) {
        Exercise[] exerciseArray = exercises.toArray(new Exercise[0]);
        Exercise selected = (Exercise) JOptionPane.showInputDialog(this, "Select an exercise:", "Exercise Selection",
                JOptionPane.PLAIN_MESSAGE, null, exerciseArray, exerciseArray[0]);
        return selected;
    }

    public Object[] promptForFoodAndServings(List<Food> foods) {
        JComboBox<Food> foodDropdown = new JComboBox<>(foods.toArray(new Food[0]));
        JTextField servingsField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Select food:"));
        panel.add(foodDropdown);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Enter servings:"));
        panel.add(servingsField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Log Food", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Food selectedFood = (Food) foodDropdown.getSelectedItem();
                double servings = Double.parseDouble(servingsField.getText().trim());
                return new Object[] { selectedFood, servings };
            } catch (NumberFormatException e) {
                showMessage("Invalid servings input.");
            }
        }
        return null; // If canceled or invalid
    }

    public Object[] promptForExerciseAndMinutes(List<Exercise> exercises) {
        JComboBox<Exercise> exerciseDropdown = new JComboBox<>(exercises.toArray(new Exercise[0]));
        JTextField minutesField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Select exercise:"));
        panel.add(exerciseDropdown);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Enter minutes:"));
        panel.add(minutesField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Log Exercise", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Exercise selectedExercise = (Exercise) exerciseDropdown.getSelectedItem();
                double minutes = Double.parseDouble(minutesField.getText().trim());
                return new Object[] { selectedExercise, minutes };
            } catch (NumberFormatException e) {
                showMessage("Invalid minutes input.");
            }
        }
        return null;
    }

    public JButton getEditExerciseButton() {
        return editExerciseButton;
    }

}
