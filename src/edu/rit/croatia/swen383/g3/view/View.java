package edu.rit.croatia.swen383.g3.view;

import  edu.rit.croatia.swen383.g3.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * The View class handles all user interface components for the Diet Manager
 * application.
 * It uses Swing to display food options, logs, and allows user interaction
 * through buttons and dialogs.
 */
public class View extends JFrame {
    private JList<String> foodList;
    private DefaultListModel<String> foodListModel;
    private JTextArea logDisplayArea;
    private JButton loadButton;
    private JButton addLogButton;
    private JButton addFoodButton;
    private JLabel calorieLabel;
    private JLabel weightLabel;

    /**
     * Constructs the GUI layout, initializes components and panels.
     */
    public View() {
        setTitle("Diet Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        // Food list panel
        foodListModel = new DefaultListModel<>();
        foodList = new JList<>(foodListModel);
        JScrollPane foodScroll = new JScrollPane(foodList);
        JPanel foodPanel = new JPanel(new BorderLayout());
        foodPanel.add(new JLabel("Available Foods (Click to Select)"), BorderLayout.NORTH);
        foodPanel.add(foodScroll, BorderLayout.CENTER);

        // Log display panel
        logDisplayArea = new JTextArea(10, 30);
        logDisplayArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logDisplayArea);
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.add(new JLabel("Today's Log"), BorderLayout.NORTH);
        logPanel.add(logScroll, BorderLayout.CENTER);

        // Buttons and info panel
        loadButton = new JButton("Load Data");
        addLogButton = new JButton("Add Log Entry");
        addFoodButton = new JButton("Add New Food/Recipe");
        calorieLabel = new JLabel("Total Calories: 0");
        weightLabel = new JLabel("Weight: 0.0 kg");

        JPanel controlPanel = new JPanel();
        addButtons(controlPanel, loadButton, addLogButton, addFoodButton, calorieLabel, weightLabel);

        // Main layout
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(foodPanel);
        centerPanel.add(logPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds multiple components to a panel using varargs.
     *
     * @param panel      the panel to add components to
     * @param components variable number of components to add
     */
    private void addButtons(JPanel panel, JComponent... components) {
        for (JComponent comp : components) {
            panel.add(comp);
        }
    }

    /**
     * Updates the JList with a list of food names.
     *
     * @param foodNames list of foods to show in the food list
     */
    public void updateFoodList(List<String> foodNames) {
        foodListModel.clear();
        for (String name : foodNames) {
            foodListModel.addElement(name);
        }
    }

    /**
     * Returns the currently selected food name from the list.
     *
     * @return the selected food name, or null if nothing is selected
     */
    public String getSelectedFoodName() {
        return foodList.getSelectedValue();
    }

    /**
     * Updates the log text area with the given log content.
     *
     * @param content log entries to display
     */
    public void updateLogList(String content) {
        logDisplayArea.setText(content);
    }

    /**
     * Updates the calorie label with the total calories.
     *
     * @param calories total calorie value
     */
    public void updateCalories(double calories) {
        calorieLabel.setText("Total Calories: " + calories);
    }

    /**
     * Updates the weight label with the current weight.
     *
     * @param weight weight in kilograms
     */
    public void updateWeight(double weight) {
        weightLabel.setText("Weight: " + weight + " kg");
    }

    /**
     * Adds an ActionListener for the Load button.
     *
     * @param listener the listener to trigger on click
     */
    public void addLoadListener(ActionListener listener) {
        loadButton.addActionListener(listener);
    }

    /**
     * Adds an ActionListener for the Add Log Entry button.
     *
     * @param listener the listener to trigger on click
     */
    public void addLogListener(ActionListener listener) {
        addLogButton.addActionListener(listener);
    }

    /**
     * Adds an ActionListener for the Add Food/Recipe button.
     *
     * @param listener the listener to trigger on click
     */
    public void addFoodListener(ActionListener listener) {
        addFoodButton.addActionListener(listener);
    }

    /**
     * Displays a message dialog box.
     *
     * @param message the message to show to the user
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Prompts the user to enter the number of servings.
     *
     * @return the input string (can be null if canceled)
     */
    public String promptForServings() {
        return JOptionPane.showInputDialog(this, "Enter number of servings:");
    }

    /**
     * Prompts the user to choose between multiple food types (e.g., Basic or
     * Recipe).
     *
     * @param message the message to display
     * @param options an array of option names
     * @return the index of the selected option
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
        String name = JOptionPane.showInputDialog(this, "Enter recipe name:");
        if (name == null || name.trim().isEmpty())
            return null;

        List<String> options = availableFoods.stream().map(Food::getName).toList();
        JList<String> ingredientList = new JList<>(options.toArray(new String[0]));
        ingredientList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scroll = new JScrollPane(ingredientList);
        scroll.setPreferredSize(new Dimension(300, 200));

        int result = JOptionPane.showConfirmDialog(this, scroll, "Select Ingredients", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Recipe recipe = new Recipe(name.trim());

            Map<String, Food> foodMap = new HashMap<>();
            for (Food f : availableFoods) {
                foodMap.put(f.getName(), f);
            }

            for (String selectedName : ingredientList.getSelectedValuesList()) {
                Food food = foodMap.get(selectedName);
                if (food != null) {
                    String servingsStr = JOptionPane.showInputDialog(this,
                            "Enter number of servings for: " + food.getName());
                    if (servingsStr == null)
                        continue;

                    try {
                        double servings = Double.parseDouble(servingsStr.trim());
                        if (servings > 0) {
                            recipe.add(food, servings);
                        }
                    } catch (NumberFormatException e) {
                        showMessage("Invalid servings amount for " + food.getName());
                    }
                }
            }

            if (recipe.getIngredientsWithServings().isEmpty()) {
                showMessage("Recipe must have at least one valid ingredient with servings.");
                return null;
            }

            return recipe;
        }

        return null;
    }
}
