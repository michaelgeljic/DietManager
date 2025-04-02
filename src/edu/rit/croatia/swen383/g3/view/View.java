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
    private JTextArea logDisplayArea;

    private JButton addLogButton;
    private JButton addFoodButton;
    private JButton setGoalsButton;

    private ChartPanel pieChartPanel;
    private DefaultPieDataset pieDataset;

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

        foodListModel = new DefaultListModel<>();
        foodList = new JList<>(foodListModel);
        foodList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane foodScroll = new JScrollPane(foodList);
        foodScroll.setPreferredSize(new Dimension(400, 200));

        JPanel foodPanel = new JPanel(new BorderLayout());
        foodPanel.add(new JLabel("Available Foods (Click to Select)"), BorderLayout.NORTH);
        foodPanel.add(foodScroll, BorderLayout.CENTER);

        logDisplayArea = new JTextArea(12, 30);
        logDisplayArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logDisplayArea);
        JPanel logPanel = new JPanel(new BorderLayout());

        currentDateLabel = new JLabel("Date: " + LocalDate.now()); // Initial text
        logPanel.add(currentDateLabel, BorderLayout.NORTH);
        logPanel.add(logScroll, BorderLayout.CENTER);

        // Buttons
        addLogButton = new JButton("Add Log Entry");
        addFoodButton = new JButton("Add New Food/Recipe");
        setGoalsButton = new JButton("Set Goals");
        changeDateButton = new JButton("Change Date");

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new BoxLayout(leftButtonPanel, BoxLayout.Y_AXIS));
        leftButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.add(changeDateButton); // new top left button
        leftButtonPanel.add(topLeftPanel);

        JPanel logFoodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logFoodPanel.add(addLogButton);
        logFoodPanel.add(addFoodButton);
        leftButtonPanel.add(logFoodPanel);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(foodPanel, BorderLayout.CENTER);
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
        JButton setGoalsCopy = setGoalsButton;
        setGoalsCopy.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.add(Box.createVerticalStrut(10)); // spacing
        statsPanel.add(setGoalsCopy);

        // Pie chart setup
        pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Fat", 0);
        pieDataset.setValue("Carbs", 0);
        pieDataset.setValue("Protein", 0);

        JFreeChart chart = ChartFactory.createPieChart("Nutrient Distribution", pieDataset, true, true, false);
        pieChartPanel = new ChartPanel(chart);
        pieChartPanel.setPreferredSize(new Dimension(300, 200));

        // Create a subpanel to hold the chart and stats side by side
        JPanel chartAndStatsPanel = new JPanel(new GridLayout(1, 2));

        // Add pie chart (left side)
        pieChartPanel.setPreferredSize(new Dimension(400, 300));
        chartAndStatsPanel.add(pieChartPanel);

        // Add stats (right side)
        statsPanel.setPreferredSize(new Dimension(200, 200));
        chartAndStatsPanel.add(statsPanel);

        // Now put the chart+stats above the log panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(chartAndStatsPanel, BorderLayout.NORTH);
        // Create vertical container for log + button
        JPanel logContainer = new JPanel();
        logContainer.setLayout(new BoxLayout(logContainer, BoxLayout.Y_AXIS));

        // Set preferred height so it matches left panel better
        logScroll.setPreferredSize(new Dimension(400, 300)); // adjust height to match foods
        logContainer.add(logPanel);

        // Center-align and add some spacing before the button
        changeDateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logContainer.add(Box.createVerticalStrut(8));
        logContainer.add(changeDateButton);

        rightPanel.add(logContainer, BorderLayout.CENTER);

        // Center layout
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(leftPanel); // ← left side (food list + buttons)
        centerPanel.add(rightPanel); // ← right side (chart + stats + logs)

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
    public void updateLogList(String content) {
        logDisplayArea.setText(content);
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
     * Returns the currently selected food from the list.
     *
     * @return the selected food's display string, or null if none selected
     */
    public String getSelectedFoodName() {
        return foodList.getSelectedValue();
    }

    /**
     * Returns a list of all currently selected foods from the list.
     *
     * @return a list of display strings for selected foods
     */
    public List<String> getSelectedFoodNames() {
        return foodList.getSelectedValuesList();
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
     * Prompts the user to enter the number of servings for a selected food.
     *
     * @return the user's input string or null if canceled
     */
    public String promptForServings() {
        return JOptionPane.showInputDialog(this, "Enter number of servings:");
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
    public void promptAndSetGoals() {
        JTextField calorieField = new JTextField();
        JTextField weightField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Calorie Goal:"));
        panel.add(calorieField);
        panel.add(new JLabel("Current Weight (kg):"));
        panel.add(weightField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Set Goals", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int calories = Integer.parseInt(calorieField.getText().trim());
                double weight = Double.parseDouble(weightField.getText().trim());

                if (calories > 0 && weight > 0) {
                    setCalorieGoal(calories);
                    setWeight(weight);
                    showMessage("Goals updated!");
                } else {
                    showMessage("Please enter valid positive numbers.");
                }
            } catch (NumberFormatException e) {
                showMessage("Please enter valid numbers.");
            }
        }
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

}
